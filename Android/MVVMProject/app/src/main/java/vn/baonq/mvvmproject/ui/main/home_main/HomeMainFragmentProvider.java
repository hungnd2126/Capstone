package vn.baonq.mvvmproject.ui.main.home_main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class HomeMainFragmentProvider {
    @ContributesAndroidInjector(modules = HomeMainFragmentModule.class)
    abstract HomeMainFragment provideHomeMainFragmentFactory();
}
