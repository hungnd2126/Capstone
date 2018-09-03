package vn.baonq.mvvmproject.ui.main.order;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OrderFragmentProvider {
    @ContributesAndroidInjector(modules = OrderFragmentModule.class)
    abstract OrderFragment provideOrderFragmentFactory();
}
