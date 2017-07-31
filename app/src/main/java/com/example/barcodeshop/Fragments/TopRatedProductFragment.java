package com.example.barcodeshop.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Adapter.ProductRatingListAdapter;
import com.example.barcodeshop.Adapter.RatingListViewAdapter;
import com.example.barcodeshop.Models.ProductRatingList;
import com.example.barcodeshop.Models.RatingListView;
import com.example.barcodeshop.R;
import com.example.barcodeshop.Session;
import com.example.barcodeshop.Url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopRatedProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter1;

    RequestQueue requestQueue;

    String customerid;

    private List<RatingListView> ratingListViews;
    private List<ProductRatingList> productRatingLists;

    private String TopRatedUrl, imageurl, ProductTopRatedUrl;
    public TopRatedProductFragment() {
        TopRatedUrl = Url.getAbsUrl("TopRated");
        imageurl = Url.getImageUrl("getImage");
        ProductTopRatedUrl = Url.getAbsUrl("ProductTopRated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated_product, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.RatingRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView1 = (RecyclerView)view.findViewById(R.id.RatingRecyclerView1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestQueue = Volley.newRequestQueue(getActivity());
        ratingListViews = new ArrayList<>();
        productRatingLists = new ArrayList<>();

        getTopRatedProducts();
        getProductTopRatedProducts();

        return view;
    }

    public void getTopRatedProducts()
    {
        Session session = new Session(getActivity());
        customerid = session.getUserID();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TopRatedUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //linear.setVisibility(View.VISIBLE);
                        //progressBar.setVisibility(View.INVISIBLE);
                        JSONArray jsonArray;
                        try {
                            jsonArray = new JSONArray(response);
                            if(jsonArray != null){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String image = imageurl + jsonObject.getString("Image");
                                    String Price = "PKR " + jsonObject.getString("Price");
                                    String shelf = "Shelf "+jsonObject.getString("Shelf");
                                    String rating = String.valueOf(jsonObject.getDouble("AverageRating"));
                                    String name = jsonObject.getString("Name");

                                    RatingListView item = new RatingListView(
                                            image,
                                            name,
                                            Price,
                                            rating,
                                            shelf
                                    );
                                    ratingListViews.add(item);

                                }
                            }else{
                                Toast.makeText(getActivity(), "No Product found", Toast.LENGTH_SHORT).show();
                            }

                            adapter = new RatingListViewAdapter(ratingListViews,getActivity());
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            //progressBar.setVisibility(View.INVISIBLE);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
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

    public void getProductTopRatedProducts()
    {
        Session session = new Session(getActivity());
        customerid = session.getUserID();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ProductTopRatedUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //linear.setVisibility(View.VISIBLE);
                        //progressBar.setVisibility(View.INVISIBLE);
                        JSONArray jsonArray;
                        try {
                            jsonArray = new JSONArray(response);
                            if(jsonArray != null){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String image = imageurl + jsonObject.getString("Image");
                                    String Price = "PKR " + jsonObject.getString("Price");
                                    String shelf = "Shelf "+jsonObject.getString("Shelf");
                                    String rating = String.valueOf(jsonObject.getDouble("AverageRating"));
                                    String name = jsonObject.getString("Name");

                                    ProductRatingList item = new ProductRatingList(
                                            image,
                                            name,
                                            Price,
                                            rating,
                                            shelf
                                    );
                                    productRatingLists.add(item);

                                }
                            }else{
                                Toast.makeText(getActivity(), "No Product found", Toast.LENGTH_SHORT).show();
                            }

                            adapter1 = new ProductRatingListAdapter(productRatingLists,getActivity());
                            recyclerView1.setAdapter(adapter1);


                        } catch (JSONException e) {
                            //progressBar.setVisibility(View.INVISIBLE);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }

}
