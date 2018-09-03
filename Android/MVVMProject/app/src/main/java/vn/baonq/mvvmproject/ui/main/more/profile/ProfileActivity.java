package vn.baonq.mvvmproject.ui.main.more.profile;

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
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.db.City;
import vn.baonq.mvvmproject.data.model.db.UserDB;
import vn.baonq.mvvmproject.databinding.ActivityProfileBinding;
import vn.baonq.mvvmproject.ui.addressDialog.AddressCallback;
import vn.baonq.mvvmproject.ui.addressDialog.AddressDialog;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfileViewModel> implements ProfileNavigator,
        AddressCallback, HasSupportFragmentInjector {
    public static final String TAG = ProfileActivity.class.getSimpleName();
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    public static final int PICK_IMAGE = 1001;
    private String PROFILE_ADDRESS = "profile_address";

    @Inject
    ProfileViewModel profileViewModel;

    private AddressDialog dialog;
    private ActivityProfileBinding binding;
    private String encodedImage;
    private UserDB user;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        return profileViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        binding = getViewDataBinding();
        profileViewModel.setNavigator(this);
        dialog = AddressDialog.newInstance();
        initUI();
    }

    @Override
    public void saveProfile() {
        user.setTknl(binding.profileTknl.getText().toString());
        user.setBio(binding.profileBio.getText().toString());
        user.setName(binding.profileLastname.getText().toString());
        user.setPhone(binding.profilePhone.getText().toString());
        ProcessBar.runProgress(this);
        profileViewModel.UpdateInfo(encodedImage, user);
        ProcessBar.endProgress();
    }

    private void initUI() {
        ProcessBar.runProgress(this);
        user = profileViewModel.getDataManager().getCurrentUser();
        profileViewModel.setupInfo(user);
        ProcessBar.endProgress();
    }

    @Override
    public void onUploadImage() {
        if (!hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(ProfileActivity.this,
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

    @Override
    public void showAddressDialog() {
        dialog.setCallback(this);
        dialog.setFeildReturn(PROFILE_ADDRESS);
        dialog.show(this.getSupportFragmentManager());
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

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            if (bitmap != null) {
                binding.profileAvatar.setImageBitmap(bitmap);
            }
        }
        ProcessBar.endProgress();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void doSubmit(String address, City city, String field) {
        if (field != null && field.equals(PROFILE_ADDRESS)) {
            profileViewModel.address.set(address + ", " + city.toString());
            user.setAddress(address);
            user.setGeoId(city.getGeonameid());
        }
        dialog.dismiss();
    }
}
