package vn.baonq.mvvmproject.ui.singleTripManage.offering;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class OfferingFragmentModule {
    @Provides
    OfferingViewModel offeringViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new OfferingViewModel(dataManager, schedulerProvider);
    }

    @Provides
    OfferingAdapter provideOfferingAdapter(OfferingFragment fragment){
        return new OfferingAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideOfferingVIewModel(OfferingViewModel offeringViewModel){
        return new ViewModelProviderFactory<>(offeringViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OfferingFragment fragment){
        return new LinearLayoutManager(fragment.getActivity());
    }
}
