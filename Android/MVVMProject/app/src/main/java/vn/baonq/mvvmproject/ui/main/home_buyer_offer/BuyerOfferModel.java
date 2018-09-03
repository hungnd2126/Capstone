package vn.baonq.mvvmproject.ui.main.home_buyer_offer;

import vn.baonq.mvvmproject.data.model.db.Item;

public class BuyerOfferModel {
    String title, item_Url;

    public BuyerOfferModel(String title, String item_Url) {
        this.title = title;
        this.item_Url = item_Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItem_Url() {
        return item_Url;
    }

    public void setItem_Url(String item_Url) {
        this.item_Url = item_Url;
    }

    public BuyerOfferModel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return false;
    }

}
