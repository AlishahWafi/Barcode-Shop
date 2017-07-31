package com.example.barcodeshop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barcodeshop.Models.OfferActivity;
import com.example.barcodeshop.R;

import java.util.List;

/**
 * Created by ' on 5/30/2017.
 */
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder>{

    private List<OfferActivity> OfferlistItems;
    private Context context;

    public OffersAdapter(List<OfferActivity> offerlistItems, Context context) {
        OfferlistItems = offerlistItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_offer,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OfferActivity offerActivity = OfferlistItems.get(position);

        holder.discount.setText(offerActivity.getDiscount());
        holder.description.setText(offerActivity.getDescription());
    }

    @Override
    public int getItemCount() {
        return OfferlistItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView discount, description;

        public ViewHolder(View itemView) {
            super(itemView);

            discount = (TextView)itemView.findViewById(R.id.offer_discount);
            description = (TextView)itemView.findViewById(R.id.offer_description);
        }
    }
}
