package vn.baonq.mvvmproject.ui.singleTripManage.STMReceived;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class STMReceivedProvider {
    @ContributesAndroidInjector(modules = STMReceivedFragmentModule.class)
    abstract  STMReceivedFragment provideSTMReceivedFragmentFactory();
}
