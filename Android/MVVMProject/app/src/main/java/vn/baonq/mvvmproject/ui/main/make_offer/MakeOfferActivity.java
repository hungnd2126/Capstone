package vn.baonq.mvvmproject.ui.main.make_offer;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.joda.time.DateTime;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.OfferViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.databinding.ActivityMakeOfferBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryAdapter;
import vn.baonq.mvvmproject.ui.singleTripManage.offered.OfferedAdapter;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;

import static vn.baonq.mvvmproject.utils.AppAction.KEY_ACTION;

public class MakeOfferActivity extends BaseActivity<ActivityMakeOfferBinding, MakeOfferViewModel> implements MakeOfferNavigator {
    public static final String TAG = MakeOfferActivity.class.getSimpleName();
    private String ACTION = "action";
    private String MAKE_OFFER = "make-offer";
    private String UPDATE_OFFER = "update-offer";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MakeOfferActivity.class);
        return intent;
    }

    @Inject
    ViewModelProvider.Factory vmFactory;

    @Inject
    MakeOfferViewModel makeOfferViewModel;

    ActivityMakeOfferBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_make_offer;
    }

    @Override
    public MakeOfferViewModel getViewModel() {
        return makeOfferViewModel;
    }

    @Override
    public void onClickMakeOffer() {
        OfferViewModel offerViewModel = new OfferViewModel();
        offerViewModel.setShippingFee(binding.ShippingFee.getText().toString());
        offerViewModel.setDeliveryDate(deliveryDate);
        offerViewModel.setMessage(binding.Message.getText().toString());
        ProcessBar.runProgress(this);
        if (ACTION.equals(MAKE_OFFER)) {
            offerViewModel.setPostId(postViewModel.getId());
            offerViewModel.setTripId(trip.getTripId());
            makeOfferViewModel.makeOfferApiCall(offerViewModel);
        }else {
            offerViewModel.setId(postViewModel.getId());
            makeOfferViewModel.updateOfferApiCall(offerViewModel);
        }
    }

    @Override
    public void onCompleteRequest(boolean isSuccess){
        if (isSuccess) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("offerSuccess", true);
            startActivity(intent);
            finish();
        }else {
            Toasty.error(this, "Tạo đề nghị không thành công").show();
        }

    }

    String deliveryDate = DateTime.now().toString("dd/MM/yyyy");

    GetTripByUserResponse.TripItemResponse trip;
    PostViewModel postViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        ProcessBar.runProgress(this);
        makeOfferViewModel.setNavigator(this);
        receiveIntent();
        getSupportActionBar().show();
        makeOfferViewModel.setData(postViewModel,trip);
        binding.editText.setText(postViewModel.getDeliveryDate());
        binding.ShippingFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String shippingFee = binding.ShippingFee.getText().toString();
                    if (shippingFee.length() == 0) shippingFee = "0";
                    int total = Integer.valueOf(shippingFee) + postViewModel.getPrice() * postViewModel.getQuantity();
                    makeOfferViewModel.shippingfeeN.set(CommonUtils.formatPrice(Integer.valueOf(shippingFee)));
                    makeOfferViewModel.total.set(CommonUtils.formatPrice(total));
                } catch (NumberFormatException e) {
                    Toasty.error(getApplicationContext(), "Số tiền không phù hợp").show();
                }
            }
        });
        ProcessBar.endProgress();
    }

    @Override
    public void onClickDateTimePicker() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                String strMonth = (monthOfYear < 10) ? "0" + monthOfYear : String.valueOf(monthOfYear);
                String strDay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                deliveryDate = strDay + "/" + strMonth + "/" + year;
                postViewModel.setDeliveryDate(deliveryDate);
                binding.editText.setText(deliveryDate);
            }
        };

        SpinnerDatePickerDialogBuilder builder = new SpinnerDatePickerDialogBuilder();
        builder.context(MakeOfferActivity.this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .callback(onDateSetListener)
                .defaultDate(DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth())
                .maxDate(2050, 1, 1)
                .minDate(DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth())
                .build()
                .show();

    }

    private void receiveIntent() {
        String callFrom = getIntent().getStringExtra("callFrom");
        if (callFrom != null && callFrom.equals(DeliveryAdapter.TAG)) {
            Bundle bundle = getIntent().getBundleExtra("data");
            trip = (GetTripByUserResponse.TripItemResponse) bundle.getSerializable("Trip");
            postViewModel = (PostViewModel) bundle.getSerializable("Post");
            getSupportActionBar().setTitle("Tạo đề nghị");
            ACTION = MAKE_OFFER;
        } else if (callFrom != null && callFrom.equals(OfferedAdapter.TAG)) {
            Bundle bundle = getIntent().getBundleExtra("data");
            postViewModel = (PostViewModel) bundle.getSerializable("Post");
            getSupportActionBar().setTitle("Cập nhật đề nghị");
            binding.btnSubmit.setText("Cập nhật");
            ACTION = UPDATE_OFFER;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
