package vn.baonq.mvvmproject.ui.addpost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.request.CreatePostRequest;
import vn.baonq.mvvmproject.databinding.ActivityAddpostBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.AppError;
import vn.baonq.mvvmproject.utils.BindingUtils;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.Commons;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class AddPostViewModel extends BaseViewModel<AddPostNavigator> {
    public static final String TAG = AddPostViewModel.class.getSimpleName();
    String encodedImage;
    String domain;
    String url;


    public AddPostViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public void onNextClick(int page, int action) {
        getNavigator().showPart(page, action);
    }

    public void onUploadClick() {
        getNavigator().onUploadImage();
    }

    public void onCompleteClick() {
        getNavigator().createPost();
    }

    public void getTime() {
        getNavigator().onClickDateTimePicker();
    }

    public void onClickPlace(int i) {
        getNavigator().onClickPlace(i);
    }

    public void crawlData(String url, ActivityAddpostBinding binding) {
        getCompositeDisposable().add(getDataManager()
                .crawlData(url)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        baseResponse -> {
                            binding.productPrice.setText(String.valueOf((long) baseResponse.productPrice));
                            binding.productName.setText(baseResponse.getProductName());
                            binding.buyFromAuto.setText(baseResponse.getCountry());
                            domain = baseResponse.getDomain();
                            this.url = baseResponse.getUrl();
                            DownloadImageTask downloadImageTask = new DownloadImageTask(binding);
                            downloadImageTask.execute(baseResponse.imageURL);
                        }, throwable -> {
                            Log.d(TAG, "Crawl Post Error");
                        }
                ));
    }

    public void createPost(PostViewModel request) {
        getCompositeDisposable().add(getDataManager()
                .doCreatePost(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        baseResponse -> {
                            boolean isPaid = true;
                            if (baseResponse.getStatusCode().equals(AppError.HET_TIEN)) {
                                isPaid = false;
                            }
                            getDataManager().setAmout(baseResponse.getCurrent_money());
                            ProcessBar.endProgress();
                            getNavigator().finishActivity(isPaid);
                        }, throwable -> {
                            ProcessBar.endProgress();
                            Log.d(TAG, "Create Post Error");
                        }
                ));
    }

    public void updatePost(PostViewModel request) {
        getCompositeDisposable().add(getDataManager()
                .doUpdatePost(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        baseResponse -> {
                            boolean isPaid = true;
                            if (baseResponse.getStatusCode().equals(AppError.HET_TIEN)) {
                                isPaid = false;
                            }
                            getDataManager().setAmout(baseResponse.getCurrent_money());
                            ProcessBar.endProgress();
                            getNavigator().finishActivity(isPaid);
                        }, throwable -> {
                            ProcessBar.endProgress();
                            Log.d(TAG, "Update Post Error");
                        }
                ));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ActivityAddpostBinding binding;

        public DownloadImageTask(ActivityAddpostBinding binding) {
            this.binding = binding;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            binding.productImage.setImageBitmap(result);
        }

    }

}
