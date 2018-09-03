package vn.baonq.mvvmproject.ui.addpost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.db.City;
import vn.baonq.mvvmproject.databinding.ActivityAddpostBinding;
import vn.baonq.mvvmproject.ui.addressDialog.AddressCallback;
import vn.baonq.mvvmproject.ui.addressDialog.AddressDialog;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class AddPostActivity extends BaseActivity<ActivityAddpostBinding, AddPostViewModel> implements AddPostNavigator, AddressCallback,
        HasSupportFragmentInjector {
    public static final String TAG = AddPostActivity.class.getSimpleName();
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private final int PICK_IMAGE = 1001;
    private final int NEXT_BUTTON = 1;
    public static final int ADD_NEW_ACTION = 1;
    public static final int UPDATE_ACTION = 2;
    private int ACTION;
    private final String BUY_TEXT = "buy_text";
    private final String DELIVERY_TEXT = "delivery_text";
    private boolean fill = false;
    private AddressDialog dialog;
    private ArrayList<String> dataSource;
    private ArrayList<String> countrySource;

    private String buy_from_country, delivery_to_country;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddPostActivity.class);
        return intent;
    }

    @Inject
    AddPostViewModel addPostViewModel;

    private ActivityAddpostBinding binding;
    private PostViewModel post;
    private String encodedImage;
    private Bitmap bitmap;
    private PostViewModel postvm;
    private String date = DateTime.now().toString("dd/MM/yyyy");

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_addpost;
    }

    @Override
    public AddPostViewModel getViewModel() {
        return addPostViewModel;
    }

    boolean search = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        addPostViewModel.setNavigator(this);
        binding = getViewDataBinding();
        post = new PostViewModel();
        dialog = AddressDialog.newInstance();
        dataSource = new ArrayList<>();
        countrySource = new ArrayList<>();
        getCountryFromAsset();
        receiveIntent();
        binding.buyFromAuto.setAdapter(new ArrayAdapter<>(this, R.layout.row_city, dataSource));
        binding.buyFromAuto.setOnItemClickListener((parent, view, position, id) -> buy_from_country = parent.getItemAtPosition(position).toString().trim());

        binding.switchDeposit.setOnCheckedChangeListener((buttonView, isChecked) -> binding.expandableLayout.toggle());
        binding.switchFill.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.expandableURL.toggle();
            fill = !fill;
            updateUI();
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (search == true) {
                        addPostViewModel.crawlData(binding.URL.getText().toString(), binding);
                        search = false;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        thread.start();
        crawData();
    }

    private void crawData() {
        binding.URL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.URL.getText().toString().equals("")) {
                    search = false;
                } else {
                    search = true;
                }
            }
        });
        binding.shippingFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTotal();
            }
        });
    }

    private void updateUI() {
        if (fill) {
            binding.buyFromLayout.setVisibility(View.GONE);
            binding.buyFrom.setVisibility(View.GONE);
          //  binding.dlAutoLayout.setVisibility(View.VISIBLE);
            binding.bfAutoLayout.setVisibility(View.VISIBLE);
        } else {
            binding.buyFromLayout.setVisibility(View.VISIBLE);
            binding.buyFrom.setVisibility(View.VISIBLE);
            //binding.dlAutoLayout.setVisibility(View.GONE);
            binding.bfAutoLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClickDateTimePicker() {
        DatePickerDialog.OnDateSetListener onDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            monthOfYear = monthOfYear + 1;
            String strMonth = (monthOfYear < 10) ? "0" + monthOfYear : String.valueOf(monthOfYear);
            String strDay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            date = strDay + "/" + strMonth + "/" + year;
            binding.receiveDate.setText(date);
            post.setDeliveryDate(date);
        };

        SpinnerDatePickerDialogBuilder builder = new SpinnerDatePickerDialogBuilder();
        builder.context(this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .callback(onDateSetListener)
                .defaultDate(DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth())
                .maxDate(2050, 1, 1)
                .minDate(DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth())
                .build()
                .show();
    }

    @Override
    public void showPart(int part, int action) {
        if (part == 1) {
            binding.addpostPart1.setVisibility(View.VISIBLE);
            binding.addpostPart2.setVisibility(View.GONE);
        } else if (part == 2) {
            if (!checkData(part, action)) {
                Toasty.normal(this, getString(R.string.error_lake_of_info)).show();
            } else {
                binding.addpostPart1.setVisibility(View.GONE);
                binding.addpostPart2.setVisibility(View.VISIBLE);
                updateTotal();
            }
        }
    }

    private void updateTotal(){
        String unitPrice = binding.productPrice.getText().toString();
        String quantity = binding.productQuantity.getText().toString();
        long unitP = 0, quan = 0, shippingFee;
        if (!unitPrice.equals("") && !quantity.equals("")){
            unitP = Integer.parseInt(unitPrice);
            quan = Integer.parseInt(quantity);
        }
        shippingFee = binding.shippingFee.getText().toString().equals("") ? 0 : Integer.parseInt(binding.shippingFee.getText().toString());
        binding.txtTotal.setText(CommonUtils.formatPrice(unitP *  quan  + shippingFee));
    }

    private boolean checkData(int part, int action) {
        if (part == 2 && action == NEXT_BUTTON) {
            if (ACTION == ADD_NEW_ACTION) {
                if (encodedImage == null && addPostViewModel.getEncodedImage() == null) {
                    return false;
                }
            }
            if (encodedImage != null) {
                post.setImageUrl(encodedImage);
            }
            if (addPostViewModel.getEncodedImage() != null) {
                post.setImageUrl(addPostViewModel.getEncodedImage());
            }
            if (binding.productPrice.getText().toString().equals("")
                    || binding.productQuantity.getText().toString().equals("")
                    || binding.productName.getText().toString().equals("")) {
                return false;
            }
            post.setPrice(Integer.valueOf(binding.productPrice.getText().toString()));
            post.setProductName(binding.productName.getText().toString());
            post.setDescription(binding.productDescription.getText().toString());
            post.setQuantity(Integer.valueOf(binding.productQuantity.getText().toString()));
        } else if (part == 3) {
            if (ACTION == ADD_NEW_ACTION) {
//                if (buy_from == null || delivery_to == null
//                        || binding.shippingFee.getText().toString().equals("")) {
//                    return false;
//                }
//                post.setDeliveryToGeoId(delivery_to.getGeonameid());
//                post.setBuyFromGeoId(buy_from.getGeonameid());
            } else if (ACTION == UPDATE_ACTION) {

            }
            post.setShippingFee(Integer.valueOf(binding.shippingFee.getText().toString()));
        }
        return true;
    }

    @Override
    public void finishActivity(boolean isPaid) {
        if (isPaid) {
            Toasty.success(this, "Đã đăng thành công", Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.normal(this, "Tài khoản không đủ để thanh toán trước", Toast.LENGTH_SHORT).show();
        }
        Intent intentMain = MainActivity.newIntent(this);
        intentMain.putExtra("currentMenu", "1");
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentMain);
        finish();
    }

    @Override
    public void onUploadImage() {
        if (!hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1002);
        }
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void updateCompleted(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    private void getCountryFromAsset() {
        try {
            String[] obj = CommonUtils.loadCountryFromAsset(this, "seed/country.txt").split("\n");
            City city;
            for (int i = 0; i < obj.length; i++) {
                // city = new City(obj[i], "", "", "");
                dataSource.add(obj[i].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createPost() {
        if (!checkData(3, NEXT_BUTTON)) {
            Toasty.normal(this, getString(R.string.error_lake_of_info)).show();
        } else {
            if (binding.expandableLayout.isExpanded() && binding.TotalTranfer.getText() != null && !binding.TotalTranfer.getText().toString().equals("")) {
                post.setDeposit(Integer.valueOf(binding.TotalTranfer.getText().toString()));
            }
            if (ACTION == ADD_NEW_ACTION) {
                ProcessBar.runProgress(this);
                if (!fill) {
                    addPostViewModel.createPost(post);
                } else {
                    post.setBuy_Address(new vn.baonq.mvvmproject.data.model.db
                            .Address(addPostViewModel.getUrl(), addPostViewModel.getDomain(), binding.buyFromAuto.getText().toString().trim(), ""));
                    addPostViewModel.createPost(post);
                }
            } else if (ACTION == UPDATE_ACTION) {
                ProcessBar.runProgress(this);
                addPostViewModel.updatePost(post);
            }
        }
    }

    @Override
    public void onClickPlace(int i) {
        String title = "";
        if (i == 1) {
            dialog.setFeildReturn(BUY_TEXT);
            title = "Nơi mua";
        } else if (i == 2) {
            dialog.setFeildReturn(DELIVERY_TEXT);
            title = "Nơi nhận";
        }
        dialog.setTitle(title);
        dialog.setCallback(this);
        dialog.show(this.getSupportFragmentManager());
    }

    @Override
    public void runProgress() {

    }

    @Override
    public void endProgress() {
        ProcessBar.endProgress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProcessBar.runProgress(this);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = BitmapFactory.decodeFile(picturePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            if (bitmap != null) {
                binding.productImage.setImageBitmap(bitmap);
            }
        }
        ProcessBar.endProgress();

    }

    private void receiveIntent() {
        String callFrom = getIntent().getStringExtra("callFrom");
        if (callFrom != null && callFrom.equals("updateItem")) {
            ACTION = UPDATE_ACTION;
            postvm = (PostViewModel) getIntent().getSerializableExtra("Post");
            updateData();
        } else {
            ACTION = ADD_NEW_ACTION;
        }
    }

    private void updateData() {
        if (postvm != null) {
            post.setId(postvm.getId());
            binding.productQuantity.setText(String.valueOf((int) postvm.getQuantity()));
            binding.productPrice.setText(String.valueOf(postvm.getPrice()));
            binding.productDescription.setText(postvm.getDescription());
            binding.receiveDate.setText(postvm.getDeliveryDate());
            binding.productName.setText(postvm.getProductName());
            Glide.with(this).load(BASE_URL + postvm.getImageUrl()).into(binding.productImage);
            binding.buyFrom.setText(postvm.getBuy_Address().toString());
            binding.deliveryTo.setText(postvm.getDelivery_Address().toString());
            binding.shippingFee.setText(String.valueOf(postvm.getShippingFee()));
            post.setImageUrl(postvm.getImageUrl());
            post.setShippingFee(postvm.getShippingFee());
            post.setQuantity(postvm.getQuantity());
            post.setDeliveryDate(postvm.getDeliveryDate());
        }
    }

    //    private void clearForm(){
//        binding.productPrice.setText("");
//        binding.productQuantity.setText("");
//        binding.productName.setText("");
//        binding.productImage.
//    }

    @Override
    public void doSubmit(String address, City city, String field) {
        if (city != null) {
            if (field.equals(BUY_TEXT)) {
                binding.buyFrom.setText(address + ", " + city.toString());
                post.setBuy_Address(new vn.baonq.mvvmproject.data.model.db.Address(address, city.getName(), city.getCountry(), city.getGeonameid()));
            } else if (field.equals(DELIVERY_TEXT)) {
                binding.deliveryTo.setText(address + ", " + city.toString());
                post.setDelivery_Address(new vn.baonq.mvvmproject.data.model.db.Address(address, city.getName(), city.getCountry(), city.getGeonameid()));
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
