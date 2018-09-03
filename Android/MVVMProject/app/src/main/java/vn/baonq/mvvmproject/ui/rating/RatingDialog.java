package vn.baonq.mvvmproject.ui.rating;

import android.support.v4.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Rating;
import vn.baonq.mvvmproject.databinding.RatingDialogBinding;
import vn.baonq.mvvmproject.ui.base.BaseDialog;

public class RatingDialog extends BaseDialog implements RatingNavigator {

    private static final String TAG = RatingDialog.class.getSimpleName();

    @Inject
    RatingViewModel ratingViewModel;

    private RatingDialogBinding binding;

    String userId;
    int bill_Id;
    int role;


    public static RatingDialog newInstance() {
        Bundle args = new Bundle();

        RatingDialog fragment = new RatingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rating_dialog, container, false);
        View view = binding.getRoot();
        AndroidSupportInjection.inject(this);
        binding.setViewModel(ratingViewModel);
        ratingViewModel.setNavigator(this);
        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void setPost(String userId, int bill_Id, int role) {
        this.userId = userId;
        this.bill_Id = bill_Id;
        this.role = role;
    }

    @Override
    public void doRating() {
        Rating rating = new Rating(userId, ((int) binding.ratingBarFeedback.getRating()), binding.txtRating.getText().toString(), bill_Id, role);
        ratingViewModel.doRatingApi(rating);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }
}
