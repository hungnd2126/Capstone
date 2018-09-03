package vn.baonq.mvvmproject.ui.main.make_offer;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class MakeOfferActivityModule {
    @Provides
    MakeOfferViewModel makeOfferViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new MakeOfferViewModel(dataManager, schedulerProvider);
    }
    @Provides
    ViewModelProvider.Factory factory(MakeOfferViewModel makeOfferViewModel) {
        return new ViewModelProviderFactory<>(makeOfferViewModel);
    }
}
