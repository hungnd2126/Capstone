package vn.baonq.mvvmproject.ui.main.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.FragmentMoreBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.login.LoginActivity;
import vn.baonq.mvvmproject.ui.main.more.account.AccountActivity;
import vn.baonq.mvvmproject.ui.main.more.noti.NotiActivity;
import vn.baonq.mvvmproject.ui.main.more.profile.ProfileActivity;
import vn.baonq.mvvmproject.ui.main.more.phone.PhoneActivity;
import vn.baonq.mvvmproject.ui.main.more.credit.CreditActivity;
import vn.baonq.mvvmproject.ui.viewProfile.ViewProfileActivity;

public class MoreFragment extends BaseFragment<FragmentMoreBinding, MoreViewModel> implements MoreNavigator {
    public static final String TAG = MoreFragment.class.getSimpleName();

    @Inject
    MoreViewModel moreViewModel;

    public static MoreFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public MoreViewModel getViewModel() {
        return moreViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moreViewModel.setNavigator(this);
    }

    @Override
    public void openProfile() {
        Intent intent = ProfileActivity.newIntent(getActivity());
        startActivity(intent);

    }

    @Override
    public void openAccount(){
        Intent intent = ViewProfileActivity.newIntent(getActivity());
        intent.putExtra("userId", moreViewModel.getDataManager().getCurrentUser().getUid());
        startActivity(intent);
    }

    @Override
    public void openPhone(){
        Intent intent = PhoneActivity.newIntent(getActivity());
        startActivity(intent);
    }

    @Override
    public void openCredit(){
        Intent intent = CreditActivity.newIntent(getActivity());
        startActivity(intent);
    }

    @Override
    public void openNoti(){
        Intent intent = NotiActivity.newIntent(getActivity());
        startActivity(intent);
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(getActivity());
        startActivity(intent);
    }

    @Override
    public void handleError(Throwable error) {

    }

}
