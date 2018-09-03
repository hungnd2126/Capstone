package vn.baonq.mvvmproject.ui.main.delivery;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DeliveryFragmentProvider {
    @ContributesAndroidInjector(modules = DeliveryFragmentModule.class)
    abstract DeliveryFragment provideDeliveryFragmentFactory();
}
