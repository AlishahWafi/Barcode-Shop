package com.example.barcodeshop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barcodeshop.Models.OrderListActivity;
import com.example.barcodeshop.R;

import java.util.List;

/**
 * Created by ' on 5/30/2017.
 */
public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.ViewHolder> {

    private List<OrderListActivity> OrderlistItems;
    private Context context;

    public PreviousOrderAdapter(List<OrderListActivity> orderlistItems, Context context) {
        OrderlistItems = orderlistItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_order_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderListActivity orderListActivity = OrderlistItems.get(position);

        holder.txtOrderID.setText(orderListActivity.getOrderID());
        holder.txtOrderPrice.setText(orderListActivity.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return OrderlistItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtOrderID, txtOrderDate, txtOrderPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            txtOrderID = (TextView)itemView.findViewById(R.id.orderID);
            txtOrderPrice = (TextView)itemView.findViewById(R.id.orderPrice);

        }
    }
}
