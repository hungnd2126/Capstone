package vn.baonq.mvvmproject.ui.main.home.Post;

import java.util.ArrayList;

import vn.baonq.mvvmproject.ui.main.offer.Offer;

public class Post {
    int Id;
    String name;
    String imageUrl;
    String title, delivery_to, from, before;
    double reward, price;
    ArrayList<Offer> offers;


    public Post() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDelivery_to() {
        return delivery_to;
    }

    public void setDelivery_to(String delivery_to) {
        this.delivery_to = delivery_to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public Post(int id, String name, String imageUrl, String title, String delivery_to, String from, String before, double reward, double price, ArrayList<Offer> offers) {
        Id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.title = title;
        this.delivery_to = delivery_to;
        this.from = from;
        this.before = before;
        this.reward = reward;
        this.price = price;
        this.offers = offers;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

