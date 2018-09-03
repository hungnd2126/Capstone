package vn.baonq.mvvmproject.ui.singleTripManage.offered;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OfferedProvider {
    @ContributesAndroidInjector(modules = OfferedFragmentModule.class)
    abstract  OfferedFragment provideReceivedFragmentFactory();
}
