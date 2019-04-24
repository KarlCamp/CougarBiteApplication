package com.capstone.kcamp.cougarbiteapplication.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_crt_name, txt_price, txt_toppings, txt_extras;
    public ImageView remove_Item;
    public TextView total;
    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_crt_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView)itemView.findViewById(R.id.cart_item_price);
        txt_toppings = itemView.findViewById(R.id.cart_item_toppings);
        txt_extras = itemView.findViewById(R.id.cart_item_extras);
        remove_Item = itemView.findViewById(R.id.cart_item_remove);

        total = itemView.findViewById(R.id.total);
    }
    @Override
    public void onClick(View view) {

    }
}