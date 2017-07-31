package com.example.barcodeshop.Models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.barcodeshop.R;

public class OrderListActivity {

    String OrderID;
    String OrderDate;
    String TotalPrice;

    public OrderListActivity(String orderID, String totalPrice) {
        OrderID = orderID;
        TotalPrice = totalPrice;
    }

    public String getOrderID() {
        return OrderID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }
}
