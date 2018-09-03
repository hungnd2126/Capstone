package vn.baonq.mvvmproject.ui.main.payment.bean;

/**
 * Created by DucChinh on 6/13/2016.
 */
public class SendOrderBean {
    private String mFunc;
    private String mVersion;
    private String mMerchantID;
    private String mMerchantAccount;
    private String mOrderCode;
    private int mTotalAmount;
    private String mCurrency;
    private String mLanguage;
    private String mReturnUrl;
    private String mCancelUrl;
    private String mNotifyUrl;
    private String mBuyerFullName;
    private String mBuyerEmail;
    private String mBuyerMobile;
    private String mBuyerAddress;
    private String mChecksum;

    public SendOrderBean() {

    }

    public void setFunc(String pFunc) {
        this.mFunc = pFunc;
    }

    public String getFunc() {
        return mFunc;
    }

    public void setVersion(String pVersion) {
        this.mVersion = pVersion;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setMerchantID(String pMerchantID) {
        this.mMerchantID = pMerchantID;
    }

    public String getMerchantID() {
        return mMerchantID;
    }

    public void setMerchantAccount(String pMerchantAccount) {
        this.mMerchantAccount = pMerchantAccount;
    }

    public String getMerchantAccount() {
        return mMerchantAccount;
    }

    public void setOrderCode(String pOrderCode) {
        this.mOrderCode = pOrderCode;
    }

    public String getOrderCode() {
        return mOrderCode;
    }

    public void setTotalAmount(int pTotalAmount) {
        this.mTotalAmount = pTotalAmount;
    }

    public int getTotalAmount() {
        return mTotalAmount;
    }

    public void setCurrency(String pCurrency) {
        this.mCurrency = pCurrency;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setLanguage(String pLanguage) {
        this.mLanguage = pLanguage;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setReturnUrl(String pReturnUrl) {
        this.mReturnUrl = pReturnUrl;
    }

    public String getReturnUrl() {
        return mReturnUrl;
    }

    public void setCancelUrl(String pCancelUrl) {
        this.mCancelUrl = pCancelUrl;
    }

    public String getCancelUrl() {
        return mCancelUrl;
    }

    public void setNotifyUrl(String pNotifyUrl) {
        this.mNotifyUrl = pNotifyUrl;
    }

    public String getNotifyUrl() {
        return mNotifyUrl;
    }

    public void setBuyerFullName(String pBuyerFullName) {
        this.mBuyerFullName = pBuyerFullName;
    }

    public String getBuyerFullName() {
        return mBuyerFullName;
    }

    public void setBuyerEmail(String pBuyerEmail) {
        this.mBuyerEmail = pBuyerEmail;
    }

    public String getBuyerEmail() {
        return mBuyerEmail;
    }

    public void setBuyerMobile(String pBuyerMobile) {
        this.mBuyerMobile = pBuyerMobile;
    }

    public String getBuyerMobile() {
        return mBuyerMobile;
    }

    public void setBuyerAddress(String pBuyerAddress) {
        this.mBuyerAddress = pBuyerAddress;
    }

    public String getBuyerAddress() {
        return mBuyerAddress;
    }

    public void setChecksum(String pChecksum) {
        this.mChecksum = pChecksum;
    }

    public String getChecksum() {
        return mChecksum;
    }
}
