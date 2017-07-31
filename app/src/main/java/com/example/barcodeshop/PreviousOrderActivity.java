package com.example.barcodeshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Adapter.PreviousOrderAdapter;
import com.example.barcodeshop.Models.OrderListActivity;
import com.example.barcodeshop.Url.Url;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreviousOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    RequestQueue requestQueue;

    private List<OrderListActivity> orderListItems;
    private String OrderUrl;

    public PreviousOrderActivity() {
        OrderUrl = Url.getAbsUrl("PreviousOrder");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order);

        setTitle("Previous Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.OrderRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);
        orderListItems = new ArrayList<>();

        OrderRequest();
    }

    public void OrderRequest()
    {
        Session session = new Session(this);
        final String Customer_Id = session.getUserID();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, OrderUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray;
                        try {
                            jsonArray = new JSONArray(response);
                            if(jsonArray != null)
                            {
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    int OrderID = jsonObject.getInt("Order_Id");
                                    String OrderDate = jsonObject.getString("OrderDate");
                                    int TotalPrice = jsonObject.getInt("Total_Price");


                                    String id = String.valueOf(OrderID);
                                    String Price = String.valueOf(TotalPrice);

                                    OrderListActivity item = new OrderListActivity(
                                            id,
                                            Price
                                    );
                                    orderListItems.add(item);
                                }
                            }else
                            {
                                Toast.makeText(PreviousOrderActivity.this, "You have not Ordered yet.", Toast.LENGTH_SHORT).show();
                            }

                            adapter = new PreviousOrderAdapter(orderListItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PreviousOrderActivity.this,"catch mai",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PreviousOrderActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Customer_Id", Customer_Id);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
