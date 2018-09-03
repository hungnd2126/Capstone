package vn.baonq.mvvmproject.ui.main.home_buyer_offer;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class BuyerOfferActivityModule {
    @Provides
    BuyerOfferViewModel buyerOfferViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new BuyerOfferViewModel(dataManager, schedulerProvider);
    }

    @Provides
    BuyerOfferAdapter provideTransitAdapter(BuyerOfferAcivity acivity){
        return new BuyerOfferAdapter(new ArrayList<>(), acivity.getApplicationContext());
    }

    @Provides
    ViewModelProvider.Factory provideBuyerOfferViewModel(BuyerOfferViewModel buyerOfferViewModel) {
        return new ViewModelProviderFactory<>(buyerOfferViewModel);
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(BuyerOfferAcivity acivity) {
        return new LinearLayoutManager(acivity.getApplicationContext());
    }

}
