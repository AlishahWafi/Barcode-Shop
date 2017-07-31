package com.example.barcodeshop.Models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OfferActivity{
    String discount;
    String description;

    public OfferActivity(String discount, String description) {
        this.discount = discount;
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }
}
