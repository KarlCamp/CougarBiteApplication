package com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.R;

/**
 * The EmployeeOrderViewHolder class is the holder utilized to fill the recycler view. In
 * other words, the class allows for the population of the recycler view with the
 * appropriate information regarding an order employee side.
 *
 * @author Karl Camp
 * @version 1.0
 * @since 2019-05-04
 */
public class EmployeeOrderViewHolder extends RecyclerView.ViewHolder  {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderTime, txtOrderDetails; //text views to be filled by id's as presented in the xml
    public ImageView deleteImage, upArrowImage, upsideDownArrowImage; //image views to be filled by id's as presented in the xml

    //generated appropriate constructor utilizing inheritance

    public EmployeeOrderViewHolder(View itemView) {
        super(itemView);
        txtOrderTime = itemView.findViewById(R.id.employee_order_time);
        txtOrderId = itemView.findViewById(R.id.employee_order_id);
        txtOrderStatus = itemView.findViewById(R.id.employee_order_status);
        txtOrderPhone = itemView.findViewById(R.id.employee_order_phone);
        txtOrderDetails = itemView.findViewById(R.id.employee_order_details);
        deleteImage = itemView.findViewById(R.id.employee_item_remove);
        upArrowImage=itemView.findViewById(R.id.up_arrow);
        upsideDownArrowImage=itemView.findViewById(R.id.upsidedown_arrow);
    }
}
