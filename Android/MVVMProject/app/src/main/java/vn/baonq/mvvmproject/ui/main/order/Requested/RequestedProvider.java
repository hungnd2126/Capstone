package vn.baonq.mvvmproject.ui.main.order.Requested;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RequestedProvider {
    @ContributesAndroidInjector(modules = RequestedFragmentModule.class)
    abstract RequestedFragment provideRequestedFragmentFactory();
}
