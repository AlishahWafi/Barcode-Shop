package com.example.barcodeshop;

import java.util.ArrayList;
import java.util.List;

public class CartListView{

    private int productID;
    private String image;
    private String productName;
    private String price;
    private String qty;


    /*public CartListView(int productID, String image, String productName, String price, String qty, ArrayList<String> list) {
        this.productID = productID;
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
        this.list = list;
    }*/

    public CartListView()
    {

    }

    public CartListView(int productID, String image, String productName, String price, String qty) {
        this.productID = productID;
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
    }

    public int getProductID() {
        return productID;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }

    public String getImage() {
        return image;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
