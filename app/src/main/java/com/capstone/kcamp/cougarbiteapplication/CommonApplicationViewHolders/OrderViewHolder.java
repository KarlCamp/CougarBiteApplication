package com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.R;

/**
 * The OrderViewHolder class is the holder utilized to fill the recycler view. In
 * other words, the class allows for the population of the recycler view with the
 * appropriate information regarding an order in particular.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderTime, txtOrderDetails, txtOrderTotal; //text views to be filled by id's as presented in the xml
    public ImageView removeItem; //image view to be filled by id as presented in the xml

    //generated appropriate constructor utilizing inheritance

    public OrderViewHolder(View itemView) {
        super(itemView);
        txtOrderTime = itemView.findViewById(R.id.order_time);
        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderDetails = itemView.findViewById(R.id.order_details);
        txtOrderTotal = itemView.findViewById(R.id.order_total);
        removeItem = itemView.findViewById(R.id.customer_item_remove);
    }
}