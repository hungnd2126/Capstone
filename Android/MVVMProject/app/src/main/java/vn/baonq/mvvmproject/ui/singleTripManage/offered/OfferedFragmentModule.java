package vn.baonq.mvvmproject.ui.singleTripManage.offered;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class OfferedFragmentModule {

    @Provides
    OfferedViewModel offeredViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new OfferedViewModel(dataManager, schedulerProvider);
    }

    @Provides
    OfferedAdapter provideOfferedAdapter(OfferedFragment fragment){
        return new OfferedAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideOfferedVIewModel(OfferedViewModel offeredViewModel){
        return new ViewModelProviderFactory<>(offeredViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OfferedFragment fragment){
        return new LinearLayoutManager(fragment.getActivity());
    }
}
