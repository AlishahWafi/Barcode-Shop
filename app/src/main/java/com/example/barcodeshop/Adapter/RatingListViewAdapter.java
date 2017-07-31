package com.example.barcodeshop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.barcodeshop.R;
import com.example.barcodeshop.Models.RatingListView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ' on 5/9/2017.
 */
public class RatingListViewAdapter extends RecyclerView.Adapter<RatingListViewAdapter.ViewHolder>{

    private List<RatingListView> ratingListViews;
    private Context context;

    public RatingListViewAdapter(List<RatingListView> ratingListViews, Context context) {
        this.ratingListViews = ratingListViews;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_rating_list_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RatingListView ratingListView = ratingListViews.get(position);

        holder.productname.setText(ratingListView.getProductName());
        holder.price.setText(ratingListView.getPrice());
        holder.shelf.setText(ratingListView.getShelf());

        float rate = Float.parseFloat(ratingListView.getRating());
        holder.ratingBar.setRating(rate);

        Picasso.with(context)
                .load(ratingListView.getImage())
                .into(holder.image);

        holder.rating.setText(ratingListView.getRating());

    }

    @Override
    public int getItemCount() {
        return ratingListViews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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
