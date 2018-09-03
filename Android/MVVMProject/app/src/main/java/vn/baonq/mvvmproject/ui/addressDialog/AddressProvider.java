package vn.baonq.mvvmproject.ui.addressDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddressProvider {
    @ContributesAndroidInjector(modules = AddressModule.class)
    abstract AddressDialog provideAddressDialogFactory();
}
