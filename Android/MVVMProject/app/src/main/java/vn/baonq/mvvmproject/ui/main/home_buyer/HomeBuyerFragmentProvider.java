package vn.baonq.mvvmproject.ui.main.home_buyer;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeBuyerFragmentProvider {
    @ContributesAndroidInjector(modules = HomeBuyerFragmentModule.class)
    abstract HomeBuyerFragment provideHomeBuyerFragmentFactory();
}