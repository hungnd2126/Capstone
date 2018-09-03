package vn.baonq.mvvmproject.ui.main.accept_offer;

public class AcceptOfferModel {
    int Id;
    String travler_name, date_request, avatar_Url;
    int rate;
    boolean isExpandable;

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public AcceptOfferModel(int id, String travler_name, String date_request, String avatar_Url, int rate, boolean isExpandable) {
        Id = id;
        this.travler_name = travler_name;
        this.date_request = date_request;
        this.avatar_Url = avatar_Url;
        this.rate = rate;
        this.isExpandable = isExpandable;
    }

    public String getAvatar_Url() {
        return avatar_Url;
    }

    public void setAvatar_Url(String avatar_Url) {
        this.avatar_Url = avatar_Url;
    }

    public AcceptOfferModel() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTravler_name() {
        return travler_name;
    }

    public void setTravler_name(String travler_name) {
        this.travler_name = travler_name;
    }

    public String getDate_request() {
        return date_request;
    }

    public void setDate_request(String date_request) {
        this.date_request = date_request;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
