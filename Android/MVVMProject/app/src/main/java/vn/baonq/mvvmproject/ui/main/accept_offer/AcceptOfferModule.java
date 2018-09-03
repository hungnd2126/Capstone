package vn.baonq.mvvmproject.ui.main.accept_offer;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class AcceptOfferModule {
    @Provides
    AcceptOfferViewModel acceptOfferViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AcceptOfferViewModel(dataManager, schedulerProvider);
    }
    @Provides
    AcceptOfferAdapter acceptOfferAdapter(AcceptOfferActivity activity){
        return new AcceptOfferAdapter(new ArrayList<>(), activity);
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(AcceptOfferActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    ViewModelProvider.Factory factory(AcceptOfferViewModel acceptOfferViewModel) {
        return new ViewModelProviderFactory<>(acceptOfferViewModel);
    }
}
