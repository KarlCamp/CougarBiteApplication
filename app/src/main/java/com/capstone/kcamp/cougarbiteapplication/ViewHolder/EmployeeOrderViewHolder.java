package com.capstone.kcamp.cougarbiteapplication.ViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.R;

public class EmployeeOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderTime, txtOrderDetails;
    public ImageView deleteImage, upArrowImage, upsideDownArrowImage;
    private ItemClickListener itemClickListener;
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

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }
}
