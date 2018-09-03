package vn.baonq.mvvmproject.ui.viewProfile;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.ReviewViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class ReviewItemViewModel {

    public final ObservableField<String> name;
    public final ObservableField<String> productName;
    public final ObservableField<String> createdDate;
    public final ObservableField<String> message;
    public final ObservableField<String> imageUrl;
    public final ObservableField<Integer> score;

    public ReviewItemViewModel(ReviewViewModel item){
        name = new ObservableField<>(item.getName());
        productName = new ObservableField<>("Đơn hàng : " + item.getProductName());
        createdDate = new ObservableField<>(CommonUtils.toDateFormat(item.getDateCreated(), "dd/MM/yyyy"));
        message = new ObservableField<>(item.getMessage());
        imageUrl = new ObservableField<>(BASE_URL + item.getImageUrl());
        score = new ObservableField<>(item.getScore());
    }


}
