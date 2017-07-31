package com.example.barcodeshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Url.Url;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DialogBox extends AppCompatActivity {

    Button btnSubmit, btnCancel;
    RatingBar rate;
    String id;

    private String listurl;
    public DialogBox() {
        listurl = Url.getAbsUrl("rate");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_box);

        setTitle("Rate");

        final Session session = new Session(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("prodID");

        btnSubmit = (Button) findViewById(R.id.BtndialogBoxSubmit);
        btnCancel = (Button) findViewById(R.id.BtndialogBoxCancel);
        rate = (RatingBar) findViewById(R.id.dialogBoxRatingBar);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DialogBox.this,"UserRating: "+rate.getRating(),Toast.LENGTH_SHORT).show();
                finish();

                String customerid = session.getUserID();
                String rating = String.valueOf(rate.getRating());
                String productid = id;

                final RequestQueue queue = Volley.newRequestQueue(DialogBox.this);

                Map<String, String> jsonParams = new HashMap<String, String>();
                jsonParams.put("Customer_Id", customerid);
                jsonParams.put("Product_Id", productid);
                jsonParams.put("Rating1", rating);

                final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, listurl,
                        new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Get response here and then logic after sucess login
                                if (response.optBoolean("success")) {


                                } else {

                                    Toast.makeText(getBaseContext(), "Can not rate at this time", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    //defining header here...
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("User-agent", System.getProperty("http.agent"));
                        return headers;
                    }
                };
                queue.add(postRequest);

                //retry connection
                postRequest.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 50000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 50000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });

            }
        });
    }
}
