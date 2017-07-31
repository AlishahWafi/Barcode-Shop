package com.example.barcodeshop.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barcodeshop.CartListView;
import com.example.barcodeshop.CartListViewArrayList;
import com.example.barcodeshop.Fragments.CartFragment;
import com.example.barcodeshop.ProductDetail;
import com.example.barcodeshop.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ' on 4/25/2017.
 */
public class CartListViewAdapter extends RecyclerView.Adapter<CartListViewAdapter.ViewHolder>{

    private List<CartListView> cartListViews;
    private Context context;



    CartListView cartListView;

    public CartListViewAdapter(List<CartListView> cartListViews, Context context) {
        this.cartListViews = cartListViews;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cart_list_view, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartListViewAdapter.ViewHolder holder, final int position) {

        cartListView = cartListViews.get(position);

        holder.productName.setText(cartListView.getProductName());
        holder.price1.setText("PKR "+cartListView.getPrice());
        holder.qty.setText(cartListView.getQty());

        Picasso.with(context)
                .load(cartListView.getImage())
                .into(holder.image);

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Position: "+position);
                int i = position;

                int prodID;
                String image;
                String name;

                prodID = cartListViews.get(i).getProductID();
                image = cartListViews.get(i).getImage();
                name = cartListViews.get(i).getProductName();

                float btnPlus_Price = 0;
                int btnPlus_Qty = 0;
                float btnPlus_SinglePrice = 0;

                btnPlus_Price = Float.parseFloat(cartListViews.get(i).getPrice());
                btnPlus_Qty = Integer.parseInt(cartListViews.get(i).getQty());
                btnPlus_SinglePrice = btnPlus_Price/btnPlus_Qty;

                System.out.println(btnPlus_Price);
                System.out.println(btnPlus_Qty);

                btnPlus_Qty = btnPlus_Qty+1;
                String finalQty = String.valueOf(btnPlus_Qty);
                String finalPrice = String.valueOf(btnPlus_Qty*btnPlus_SinglePrice);

                CartListView ca = new CartListView(prodID,image,name,finalPrice,finalQty);

                cartListViews.set(i,ca);

                holder.price1.setText("PKR "+cartListViews.get(i).getPrice());
                holder.qty.setText(cartListViews.get(i).getQty());

                System.out.println("After Qty :"+btnPlus_Qty);
                System.out.println("After price :"+finalPrice);

            }
        });


        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Position: "+position);
                int i = position;

                int prodID;
                String image;
                String name;

                prodID = cartListViews.get(i).getProductID();
                image = cartListViews.get(i).getImage();
                name = cartListViews.get(i).getProductName();

                float btnPlus_Price = 0;
                int btnPlus_Qty = 0;
                float btnPlus_SinglePrice = 0;

                btnPlus_Price = Float.parseFloat(cartListViews.get(i).getPrice());
                btnPlus_Qty = Integer.parseInt(cartListViews.get(i).getQty());
                btnPlus_SinglePrice = btnPlus_Price/btnPlus_Qty;

                System.out.println(btnPlus_Price);
                System.out.println(btnPlus_Qty);

                if(btnPlus_Qty == 1)
                {
                    holder.qty.setText(cartListViews.get(i).getQty());
                }
                else
                {
                    btnPlus_Qty = btnPlus_Qty-1;

                    String finalQty = String.valueOf(btnPlus_Qty);
                    String finalPrice = String.valueOf(btnPlus_Qty*btnPlus_SinglePrice);

                    CartListView ca = new CartListView(prodID,image,name,finalPrice,finalQty);
                    cartListViews.set(i,ca);

                    holder.price1.setText("PKR "+cartListViews.get(i).getPrice());
                    holder.qty.setText(cartListViews.get(i).getQty());

                    System.out.println("After Qty :"+btnPlus_Qty);
                    System.out.println("After price :"+finalPrice);

                }
            }
        });

    }

    public void updateArrayList(int pos, String CalcPrice, int qty)
    {
        cartListView.setPrice(String.valueOf(CalcPrice));
        cartListView.setQty(String.valueOf(qty));

        cartListViews.set(pos,cartListView);

        Iterator itr = cartListViews.iterator();
        while(itr.hasNext())
        {
            CartListView st = (CartListView)itr.next();
            System.out.println("After: "+st.getProductID()+", "+st.getProductName()+", "+st.getPrice()+", "+st.getQty());
        }
    }

    @Override
    public int getItemCount() {
        return cartListViews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView productName, price1, qty, cartPrice;
        public ImageView image;
        public Button btnPlus, btnMinus, delete;

        public ViewHolder(View itemView) {
            super(itemView);

            productName = (TextView)itemView.findViewById(R.id.txtCartViewProductName);
            price1 = (TextView)itemView.findViewById(R.id.txtCartViewPrice);
            qty = (TextView) itemView.findViewById(R.id.txtCartViewQuantity);
            image = (ImageView) itemView.findViewById(R.id.CartViewImage);
            btnPlus = (Button) itemView.findViewById(R.id.btnCartViewAddQty);
            btnMinus = (Button) itemView.findViewById(R.id.btnCartViewMinusQty);
            delete = (Button) itemView.findViewById(R.id.btnCartViewDelete);

            cartPrice = (TextView) itemView.findViewById(R.id.txtCartFragmentPrice);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartListViews.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
    }
}
