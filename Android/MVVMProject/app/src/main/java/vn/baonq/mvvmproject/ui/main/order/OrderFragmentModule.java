package vn.baonq.mvvmproject.ui.main.order;


import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class OrderFragmentModule {

    @Provides
    OrderPagerAdapter provideOrderPagerAdapter(OrderFragment orderFragment){
        return new OrderPagerAdapter(orderFragment.getFragmentManager());
    }

    @Provides
    ViewModelProvider.Factory orderViewModelProvider(OrderViewModel orderViewModel) {
        return new ViewModelProviderFactory<>(orderViewModel);
    }

    @Provides
    OrderViewModel provideFeedViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider) {
        return new OrderViewModel(dataManager, schedulerProvider);
    }
}
