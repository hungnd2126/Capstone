package vn.baonq.mvvmproject.ui.main.time_line;

import android.content.Context;
import android.databinding.ObservableField;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TimelineViewModel;

public class TimeLineItemViewModel {
    public final ObservableField<String> time;
    public final ObservableField<String> status= new ObservableField<>();
    public final ObservableField<String> place= new ObservableField<>();

    public final TimeLineItemViewModel.TimeLineItemListener timeLineItemListener;

    public TimeLineItemViewModel(TimelineViewModel model, TimeLineItemViewModel.TimeLineItemListener timeLineItemListener) {
        time = new ObservableField<>(model.getDateCreated());

        switch (model.getStatus())
        {
            case 1:
                status.set("Đang ở ");
                place.set(model.getDescription());
                break;
            case 2:
                status.set("Đã mua được hàng");
                break;
            case 3:
                status.set("Có thể giao hàng");
                break;
            case 4:
                status.set("Không thể mua hàng");
                break;
            case 5:
                status.set("Đơn hàng đã bị hủy");
                break;
            case 6:
                status.set("Giao hàng thành công");
                break;
        }

        this.timeLineItemListener = timeLineItemListener;
    }
    public void onItemClick() { timeLineItemListener.onItemClick();}

    public interface TimeLineItemListener {
        void onItemClick();
    }
}
