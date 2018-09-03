package vn.baonq.mvvmproject.ui.main.order.Requested;


import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.order.OrderFragment;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class RequestedFragmentModule {

    @Provides
    RequestedViewModel requestedViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new RequestedViewModel(dataManager, schedulerProvider);
    }

    @Provides
    RequestedAdapter provideRequestedAdapter(RequestedFragment fragment){
        return new RequestedAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideRequestedViewModel(RequestedViewModel requestedViewModel) {
        return new ViewModelProviderFactory<>(requestedViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(RequestedFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
