package vn.baonq.mvvmproject.ui.main.detail_order;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class DetailActivityModule {
    @Provides
    DetailViewModel provideDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new DetailViewModel(dataManager, schedulerProvider);
    }
    @Provides
    DetailOfferItemAdapter detailOfferItemAdapter(DetailActivity activity){
        return new DetailOfferItemAdapter(new ArrayList<>(), activity.getApplicationContext());
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(DetailActivity activity) {
        return new LinearLayoutManager(activity.getApplicationContext());
    }

    @Provides
    ViewModelProvider.Factory factory(DetailViewModel detailViewModel) {
        return new ViewModelProviderFactory<>(detailViewModel);
    }
}
