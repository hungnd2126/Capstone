package vn.baonq.mvvmproject.ui.main.accept_offer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;
import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse.OfferResponse;
import vn.baonq.mvvmproject.databinding.ActivityAcceptOfferPart3Binding;
import vn.baonq.mvvmproject.ui.main.Payment_Type.PaymentTypeActivity;
import vn.baonq.mvvmproject.ui.main.message.messages.CustomHolderMessagesActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentActivity;

public class AcceptOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<OfferResponse> acceptOfferModels;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private PostViewModel Post;
    private String SenderId;
    private String Avatar;
    private String NickName;
    public AcceptOfferAdapter(ArrayList<OfferResponse> acceptOfferModels, Context context) {
        this.acceptOfferModels = acceptOfferModels;
        this.context = context;

        for (int i = 0; i < acceptOfferModels.size(); i++) {
            expandState.append(i, false);
        }
    }
    public void addSender(String Id, String Avatar, String NickName) {
        SenderId= Id;
        this.Avatar=  Avatar;
        this.NickName= NickName;
    }
    public void setPost(PostViewModel post) {
        Post = post;
    }

    public void addItems(List<OfferResponse> items) {
        acceptOfferModels.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        acceptOfferModels.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ActivityAcceptOfferPart3Binding activityAcceptOfferPart3Binding = ActivityAcceptOfferPart3Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new AcceptOfferParentViewHolder(activityAcceptOfferPart3Binding, expandState);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (acceptOfferModels != null && acceptOfferModels.size() > 0) {
            AcceptOfferParentViewHolder viewHolder = (AcceptOfferParentViewHolder) holder;
            OfferResponse offerModel = acceptOfferModels.get(position);
            viewHolder.setIsRecyclable(false);
            viewHolder.Bind(offerModel, position);
        }

    }

    @Override
    public int getItemCount() {
        if (acceptOfferModels != null && acceptOfferModels.size() > 0) {
            return acceptOfferModels.size();
        } else {
            return 0;
        }

    }

    public class AcceptOfferParentViewHolder extends RecyclerView.ViewHolder implements AcceptOfferItemViewModel.AcceptOfferItemListener {

        private AcceptOfferItemViewModel acceptOfferItemViewModel;
        ActivityAcceptOfferPart3Binding binding;
        private SparseBooleanArray expandState;
        int position;
        public AcceptOfferParentViewHolder(ActivityAcceptOfferPart3Binding binding, SparseBooleanArray expandState) {
            super(binding.getRoot());
            this.binding = binding;
            this.expandState = expandState;
        }

        public void Bind(OfferResponse model, int position) {
            if (acceptOfferModels != null & acceptOfferModels.size() > 0) {
                this.position= position;
                binding.expandableLayout.setInRecyclerView(true);
                binding.expandableLayout.setExpanded(expandState.get(position));
                binding.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                    @Override
                    public void onPreOpen() {
                        createRotateAnimator(binding.layoutMain, 0f, 180f).start();
                        expandState.put(position, true);
                    }

                    @Override
                    public void onPreClose() {
                        createRotateAnimator(binding.layoutMain, 180f, 0f).start();
                        expandState.put(position, false);
                    }
                });
                binding.layoutMain.setRotation(expandState.get(position) ? 180f : 0f);
                binding.layoutMain.setOnClickListener(v -> {
                    binding.expandableLayout.toggle();
                    binding.expandableLayout.setDuration(0);
                });
                binding.expandableLayout.setInRecyclerView(true);
                acceptOfferItemViewModel = new AcceptOfferItemViewModel(model, this);
                if (model.getType() == 2){
                    binding.button2.setVisibility(View.GONE);
                }
                binding.ratingBar.setStepSize(0.01f);
                binding.setViewModel(acceptOfferItemViewModel);
                binding.executePendingBindings();
            }

        }
        @Override
        public void onSendMessage() {
            Intent intent= new Intent(context,CustomHolderMessagesActivity.class);
            intent.putExtra("receiverId", acceptOfferModels.get(position).getTravelerId());
            intent.putExtra("receiverName", acceptOfferModels.get(position).getTravelerName());
            intent.putExtra("receiverAvatar", acceptOfferModels.get(position).getTravelerImage());

            intent.putExtra("SenderId",SenderId);
            intent.putExtra("Avatar",Avatar);
            intent.putExtra("NickName",NickName);
            context.startActivity(intent);
        }

        @Override
        public void onAcceptOffer(int offerId, String  total) {
            Intent intent= new Intent(context, PaymentTypeActivity.class);
            Bundle bundle= new Bundle();
            OfferResponse offerResponse=acceptOfferModels.get(position);
            offerResponse.setTotal(total);
            offerResponse.setPostId(Post.getId());
            bundle.putSerializable("Offer",offerResponse);
            bundle.putSerializable("Post",Post);
            intent.putExtra("Data", bundle);
            context.startActivity(intent);

        }

        public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
            animator.setDuration(0);
            animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
            return animator;
        }
    }
}

