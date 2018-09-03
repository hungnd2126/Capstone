package vn.baonq.mvvmproject.ui.main.more;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoreProvider {
    @ContributesAndroidInjector(modules = MoreModule.class)
    abstract MoreFragment provideMoreFragmentFactory();

}
