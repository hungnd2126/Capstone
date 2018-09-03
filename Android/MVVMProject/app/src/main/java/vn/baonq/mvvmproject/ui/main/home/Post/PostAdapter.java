package vn.baonq.mvvmproject.ui.main.home.Post;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.PostBinding;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.CustomView> {
    private ArrayList<PostViewModel> arrayList;
    private Context context;
    private LayoutInflater layoutInflater;
    public PostAdapter(Context context, ArrayList<PostViewModel> arrayList){
        this.context= context;
        this.arrayList= arrayList;

    }
    @Override
    public CustomView onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layoutInflater ==null){
            layoutInflater= LayoutInflater.from(parent.getContext());

        }
        PostBinding postBinding= DataBindingUtil.inflate(layoutInflater, R.layout.layout_item,parent,false);

        return new CustomView(postBinding);
    }

    @Override
    public void onBindViewHolder(CustomView holder, int position) {
        PostViewModel postViewModel= arrayList.get(position);
        holder.bind(postViewModel, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CustomView extends RecyclerView.ViewHolder{
       private PostBinding postBinding;

       public CustomView(PostBinding postBinding){
            super(postBinding.getRoot());
            this.postBinding= postBinding;
        }

        public void bind(PostViewModel postViewModel, int position){

           this.postBinding.setPostmodel(postViewModel);
           this.postBinding.setHandler(new PostNavigator() {
               @Override
               public void viewDetail() {
                   Log.d("item position", position+"");
                   Intent myIntent = new Intent(context,DetailActivity.class);
                   context.startActivity(myIntent);
               }
               @Override
               public void makeOffer() {
                   Log.d("item position", position+"");
                   Intent myIntent = new Intent(context,PaymentActivity.class);
                   context.startActivity(myIntent);
               }
           });
           postBinding.executePendingBindings();

        }

        public PostBinding getPostBinding() {
            return postBinding;
        }
    }

}
