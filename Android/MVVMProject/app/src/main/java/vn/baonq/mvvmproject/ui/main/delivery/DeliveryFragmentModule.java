package vn.baonq.mvvmproject.ui.main.delivery;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedAdapter;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedFragment;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class DeliveryFragmentModule {
    @Provides
    DeliveryViewModel deliveryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new DeliveryViewModel(dataManager, schedulerProvider);
    }

    @Provides
    DeliveryAdapter provideDeliveryAdapter(DeliveryFragment fragment){
        return new DeliveryAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideDeliveryViewModel(DeliveryViewModel deliveryViewModel) {
        return new ViewModelProviderFactory<>(deliveryViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(DeliveryFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
