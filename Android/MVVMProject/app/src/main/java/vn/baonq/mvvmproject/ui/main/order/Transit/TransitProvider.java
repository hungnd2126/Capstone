package vn.baonq.mvvmproject.ui.main.order.Transit;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TransitProvider {
    @ContributesAndroidInjector(modules = TransitFragmentModule.class)
    abstract TransitFragment provideRequestedFragmentFactory();
}
