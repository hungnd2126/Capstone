package vn.baonq.mvvmproject.ui.singleTripManage.STMTransit;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class STMTransitProvider {
    @ContributesAndroidInjector(modules = STMTransitFragmentModule.class)
    abstract  STMTransitFragment provideSTMTransitFragmentFactory();
}
