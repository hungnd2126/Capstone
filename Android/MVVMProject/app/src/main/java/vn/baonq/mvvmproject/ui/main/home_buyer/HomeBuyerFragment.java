package vn.baonq.mvvmproject.ui.main.home_buyer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.FragmentHomebuyerBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.utils.ProcessBar;


public class HomeBuyerFragment extends BaseFragment<FragmentHomebuyerBinding, HomeBuyerViewModel> {
    public static final String TAG = HomeBuyerFragment.class.getSimpleName();
    public static HomeBuyerFragment newInstance() {

        Bundle args = new Bundle();
        HomeBuyerFragment fragment = new HomeBuyerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    HomeBuyerViewModel mHomeBuyerViewModel;

    FragmentHomebuyerBinding mFragmentHomebuyerBinding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;
    @Inject
    HomeBuyerAdapter mHomeBuyerAdapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_homebuyer;
    }

    @Override
    public HomeBuyerViewModel getViewModel() {
        mHomeBuyerViewModel = ViewModelProviders.of(this, vmFactory).get(HomeBuyerViewModel.class);
        return mHomeBuyerViewModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " on create");

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomebuyerBinding = getViewDataBinding();
        ProcessBar.runProgress(getContext());
        setUp();
        setLiveData();
        ProcessBar.endProgress();

    }
    private void setUp(){
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentHomebuyerBinding.fragmentHomeBuyerRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentHomebuyerBinding.fragmentHomeBuyerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentHomebuyerBinding.fragmentHomeBuyerRecyclerView.setAdapter(mHomeBuyerAdapter);
    }

    public void setLiveData(){
        mHomeBuyerViewModel.fechSuggestTrip();
        mHomeBuyerViewModel.getListMutableLiveData().observe(this, item -> mHomeBuyerViewModel.addItem(item));
    }

}
