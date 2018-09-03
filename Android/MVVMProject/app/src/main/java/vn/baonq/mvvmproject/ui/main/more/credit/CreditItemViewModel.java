package vn.baonq.mvvmproject.ui.main.more.credit;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;

public class CreditItemViewModel {
    //<editor-fold desc="properties">
    public final ObservableField<String> name;
    public final ObservableField<String> value;
    public final ObservableField<String> createdDate;
    public final ObservableField<String> isSuccess;
    //</editor-fold>

    public CreditItemViewModel(HistoryViewModel item) {
        name = new ObservableField<>(item.getName());
        createdDate = new ObservableField<>(item.getCreateDate());
        value = new ObservableField<>(item.getDau() + " " + CommonUtils.formatPrice(Integer.valueOf(item.getValue())));
        isSuccess = new ObservableField<>(item.getIsSuccess());
    }
}
