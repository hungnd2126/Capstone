package vn.baonq.mvvmproject;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import io.reactivex.annotations.NonNull;

public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {

private V viewModel;
public ViewModelProviderFactory(V viewModel){
        this.viewModel = viewModel;
        }
@Override
public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.getClass())){
        return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
        }
        }
