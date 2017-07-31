package com.example.barcodeshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Wallet extends AppCompatActivity {

    RequestQueue requestQueue;
    String customerid;
    int wallet;
    ProgressBar progressBar;
    LinearLayout linear;
    TextView walletPoints;

    private String walletUrl;

    public Wallet() {
        walletUrl = Url.getAbsUrl("Wallet");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        setTitle("Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(this);
        Session session = new Session(this);
        customerid = session.getUserID();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        walletPoints = (TextView)findViewById(R.id.txtWalletPoints);
        linear = (LinearLayout)findViewById(R.id.linearLayoutWallet);

        walletRequest();
    }

    public void walletRequest()
    {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, walletUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        linear.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        JSONArray jsonArray;
                        try {
                            jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            wallet = jsonObject.getInt("Points");

                            walletPoints.setText("PKR "+String.valueOf(wallet));

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
                        Toast.makeText(Wallet.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Customer_Id", customerid);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
