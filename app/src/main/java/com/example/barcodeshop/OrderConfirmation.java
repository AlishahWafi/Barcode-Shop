package com.example.barcodeshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderConfirmation extends AppCompatActivity {

    TextView txtPrice, txtItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        setTitle("Order Confirmed");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String tprice = intent.getStringExtra("Price");
        String tItems = intent.getStringExtra("Items");

        txtPrice = (TextView)findViewById(R.id.txtConfirmationPrice);
        txtItems = (TextView)findViewById(R.id.txtConfirmationItem);

        txtPrice.setText(tprice);
        txtItems.setText(tItems);
    }
}
