package com.example.barcodeshop.Models;

/**
 * Created by ' on 5/15/2017.
 */
public class ProductRatingList {

    private String image;
    private String productName;
    private String price;
    private String rating;
    private String Shelf;

    public ProductRatingList(String image, String productName, String price, String rating, String shelf) {
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
