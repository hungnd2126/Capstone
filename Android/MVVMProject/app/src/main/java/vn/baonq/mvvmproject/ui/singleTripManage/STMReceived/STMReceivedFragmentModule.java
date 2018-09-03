package vn.baonq.mvvmproject.ui.singleTripManage.STMReceived;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class STMReceivedFragmentModule {
    @Provides
    STMReceivedViewModel stmReceivedViewModel (DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new STMReceivedViewModel(dataManager, schedulerProvider);
    }

    @Provides
    STMReceivedAdapter provideStmReceivedAdapter(STMReceivedFragment fragment) {
        return new STMReceivedAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideStmReceivedViewModel(STMReceivedViewModel stmReceivedViewModel) {
        return new ViewModelProviderFactory<>(stmReceivedViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(STMReceivedFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
