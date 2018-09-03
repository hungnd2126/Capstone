package vn.baonq.mvvmproject.ui.main.more.credit;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitAdapter;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitFragment;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class CreditActivityModule {
    @Provides
    CreditViewModel provideCreditViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new CreditViewModel(dataManager, schedulerProvider);
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(CreditActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    CreditItemAdapter provideCreditItemAdapter(){
        return new CreditItemAdapter(new ArrayList<>());
    }
}
