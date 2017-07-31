package com.example.barcodeshop;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Adapter.CartListViewAdapter;
import com.example.barcodeshop.Url.Url;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProductDetail extends AppCompatActivity {

    RequestQueue requestQueue;
    RequestQueue requestQueue1;
    ProgressBar progressBar;
    LinearLayout linear;
    TextView txtProductName, txtQuantity, txtPrice;
    Button btnPlus, btnMinus, btnAddtoCart;
    ImageView imageView;
    int qty = 1;
    String quantity = String.valueOf(1);
    String Barcode;

    String ProductName,Price,ProductStock,ProductImage;
    int ProductID;

    String tempPrice;

    static ArrayList<CartListView> productDetails = new ArrayList<>();

    private String listurl, image, dialogboxUrl;
    public ProductDetail() {
        listurl = Url.getAbsUrl("list");
        image = Url.getImageUrl("getImage");
        dialogboxUrl = Url.getAbsUrl("ratingDialogBox");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue1 = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        Barcode = intent.getStringExtra("code");

        linear = (LinearLayout)findViewById(R.id.linearLayoutDetail);
        txtProductName = (TextView)findViewById(R.id.txtProductName);
        txtQuantity = (TextView)findViewById(R.id.txtQuantity);
        txtPrice = (TextView)findViewById(R.id.txtPrice);

        btnPlus = (Button)findViewById(R.id.btn_plus);
        btnMinus = (Button)findViewById(R.id.btn_minus);
        btnAddtoCart = (Button)findViewById(R.id.btnAddtoCart);

        imageView = (ImageView)findViewById(R.id.imgProduct);

        txtQuantity.setText(String.valueOf(qty));

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    qty = qty +1;
                    quantity = ""+qty;
                    txtQuantity.setText(quantity);

                    float temp1 = Float.parseFloat(tempPrice);
                    float temp2 = temp1*qty;

                    Price = String.valueOf(temp2);
                    txtPrice.setText(Price);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty == 1) {
                    txtQuantity.setText(String.valueOf(qty));
                }
                else{
                    qty = qty - 1;
                    quantity = ""+qty;
                    txtQuantity.setText(quantity);

                    float temp1 = Float.parseFloat(tempPrice);
                    float temp2 = temp1*qty;

                    Price = String.valueOf(temp2);
                    txtPrice.setText(Price);
                }
            }
        });

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartListView list1 = new CartListView(ProductID, ProductImage, ProductName, Price, quantity);

                int id = list1.getProductID();
                System.out.println(id);

                Boolean bool = false;

                if(productDetails.size() == 0){
                    bool = false;
                }
                else
                {
                    for(int i=0; i<productDetails.size(); i++){
                        CartListView cartListView = productDetails.get(i);
                        if(cartListView.getProductID() == id){
                            bool = true;
                            break;
                        }
                        else
                        {
                           bool = false;
                        }
                    }
                }

                System.out.println(bool);

                if(bool == true){
                    Toast.makeText(ProductDetail.this,"Already in Cart",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    productDetails.add(list1);

                    CartListViewArrayList list = new CartListViewArrayList();
                    list.setList(productDetails);

                    Toast.makeText(ProductDetail.this,"Product Added to Cart",Toast.LENGTH_SHORT).show();
                    ratingRequest();
                    finish();
                }
            }
        });

        ProductRequest();

    }

    public void ProductRequest(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, listurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        linear.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        JSONArray jsonArray;
                        try {
                            jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                                ProductID = jsonObject.getInt("Product_Id");
                                ProductName = jsonObject.getString("Name");
                                Price = jsonObject.getString("Price");
                                tempPrice = Price;
                                ProductStock = jsonObject.getString("Stock");
                                ProductImage = image+jsonObject.getString("Image");

                            if(ProductStock.equals(0)){
                                btnAddtoCart.setEnabled(false);
                                btnAddtoCart.setText("Out of Stock");
                                btnAddtoCart.setBackgroundColor(getResources().getColor(R.color.disable));
                                btnAddtoCart.setTextColor(getResources().getColor(R.color.danger));
                            }

                            txtProductName.setText(ProductName);
                            txtPrice.setText(Price);
                            Picasso.with(ProductDetail.this).load(ProductImage).into(imageView);

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ProductDetail.this,"Server Error",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Barcode", Barcode);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void ratingRequest()
    {
        Session session = new Session(this);
        final String Customer_Id = session.getUserID();
        final String Product_Id = String.valueOf(ProductID);

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("Customer_Id", Customer_Id);
        jsonParams.put("Product_Id", Product_Id);

        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, dialogboxUrl,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                        public void onResponse(JSONObject response) {
                        System.out.println("Data : "+response);

                        if (response.optBoolean("success"))
                        {
                            Intent intent = new Intent(ProductDetail.this, DialogBox.class);
                            intent.putExtra("prodID",Product_Id);
                            startActivity(intent);
                        }
                        else
                        {
                            //Toast.makeText(ProductDetail.this, "false aa raha hai", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetail.this, "Error hai", Toast.LENGTH_LONG).show();
                    }
                })   {
            //defining header here...
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        requestQueue1.add(postRequest);
    }
}
