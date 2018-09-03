package vn.baonq.mvvmproject.ui.main.order.Received;

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
public class ReceivedFragmentModule {

    @Provides
    ReceivedViewModel receivedViewModel (DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ReceivedViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ReceivedAdapter provideReceivedAdapter(ReceivedFragment fragment) {
        return new ReceivedAdapter(new ArrayList<>(), fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideReceivedViewModel(ReceivedViewModel receivedViewModel) {
        return new ViewModelProviderFactory<>(receivedViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(ReceivedFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
