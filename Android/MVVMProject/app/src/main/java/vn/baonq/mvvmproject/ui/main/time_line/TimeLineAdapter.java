package vn.baonq.mvvmproject.ui.main.time_line;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TimelineViewModel;
import vn.baonq.mvvmproject.databinding.ActivityTimelineBinding;
import vn.baonq.mvvmproject.databinding.ItemTimelineBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.main.message.model.Dialog;
import vn.baonq.mvvmproject.ui.map.MapActivity;

public class TimeLineAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<TimelineViewModel> timelineViewModels;
    private PostViewModel postViewModel;
    private Context context;
    public TimeLineAdapter(List<TimelineViewModel> viewModels, Context context){
        this.timelineViewModels = viewModels;
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTimelineBinding itemTimelineBinding = ItemTimelineBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new TimeLineViewHolder(itemTimelineBinding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }
    public void addItem(TimelineViewModel item) {
        timelineViewModels.add(item);
        notifyDataSetChanged();
    }
    private List<TimelineViewModel>cloneTimeline(){
        List<TimelineViewModel> listResult= new ArrayList<TimelineViewModel>();
        for (TimelineViewModel item: timelineViewModels) {
            try {
                listResult.add((TimelineViewModel)item.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return listResult;
    }
    @Override
    public int getItemCount() {
        if (timelineViewModels != null && timelineViewModels.size() > 0) {
            return  timelineViewModels.size();
        } else {
            return 0;
        }
    }
    public void addPost(PostViewModel item) {
        postViewModel=item;
    }
    public void addItems(List<TimelineViewModel> items) {
        timelineViewModels.addAll(items);
        notifyDataSetChanged();
    }
    public void clearItems() {
        timelineViewModels.clear();
    }
    public class TimeLineViewHolder extends BaseViewHolder implements TimeLineItemViewModel.TimeLineItemListener{
        private ItemTimelineBinding binding;
        private TimeLineItemViewModel timeLineItemViewModel;
        private int position;

        public TimeLineViewHolder(ItemTimelineBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position){
            if(timelineViewModels != null && timelineViewModels.size() > 0){
                this.position = position;
                TimelineViewModel model = timelineViewModels.get(position);
                if(position==0 && timelineViewModels.size()==1 ){
                    binding.from.setVisibility(View.GONE);
                    binding.to.setVisibility(View.GONE);
                }
                else if(position==0){
                        binding.from.setVisibility(View.GONE);
                    binding.to.setVisibility(View.VISIBLE);
                }else if(position==timelineViewModels.size()-1){
                    binding.to.setVisibility(View.GONE);
                    binding.from.setVisibility(View.VISIBLE);
                }else{
                    binding.to.setVisibility(View.VISIBLE);
                    binding.from.setVisibility(View.VISIBLE);
                }


                timeLineItemViewModel = new TimeLineItemViewModel(model, this);
                binding.setViewModel(timeLineItemViewModel);
                binding.executePendingBindings();
            }
        }

        @Override
        public void onItemClick() {
            if(timelineViewModels.get(position).getStatus()==1) {
                Intent intent = new Intent(context, MapActivity.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("Model",timelineViewModels.get(position));
                intent.putExtra("Data",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }
}