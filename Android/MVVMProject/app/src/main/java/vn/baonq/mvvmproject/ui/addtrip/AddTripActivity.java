package vn.baonq.mvvmproject.ui.addtrip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.db.City;
import vn.baonq.mvvmproject.databinding.ActivityAddtripBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class AddTripActivity extends BaseActivity<ActivityAddtripBinding, AddTripViewModel> implements AddTripNavigator {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddTripActivity.class);
        return intent;
    }

    @Inject
    AddTripViewModel addTripViewModel;

    private ActivityAddtripBinding binding;
    private ArrayList<City> dataSource;
    private City travel_from, travel_to;
    private String from_date, to_date;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_addtrip;
    }

    @Override
    public AddTripViewModel getViewModel() {
        return addTripViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        addTripViewModel.setNavigator(this);
        ProcessBar.runProgress(this);
        binding = getViewDataBinding();
        setUpDataSource();
        ProcessBar.endProgress();
    }

    private void setUpDataSource() {
        getDataFromAsset();

        binding.travelFrom.setAdapter(new ArrayAdapter<>(this, R.layout.row_city, dataSource));
        binding.travelFrom.setOnItemClickListener((parent, view, position, id) -> travel_from = (City) parent.getItemAtPosition(position));

        binding.travelTo.setAdapter(new ArrayAdapter<>(this, R.layout.row_city, dataSource));
        binding.travelTo.setOnItemClickListener((parent, view, position, id) -> travel_to = (City) parent.getItemAtPosition(position));

    }

    private void getDataFromAsset() {
        dataSource = new ArrayList<City>();
        try {
            JSONObject obj = new JSONObject(CommonUtils.loadJSONFromAsset(this, "seed/data.txt"));
            JSONArray jsonArray = obj.getJSONArray("list");
            City city;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String country = item.getString("country");
                String id = item.getString("geonameid");
                String name = item.getString("name");
                String subcountry = item.getString("subcountry");
                city = new City(country, id, name, subcountry);
                dataSource.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void submitForm() {
        ProcessBar.runProgress(this);
        addTripViewModel.CreateTrip(travel_from.getName() + " - " + travel_to.getName()
                , travel_from.getGeonameid(), travel_to.getGeonameid(), from_date, to_date);
    }

    @Override
    public void openSTMActivity() {
        finish();
    }

    @Override
    public void onClickDateTimePicker(int edText) {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                String strMonth = (monthOfYear < 10) ? "0" + monthOfYear : String.valueOf(monthOfYear);
                String strDay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                String date = strDay + "/" + strMonth + "/" + year;

                switch (edText) {
                    case 1:
                        from_date = date;
                        binding.fromDate.setText(from_date);
                        break;
                    case 2:
                        to_date = date;
                        binding.toDate.setText(to_date);
                        break;
                    default:
                        from_date = date;
                        binding.fromDate.setText(from_date);
                        break;
                }
            }
        };

        SpinnerDatePickerDialogBuilder builder = new SpinnerDatePickerDialogBuilder();
        builder.context(this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .callback(onDateSetListener)
                .defaultDate(2017, 1, 1)
                .maxDate(2050, 1, 1)
                .minDate(DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth())
                .build()
                .show();


    }
}
