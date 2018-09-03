package vn.baonq.mvvmproject.ui.main.payment.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.RequestedVMResponse;
import vn.baonq.mvvmproject.data.model.api.request.CreatePostRequest;
import vn.baonq.mvvmproject.ui.addpost.AddPostActivity;
import vn.baonq.mvvmproject.utils.Constant;

public class CheckOutActivity extends Activity {

    public static final String TOKEN_CODE = "token_code";
    public static final String CHECKOUT_URL = "checkout_url";

    private WebView webData;

    private String mTokenCode = "";
    private String mCheckoutUrl = "";
    private String ACTION;
    private String amount;

    private void receiveIntent() {
        try {
            amount = getIntent().getStringExtra("amount");
            ACTION = getIntent().getStringExtra("NEW_ACTION");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTokenCode = extras.getString(TOKEN_CODE, "");
            mCheckoutUrl = extras.getString(CHECKOUT_URL, "");
            receiveIntent();
        }

        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        webData = findViewById(R.id.activity_checkout_webView);
        webData.getSettings().setJavaScriptEnabled(true);
        webData.setWebChromeClient(new WebChromeClient());
        webData.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.equalsIgnoreCase(Constant.RETURN_URL)) {
                    Intent intentCheckOut = new Intent(getApplicationContext(), CheckOrderActivity.class);
                    intentCheckOut.putExtra(CheckOrderActivity.TOKEN_CODE, mTokenCode);
                    intentCheckOut.putExtra("NEW_ACTION", ACTION);
                    intentCheckOut.putExtra("amount", amount);
                    intentCheckOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentCheckOut);
                    finish();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        if (!mCheckoutUrl.equalsIgnoreCase("")) {
            webData.loadUrl(mCheckoutUrl);
        }
    }
}
