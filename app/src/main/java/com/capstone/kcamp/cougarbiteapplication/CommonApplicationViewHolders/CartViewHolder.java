package com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.R;

/**
 * The CartViewHolder class is the holder utilized to fill the recycler view. In
 * other words, the class allows for the population of the recycler view with the
 * appropriate information regarding an order.
 *
 * @author Karl Camp
 * @version 1.0
 * @since 2019-05-04
 */
public class CartViewHolder extends RecyclerView.ViewHolder {

    TextView txt_crt_name, txt_price, txt_toppings, txt_extras; //text views to be filled by id's as presented in the xml
    ImageView remove_Item; //image view to be filled by id as presented in the xml

    //generated appropriate constructor utilizing inheritance

    CartViewHolder(View itemView) {
        super(itemView);
        txt_crt_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_price);
        txt_toppings = itemView.findViewById(R.id.cart_item_toppings);
        txt_extras = itemView.findViewById(R.id.cart_item_extras);
        remove_Item = itemView.findViewById(R.id.cart_item_remove);
    }
}