package vn.baonq.mvvmproject.ui.singleTripManage.STMTransit;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class STMTransitFragmentModule {
    @Provides
    STMTransitViewModel stmTransitViewModel (DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new STMTransitViewModel(dataManager, schedulerProvider);
    }

    @Provides
    STMTransitAdapter provideStmTransitAdapter(STMTransitFragment fragment) {
        return new STMTransitAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideStmTransitViewModel(STMTransitViewModel stmTransitViewModel) {
        return new ViewModelProviderFactory<>(stmTransitViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(STMTransitFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
