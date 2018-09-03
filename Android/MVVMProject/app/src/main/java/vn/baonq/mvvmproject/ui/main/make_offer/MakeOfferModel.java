package vn.baonq.mvvmproject.ui.main.make_offer;

public class MakeOfferModel {
    String description;
    String delivery_fee;

    public MakeOfferModel(String description, String delivery_fee) {
        this.description = description;
        this.delivery_fee = delivery_fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }
}
