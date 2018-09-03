package vn.baonq.mvvmproject.ui.main.order.Transit;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class TransitFragmentModule {

    @Provides
    TransitViewModel transitViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new TransitViewModel(dataManager, schedulerProvider);
    }

    @Provides
    TransitAdapter provideTransitAdapter(TransitFragment fragment){
        return new TransitAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideTransitViewModel(TransitViewModel transitViewModel) {
        return new ViewModelProviderFactory<>(transitViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(TransitFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
