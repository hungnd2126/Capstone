package vn.baonq.mvvmproject.ui.main.home;


import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class HomeFragmentModule {

    @Provides
    HomeViewModel homeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new HomeViewModel(dataManager, schedulerProvider);
    }
    @Provides
    HomeAdapter provideHomeAdapter(HomeFragment fragment){
        return new HomeAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideHomeViewModel(HomeViewModel homeViewModel) {
        return new ViewModelProviderFactory<>(homeViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(HomeFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
