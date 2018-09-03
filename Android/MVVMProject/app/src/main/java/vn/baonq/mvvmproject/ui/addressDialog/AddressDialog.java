package vn.baonq.mvvmproject.ui.addressDialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Rating;
import vn.baonq.mvvmproject.data.model.db.City;
import vn.baonq.mvvmproject.databinding.AddressDialogBinding;
import vn.baonq.mvvmproject.databinding.RatingDialogBinding;
import vn.baonq.mvvmproject.ui.base.BaseDialog;
import vn.baonq.mvvmproject.utils.CommonUtils;

public class AddressDialog extends BaseDialog implements AddressNavigator {

    private static final String TAG = AddressDialog.class.getSimpleName();

    @Inject
    AddressViewModel viewModel;

    private AddressDialogBinding binding;
    private AddressCallback callback;
    private ArrayList<City> dataSource;
    private City city;
    private String address;
    private String feild;
    private String title;

    public static AddressDialog newInstance() {
        Bundle args = new Bundle();

        AddressDialog fragment = new AddressDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.address_dialog, container, false);
        View view = binding.getRoot();
        AndroidSupportInjection.inject(this);
        binding.setViewModel(viewModel);
        setUpDataSource();
        viewModel.setNavigator(this);
        binding.Title.setText(title);
        binding.txtAddress.setText("");
        binding.txtCity.setText("");
        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);

    }

    public void setFeildReturn(String feild) {
        this.feild = feild;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCallback(AddressCallback callback) {
        this.callback = callback;
    }

    @Override
    public void doRating() {
        if (binding.txtAddress.getText() != null) {
            address = binding.txtAddress.getText().toString();
            callback.doSubmit(address, city, feild);
        }
        dismissDialog();
    }

    private void setUpDataSource() {
        if (dataSource == null || dataSource.size() == 0) {
            getDataFromAsset();
        }
        binding.txtCity.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.row_city, dataSource));
        binding.txtCity.setText("");
        binding.txtAddress.setText("");
        binding.txtCity.setOnItemClickListener((parent, view, position, id) -> city = (City) parent.getItemAtPosition(position));

    }

    private void getDataFromAsset() {
        dataSource = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(CommonUtils.loadJSONFromAsset(getActivity(), "seed/data.txt"));
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
    public void dismissDialog() {
        dismissDialog(TAG);
    }
}
