package vn.baonq.mvvmproject.ui.main.more.noti;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Notification;
import vn.baonq.mvvmproject.utils.CommonUtils;

public class NotificationItemViewModel {
    //<editor-fold desc="properties">
    public final ObservableField<String> title;
    public final ObservableField<String> message;
    public final ObservableField<String> createdDate;
    public final ObservableField<String> isRead;
    //</editor-fold>

    public NotificationItemViewModel(Notification item) {
        title = new ObservableField<>(item.getTitle());
        createdDate = new ObservableField<>(item.getDateCreated());
        message = new ObservableField<>(item.getMessage());
        isRead = new ObservableField<>(item.isRead() + "");
    }
}
