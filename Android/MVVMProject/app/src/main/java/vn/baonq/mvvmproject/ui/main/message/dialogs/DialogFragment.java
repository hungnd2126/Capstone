package vn.baonq.mvvmproject.ui.main.message.dialogs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.FragmentDialogBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.main.message.model.Dialog;
import vn.baonq.mvvmproject.ui.main.message.model.DialogFireBase;
import vn.baonq.mvvmproject.ui.main.message.model.Message;
import vn.baonq.mvvmproject.ui.main.message.model.MessageFrieBase;
import vn.baonq.mvvmproject.ui.main.message.model.User;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class DialogFragment extends BaseFragment<FragmentDialogBinding, DialogViewModel>
        implements DialogNavigator {
    public static DialogFragment newInstance() {
        Bundle args = new Bundle();
        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentDialogBinding binding;
    DialogViewModel dialogViewModel;
    @Inject
    DialogAdapter dialogAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dialog;
    }

    @Override
    public DialogViewModel getViewModel() {

        dialogViewModel = ViewModelProviders.of(this, vmFactory).get(DialogViewModel.class);
        return dialogViewModel;
    }

    private DialogsList dialogsList;
    protected ImageLoader imageLoader;
    private List<Dialog> dialogs = new ArrayList<>();
    private HashMap<String,Integer> map= new HashMap<>();
    int dialogPosition =0;
    private String senderId;
    private String senderAvartar;
    private String senderName;
    boolean search=false;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogViewModel.setNavigator(this);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
        senderId= dialogViewModel.getDataManager().getCurrentUser().getUid();
        senderAvartar= dialogViewModel.getDataManager().getCurrentUser().getImageUrl();
        senderName= dialogViewModel.getDataManager().getCurrentUser().getName();
        dialogAdapter.addSender(senderId, senderAvartar.replace(ApiEndPoint.BASE_URL,""), senderName);
        setLiveData();
        loadDialog();
        searchUser();

        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                if(search== true){
                    search=false;
//                    ProcessBar.runProgress(getContext());
                    dialogViewModel.getCompositeDisposable().add(dialogViewModel.getDataManager()
                            .findUser(binding.txtSearch.getText().toString())
                            .subscribeOn(dialogViewModel.getSchedulerProvider().io())
                            .observeOn(dialogViewModel.getSchedulerProvider().ui())
                            .subscribe(response -> {
                                if(response!=null&&response.size()>0){
                                    dialogAdapter.clearItems();
                                    Dialog dialog =null;
                                    for(vn.baonq.mvvmproject.data.model.api.DialogViewModel dialogViewModel : response) {
                                        dialog = new Dialog();
                                        dialog.setSender_Name(senderName);
                                        dialog.setSenderAvartar(senderAvartar);
                                        dialog.setSenderId(senderId);
                                        dialog.setReceiverAvartar(dialogViewModel.getUserAvartar());
                                        dialog.setReceiverName(dialogViewModel.getNickName());
                                        dialog.setReceiverId(dialogViewModel.getUserId());

                                        dialogAdapter.addItem(dialog);
                                        dialogAdapter.notifyDataSetChanged();
                                    }

                                }
                     //           ProcessBar.endProgress();
                            }, throwable -> {
                     //           ProcessBar.endProgress();

                            }));
                }
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            }
            }
        });
     thread.start();
    }

    private void searchUser(){
        binding.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(binding.txtSearch.getText().toString().equals("")){
                    search=false;
                    dialogAdapter.clearItems();
                    dialogAdapter.addItems(dialogs);
                }else{
                    search=true;

                }
            }

        });
    }
    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.fragmentDialogRecyclerView.setLayoutManager(mLayoutManager);
        binding.fragmentDialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.fragmentDialogRecyclerView.setAdapter(dialogAdapter);
    }
    public void loadDialog() {
        myRef.child("Message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String[] keys = key.split("_");
                for (String p : keys) {
                    if (p.equals(senderId)) {
                        User user = null;
                        Dialog dialog = new Dialog();
                        dialog  =dataSnapshot.getValue(Dialog.class);
                        if(dialog.getLastMessage()!=null&&!dialog.getLastMessage().equals("")){
                        dialogs.add(dialog);
                        map.put(key,dialogPosition);
                        dialogPosition++;
                        dialogAdapter.addItem(dialog);
                        dialogAdapter.notifyDataSetChanged();
                        break;
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Dialog dialog = new Dialog();
//                dialog  =dataSnapshot.getValue(Dialog.class);
//                if(map.get(dataSnapshot.getKey().get)
//                int position =map.get(dataSnapshot.getKey());
//                dialogAdapter.updateItem(dialog,position);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setLiveData() {
        dialogViewModel.getListMutableLiveData().observe(this, requested -> dialogViewModel.addRequestedItem(requested));
    }

}