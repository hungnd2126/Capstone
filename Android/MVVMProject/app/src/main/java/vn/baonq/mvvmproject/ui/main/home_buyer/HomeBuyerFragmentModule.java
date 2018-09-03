package vn.baonq.mvvmproject.ui.main.home_buyer;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class HomeBuyerFragmentModule {
    @Provides
    HomeBuyerViewModel homeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new HomeBuyerViewModel(dataManager, schedulerProvider);
    }
    @Provides
    HomeBuyerAdapter homeBuyerAdapter(HomeBuyerFragment fragment){
        return new HomeBuyerAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideTransitViewModel(HomeBuyerViewModel model) {
        return new ViewModelProviderFactory<>(model);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(HomeBuyerFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
}}
