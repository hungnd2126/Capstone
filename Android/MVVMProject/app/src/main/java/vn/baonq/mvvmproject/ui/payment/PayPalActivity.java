package vn.baonq.mvvmproject.ui.payment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.common.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.databinding.ActivityPaypalPaymentBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class PayPalActivity extends BaseActivity<ActivityPaypalPaymentBinding,PaypalViewModel> implements PaypalNavigator {
    @Inject
    PaypalViewModel paypalViewModel;
    ActivityPaypalPaymentBinding paymentBinding;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return  R.layout.activity_paypal_payment;
    }

    @Override
    public PaypalViewModel getViewModel() {
        return paypalViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        paymentBinding= getViewDataBinding();
        receiveIntent();
        WebView webView= paymentBinding.webview;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        String postData="";
        try {
             postData = "Total=" + URLEncoder.encode(""+offerResponse.getTotal(), "UTF-8") +
                     "&TravelerId=" + URLEncoder.encode(""+offerResponse.getTravelerId(), "UTF-8")
                     +"&ShippingFee=" + URLEncoder.encode(""+(long)offerResponse.getShippingFee(), "UTF-8")
                     +"&OfferId=" + URLEncoder.encode(""+offerResponse.getOfferId(), "UTF-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        webView.postUrl(BASE_URL+"/checkouts/new",postData.getBytes());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest (WebView view, String url) {
                URL aURL = null;
                if(url.equals(BASE_URL+"/checkouts")){

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("currentMenu", "1");
                startActivity(intent);}
                return null;
            }
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {
                URL aURL = null;
                try {
                    aURL = new URL(request.toString());
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    int n = 0;
                    char[] buffer = new char[1024 * 4];
                    InputStreamReader reader = new InputStreamReader(is, "UTF8");
                    StringWriter writer = new StringWriter();
                    while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
                    writer.toString();
                    Log.d("respone", "asd");
                    String html = URLDecoder.decode(request.toString(), "UTF-8").substring(9);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            public void onPageStarted(
                    WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });
    }
    GetListOfferOnPostResponse.OfferResponse offerResponse;
    private void receiveIntent(){
        Bundle bundle= getIntent().getBundleExtra("Offer");
        offerResponse= (GetListOfferOnPostResponse.OfferResponse) bundle.getSerializable("Offer");

    }
}
