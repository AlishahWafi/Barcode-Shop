package com.example.barcodeshop.Fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Adapter.CartListViewAdapter;
import com.example.barcodeshop.CartListView;
import com.example.barcodeshop.CartListViewArrayList;
import com.example.barcodeshop.Models.ProductModel;
import com.example.barcodeshop.OrderConfirmation;
import com.example.barcodeshop.R;
import com.example.barcodeshop.Session;
import com.example.barcodeshop.Url.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    TextView TotalItems, TotalPrice;
    float TempPrice;
    Button btnProceed;
    RequestQueue requestQueue;

    LinearLayout linear;

    final Context context = getActivity();

    private ArrayList<CartListView> cartListViews;
    CartListViewArrayList cartListViewArrayList = new CartListViewArrayList();

    private String saveListUrl;
    public CartFragment() {
        saveListUrl = Url.getAbsUrl("OrderList");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.CartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TotalItems = (TextView)view.findViewById(R.id.txtCartFragmentItems);
        TotalPrice = (TextView)view.findViewById(R.id.txtCartFragmentPrice);
        btnProceed = (Button)view.findViewById(R.id.btnCartProceed);

        linear = (LinearLayout)view.findViewById(R.id.LinearEmptyCart);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        cartListViews = cartListViewArrayList.getList();

        int i = cartListViews.size();
        System.out.println("Size of the Array :"+i);
        TotalItems.setText(String.valueOf(i));

        if(i == 0)
        {
            linear.setVisibility(View.VISIBLE);
        }
        else
        {
            linear.setVisibility(View.INVISIBLE);
        }

        TempPrice = 0;

        Iterator itr = cartListViews.iterator();
        while(itr.hasNext())
        {
            CartListView st = (CartListView)itr.next();
            TempPrice = TempPrice + Float.parseFloat(st.getPrice());
        }

        TotalPrice.setText(String.valueOf(TempPrice));

        adapter = new CartListViewAdapter(cartListViews,getActivity());
        recyclerView.setAdapter(adapter);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cartListViews.size() != 0)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Confirmation");

                    alertDialogBuilder
                            .setMessage("Are you sure you want to Continue?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                    //Toast.makeText(getActivity(), "Yes is Pressed", Toast.LENGTH_SHORT).show();
                                    //------------------------------------------------------------------
                                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                    progressDialog.setMessage("Getting order ready!");
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();

                                    Session session = new Session(getActivity());

                                        ProductModel productModel = new ProductModel();
                                        productModel.jsonData = new ArrayList<CartListView>();
                                        productModel.customer_id = session.getUserID();
                                        productModel.total_price = TotalPrice.getText().toString();
                                        productModel.jsonData.addAll(cartListViews);

                                        Gson gson = new Gson();
                                        String data =gson.toJson(productModel);

                                        JSONObject jsonData = null;
                                        try {
                                            jsonData = new JSONObject(data);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        System.out.println("json: "+jsonData);

                                        //-------------------------------------JSON CALL-----------------------------------------

                                        if (jsonData!=null){
                                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, saveListUrl,
                                                    jsonData, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    System.out.println("Response: "+response);

                                                    if (response.optBoolean("success")) {
                                                        progressDialog.dismiss();
                                                        String i = String.valueOf(cartListViews.size());
                                                        Toast.makeText(getActivity(), "Order Placed successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getActivity(), OrderConfirmation.class);
                                                        intent.putExtra("Price",TotalPrice.getText().toString());
                                                        intent.putExtra("Items",i);

                                                        cartListViews.clear();

                                                        startActivity(intent);

                                                    } else {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getActivity(), "Order Not Placed Please try again or update your wallet", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_LONG).show();
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
                                            } ;

                                            requestQueue.add(jsonObjectRequest);
                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Cart List is empty!", Toast.LENGTH_SHORT).show();
                                        }

                                        //------------------------------------------------------------------------------
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

            }
            else
            {
                Toast.makeText(getActivity(), "Cart List is empty!", Toast.LENGTH_SHORT).show();
            }
        }});

        return view;
    }

}
