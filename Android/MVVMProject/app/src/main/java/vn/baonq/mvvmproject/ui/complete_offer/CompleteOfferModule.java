package vn.baonq.mvvmproject.ui.complete_offer;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class CompleteOfferModule {
    @Provides
    CompleteOfferViewModel completeOfferViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new CompleteOfferViewModel(dataManager, schedulerProvider);
    }
}
