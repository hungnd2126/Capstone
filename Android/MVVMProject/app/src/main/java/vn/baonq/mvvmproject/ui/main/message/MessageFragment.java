package vn.baonq.mvvmproject.ui.main.message;

import android.os.Bundle;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.databinding.FragmentMessageBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;

public class MessageFragment extends BaseFragment<FragmentMessageBinding, MessageViewModel> implements MessageNavigator {

    public static MessageFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public MessageViewModel getViewModel() {
        return null;
    }
}
