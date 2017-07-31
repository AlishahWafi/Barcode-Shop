package com.example.barcodeshop.Models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RatingListView{

    private String image;
    private String productName;
    private String price;
    private String rating;
    private String Shelf;

    public RatingListView(String image, String productName, String price, String rating, String shelf) {
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.rating = rating;
        Shelf = shelf;
    }

    public String getImage() {
        return image;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getShelf() {
        return Shelf;
    }
}
