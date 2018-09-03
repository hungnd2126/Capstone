package vn.baonq.mvvmproject.ui.main.home_buyer_offer;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityBuyerMakeOfferBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;

public class BuyerOfferAcivity extends BaseActivity<ActivityBuyerMakeOfferBinding, BuyerOfferViewModel> implements BuyerOfferNavigator {

    @Inject
    BuyerOfferAdapter mBuyerOfferAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    private BuyerOfferViewModel buyerOfferViewModel;

    ActivityBuyerMakeOfferBinding activityBuyerMakeOfferBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buyer_make_offer;
    }

    @Override
    public BuyerOfferViewModel getViewModel() {
        buyerOfferViewModel = ViewModelProviders.of(this, vmFactory).get(BuyerOfferViewModel.class);
        return buyerOfferViewModel;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBuyerMakeOfferBinding = getViewDataBinding();
        buyerOfferViewModel.setNavigator(this);
        getSupportActionBar().show();
        setUp();
        setLiveData();
    }

    private void setUp(){
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityBuyerMakeOfferBinding.requestedRecyclerView.setLayoutManager(mLayoutManager);
        activityBuyerMakeOfferBinding.requestedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBuyerOfferAdapter.setViewModel(buyerOfferViewModel);
        mBuyerOfferAdapter.setTripId(Integer.valueOf(getIntent().getStringExtra("tripId")));
        activityBuyerMakeOfferBinding.requestedRecyclerView.setAdapter(mBuyerOfferAdapter);
    }

    private void setLiveData(){
        buyerOfferViewModel.fecthPost(getIntent().getIntExtra("ToCityId",0)
                ,getIntent().getIntExtra("FromCityId",0));
        buyerOfferViewModel.getBuyerOfferLiveData().observe(this, item -> buyerOfferViewModel.addItems(item));
    }

    @Override
    public void showMessage(String mess, boolean isSuccess) {
        Toasty.normal(this, mess).show();
        if (isSuccess){
            finish();
        }
    }


}
