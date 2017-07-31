package com.example.barcodeshop.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.barcodeshop.Adapter.OffersAdapter;
import com.example.barcodeshop.Models.OfferActivity;
import com.example.barcodeshop.R;
import com.example.barcodeshop.Url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfferFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    RequestQueue requestQueue;

    private List<OfferActivity> OfferlistItems = new ArrayList<>();

    private String offerurl;

    public OfferFragment() {
        offerurl = Url.getAbsUrl("offers");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.OffersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestQueue = Volley.newRequestQueue(getActivity());

        getOffers();

        return view;
    }

    private void getOffers(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, offerurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                String discount = object.getString("Discount")+"%";

                                OfferActivity offers = new OfferActivity(
                                        discount,
                                        object.getString("Description")
                                );
                                OfferlistItems.add(offers);
                            }

                            adapter = new OffersAdapter(OfferlistItems, getContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet Connection Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);

    }

}
