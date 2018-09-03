package vn.baonq.mvvmproject.ui.main.message.dialogs;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogFragmentProvider {
    @ContributesAndroidInjector(modules = DialogModule.class)
    abstract DialogFragment dialogFragment();
}
