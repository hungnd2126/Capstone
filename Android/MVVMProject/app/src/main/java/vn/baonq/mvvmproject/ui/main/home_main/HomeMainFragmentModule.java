package vn.baonq.mvvmproject.ui.main.home_main;


import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class HomeMainFragmentModule {
    @Provides
    HomeMainPagerAdapter provideHomeMainPagerAdapter(HomeMainFragment homeMainFragment){
        return new HomeMainPagerAdapter(homeMainFragment.getFragmentManager());
    }

    @Provides
    ViewModelProvider.Factory homeMainViewModelProvider(HomeMainViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    HomeMainViewModel provideHomeMainViewModel(DataManager dataManager,
                                        SchedulerProvider schedulerProvider) {
        return new HomeMainViewModel(dataManager, schedulerProvider);
    }
}
