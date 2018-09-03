package vn.baonq.mvvmproject.ui.main.home.Post;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.ui.main.offer.Offer;

public class PostViewModel extends ViewModel {
     int Id;
     String name;
     String imageUrl;
     String title, delivery_to, from,before;
     double reward, price;
     int offer_number;
     ArrayList<Offer> offers;


    public MutableLiveData<ArrayList<PostViewModel>> arrayListMutableLiveData= new MutableLiveData<>();

    public PostViewModel(Post post) {
        this.Id = post.Id;
        this.name= post.name;
        this.imageUrl= post.imageUrl;
        this.title= post.title; this.delivery_to= post.delivery_to;
        this.from=post.from;before= post.before;
        this.reward= post.reward;
        this.price= post.price;
        this.offer_number= post.offers == null ? 0 : post.offers.size();
        this.offers= post.offers;


    }

    public PostViewModel() {

    }
    public MutableLiveData<ArrayList<PostViewModel>> getArrayListMutableLiveData() {
        arrayList = new ArrayList<>();
        Post post= new Post(1,"hungnd","http://i.imgur.com/DvpvklR.png","Ỗ cứng SSD","HCM, Vietname","Texas,UK","Jun 12, 2018", 10,30,null);


        PostViewModel postViewModel= new PostViewModel(post);

       arrayList.add(postViewModel);
        arrayList.add(postViewModel);
        arrayList.add(postViewModel);
        arrayList.add(postViewModel);
        arrayList.add(postViewModel);
        postViewModel.setImageUrl("https://scontent.fsgn5-7.fna.fbcdn.net/v/t1.0-9/31530601_1854043604618555_8119004892716072960_n.jpg?_nc_cat=0&oh=f81eeaf2975cee3bd1fccfe869b5e9ce&oe=5BB6AE85");
        arrayList.add(postViewModel);
        arrayList.add(postViewModel);
        arrayListMutableLiveData.setValue(arrayList);


        return arrayListMutableLiveData;
    }

    @BindingAdapter({"loadUrl"})
    public static void loadImage(ImageView imageView,String imageUrl ){
         if (imageUrl != null) {
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_shopping);
        }


    }

    public ArrayList<PostViewModel> arrayList;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDelivery_to() {
        return delivery_to;
    }

    public void setDelivery_to(String delivery_to) {
        this.delivery_to = delivery_to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOffer_number() {
        return offer_number;
    }

    public void setOffer_number(int offer_number) {
        this.offer_number = offer_number;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public ArrayList<PostViewModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<PostViewModel> arrayList) {
        this.arrayList = arrayList;
    }
}
