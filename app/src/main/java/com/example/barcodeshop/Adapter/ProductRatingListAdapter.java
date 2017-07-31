package com.example.barcodeshop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.barcodeshop.Models.ProductRatingList;
import com.example.barcodeshop.Models.RatingListView;
import com.example.barcodeshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ' on 5/15/2017.
 */
public class ProductRatingListAdapter extends RecyclerView.Adapter<ProductRatingListAdapter.ViewHolder> {

    private List<ProductRatingList> productRatingLists;
    private Context context;

    public ProductRatingListAdapter(List<ProductRatingList> productRatingLists, Context context) {
        this.productRatingLists = productRatingLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productratinglist, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ProductRatingList productRatingList = productRatingLists.get(position);

        holder.productname.setText(productRatingList.getProductName());
        holder.price.setText(productRatingList.getPrice());
        holder.shelf.setText(productRatingList.getShelf());

        float rate = Float.parseFloat(productRatingList.getRating());
        holder.ratingBar.setRating(rate);

        Picasso.with(context)
                .load(productRatingList.getImage())
                .into(holder.image);

        holder.rating.setText(productRatingList.getRating());

    }

    @Override
    public int getItemCount() {
        return productRatingLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView productname, price, shelf, rating;
        public ImageView image;

        public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            productname = (TextView) itemView.findViewById(R.id.txtRateViewProductName);
            price = (TextView) itemView.findViewById(R.id.txtRateViewPrice);
            shelf = (TextView) itemView.findViewById(R.id.txtRateShelf);
            rating = (TextView) itemView.findViewById(R.id.txtRating);

            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar1);

            image = (ImageView) itemView.findViewById(R.id.RateViewImage);

        }
    }
}
