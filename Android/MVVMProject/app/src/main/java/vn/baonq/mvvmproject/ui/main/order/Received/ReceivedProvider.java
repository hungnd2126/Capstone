package vn.baonq.mvvmproject.ui.main.order.Received;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ReceivedProvider {
    @ContributesAndroidInjector(modules = ReceivedFragmentModule.class)
    abstract  ReceivedFragment provideReceivedFragmentFactory();
}
