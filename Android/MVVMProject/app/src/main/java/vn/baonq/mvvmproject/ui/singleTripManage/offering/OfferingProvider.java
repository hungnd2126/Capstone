package vn.baonq.mvvmproject.ui.singleTripManage.offering;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OfferingProvider {
    @ContributesAndroidInjector(modules = OfferingFragmentModule.class)
    abstract  OfferingFragment provideOfferingFragmentFactory();
}
