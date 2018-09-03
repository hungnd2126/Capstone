package vn.baonq.mvvmproject.ui.main.payment.api;

import android.content.Context;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.ui.main.payment.bean.SendOrderBean;
import vn.baonq.mvvmproject.utils.Constant;

public class SendOrderRequest {
    private SendOrderRequestOnResult sendOrderRequestOnResult;

    public void execute(final Context pContext, SendOrderBean sendOrderBean) {
        RequestParams lvParams = new RequestParams();
        lvParams.put("func", sendOrderBean.getFunc());
        lvParams.put("version", sendOrderBean.getVersion());
        lvParams.put("merchant_id", sendOrderBean.getMerchantID());
        lvParams.put("merchant_account", sendOrderBean.getMerchantAccount());
        lvParams.put("order_code", sendOrderBean.getOrderCode());
        lvParams.put("total_amount", sendOrderBean.getTotalAmount());
        lvParams.put("currency", sendOrderBean.getCurrency());
        lvParams.put("language", sendOrderBean.getLanguage());
        lvParams.put("return_url", sendOrderBean.getReturnUrl());
        lvParams.put("cancel_url", sendOrderBean.getCancelUrl());
        lvParams.put("notify_url", sendOrderBean.getNotifyUrl());
        lvParams.put("buyer_fullname", sendOrderBean.getBuyerFullName());
        lvParams.put("buyer_email", sendOrderBean.getBuyerEmail());
        lvParams.put("buyer_mobile", sendOrderBean.getBuyerMobile());
        lvParams.put("buyer_address", sendOrderBean.getBuyerAddress());
        lvParams.put("checksum", sendOrderBean.getChecksum());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.TIMEOUT);
        client.post(Constant.MAIN_URL, lvParams, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (sendOrderRequestOnResult != null) {
                    String content = pContext.getString(R.string.session_timeout);
                    sendOrderRequestOnResult.onSendOrderRequestOnResult(false, content);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                try {
                    if (sendOrderRequestOnResult != null) {
                        sendOrderRequestOnResult.onSendOrderRequestOnResult(true, content);
                    }
                } catch (NullPointerException e) {
                    if (sendOrderRequestOnResult != null) {
                        sendOrderRequestOnResult.onSendOrderRequestOnResult(false, content);
                    }
                } catch (Exception e) {
                    if (sendOrderRequestOnResult != null) {
                        sendOrderRequestOnResult.onSendOrderRequestOnResult(false, content);
                    }
                } catch (OutOfMemoryError e) {
                    if (sendOrderRequestOnResult != null) {
                        sendOrderRequestOnResult.onSendOrderRequestOnResult(false, content);
                    }
                }
            }

        });
    }

    public void getSendOrderRequestOnResult(SendOrderRequestOnResult sendOrderRequestOnResult) {
        this.sendOrderRequestOnResult = sendOrderRequestOnResult;
    }

    public interface SendOrderRequestOnResult {
        void onSendOrderRequestOnResult(boolean result, String data);
    }
}
