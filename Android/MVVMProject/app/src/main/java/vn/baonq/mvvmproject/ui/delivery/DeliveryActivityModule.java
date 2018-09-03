package vn.baonq.mvvmproject.ui.delivery;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryAdapter;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class DeliveryActivityModule {

    @Provides
    DeliveryAdapter provideDeliveryAdapter(DeliveryActivity activity){
        return new DeliveryAdapter(new ArrayList<>(), activity.getApplication());
    }
    @Provides
    DeliveryViewModel deliveryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new DeliveryViewModel(dataManager, schedulerProvider);
    }
    @Provides
    ViewModelProvider.Factory provideDeliveryViewModel(DeliveryViewModel deliveryViewModel) {
        return new ViewModelProviderFactory<>(deliveryViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(DeliveryActivity activity) {
        return new LinearLayoutManager(activity.getApplication());
    }

}
