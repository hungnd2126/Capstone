package vn.baonq.mvvmproject.ui.main.more.credit;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.request.RequestedVMRequest;
import vn.baonq.mvvmproject.data.model.api.request.UpdateCreditRequest;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class CreditViewModel extends BaseViewModel<CreditNavigator> implements Serializable{
    public static final String TAG = CreditViewModel.class.getSimpleName();
    public ObservableField<String> amount = new ObservableField<>();
    private final ObservableList<HistoryViewModel> historyObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<HistoryViewModel>> historyLiveData;

    public CreditViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        historyLiveData = new MutableLiveData<>();

    }

    public void napTien() {
        getNavigator().napTien();
    }

    public void rutTien() {
        getNavigator().rutTien();
    }

    public void getAmount() {
        amount.set(CommonUtils.formatPrice(getDataManager().getAmount()));
    }

    public void addHistoryItem(List<HistoryViewModel> posts) {
        historyObservableList.clear();
        historyObservableList.addAll(posts);
    }

    public ObservableList<HistoryViewModel> getHistoryObservableList() {
        return historyObservableList;
    }

    public void getData() {
        getCompositeDisposable().add(getDataManager()
                .getHistory()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.size() > 0) {
                        historyLiveData.setValue(response);
                    }

                }, throwable -> {
                    Log.d(TAG, "Error");
                }));

        getCompositeDisposable().add(getDataManager()
                .getMoney()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        try{
                            getDataManager().setAmout(Double.parseDouble(response));
                            getAmount();
                        }catch (Exception e){
                            throw e;
                        }
                    }
                    ProcessBar.endProgress();
                }, throwable -> {
                    Log.d(TAG, "Error");
                    ProcessBar.endProgress();
                }));
    }





    public MutableLiveData<List<HistoryViewModel>> getPostLiveData() {
        return historyLiveData;
    }
}
