package vn.baonq.mvvmproject.ui.main.home;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.db.Item;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedItemViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class HomeItemViewModel {

    public final ObservableField<String> shippingFee ,quantity, price,description,ImageUrl,dateUpdated,
            dateCreated,deliveryTo,buyFrom,productName,OfferSize,
            Username,UserAvartar, deposit,deliveryDate ;
    public final ObservableField<Integer>   id;
    public final HomeItemViewModel.HomeItemListener homeItemListener;

    public HomeItemViewModel(PostViewModel post, HomeItemViewModel.HomeItemListener homeItemListener) {
        id = new ObservableField<>(post.getId());
        shippingFee = new ObservableField<>(CommonUtils.formatPrice(post.getShippingFee()));
        quantity= new ObservableField<>(String.valueOf(post.getQuantity()));
        price= new ObservableField<>(CommonUtils.formatPrice(post.getPrice()));
        description= new ObservableField<>(post.getDescription());
        ImageUrl= new ObservableField<>(ApiEndPoint.BASE_URL+post.getImageUrl());
        dateUpdated= new ObservableField<>(post.getDateUpdated());
        dateCreated= new ObservableField<>(post.getDateCreated());
        deliveryTo= new ObservableField<>(post.getDeliveryTo());
        buyFrom= new ObservableField<>();
        if(post.getDomain()!=null&&!post.getDomain().equals("")){
            buyFrom.set(post.getDomain()+" - "+post.getBuyFrom());
        }else{
            buyFrom.set(post.getBuyFrom());
        }
        productName= new ObservableField<>(post.getProductName());
        OfferSize= new ObservableField<>(post.getOfferSize());
        Username = new ObservableField<>(post.getNickname());
        deposit = new ObservableField<>(CommonUtils.formatPrice(post.getDeposit()));
        UserAvartar= new ObservableField<>(ApiEndPoint.BASE_URL+post.getUserAvartar());
        deliveryDate= new ObservableField<>(post.getDeliveryDate());
        this.homeItemListener = homeItemListener;
    }

    public void viewDetail() { homeItemListener.viewDetail();}
    public void makeOffer() { homeItemListener.makeOffer();}

    public interface HomeItemListener{
        public void viewDetail();
        public void makeOffer();
    }
}
