package vn.baonq.mvvmproject.ui.main.message.dialogs;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitFragment;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class DialogModule {

    @Provides
    ViewModelProvider.Factory orderViewModelProvider(DialogViewModel dialogViewModel) {
        return new ViewModelProviderFactory<>(dialogViewModel);
    }

    @Provides
    DialogViewModel dialogViewModel(DataManager dataManager,
                                        SchedulerProvider schedulerProvider) {
        return new DialogViewModel(dataManager, schedulerProvider);}
    @Provides
    DialogAdapter provideTransitAdapter(DialogFragment fragment){
        return new DialogAdapter(new ArrayList<>(), fragment.getActivity());
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(DialogFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
