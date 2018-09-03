package vn.baonq.mvvmproject.ui.main.message.dialogs;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.ui.main.message.MessageViewModel;
import vn.baonq.mvvmproject.ui.main.message.model.Dialog;
import vn.baonq.mvvmproject.ui.main.message.model.Message;
import vn.baonq.mvvmproject.ui.main.message.model.User;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class DialogViewModel extends BaseViewModel<DialogNavigator> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private ArrayList<User> users;
    private Message lastMessage;

    public final ObservableList<Dialog> dialogObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<Dialog>> listMutableLiveData;
    public DialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData= new MutableLiveData<>();
        setDialog();
    }
    public ObservableList<Dialog> getDialogObservableArrayList() {
        return dialogObservableArrayList;
    }

    public void setDialog() {
    }
    public void addRequestedItem(List<Dialog> item) {
        dialogObservableArrayList.clear();
        dialogObservableArrayList.addAll(item);
    }
    public MutableLiveData<List<Dialog>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    @BindingAdapter({"dialog_adapter"})
    public static void addDialogItems(RecyclerView recyclerView, List<Dialog> items) {
        DialogAdapter adapter = (DialogAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }
}
