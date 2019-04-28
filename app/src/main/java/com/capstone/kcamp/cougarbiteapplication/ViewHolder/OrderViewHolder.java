package com.capstone.kcamp.cougarbiteapplication.ViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.R;
public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderTime, txtOrderDetails, txtOrderTotal;
    public ImageView removeItem;
    private ItemClickListener itemClickListener;
    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderTime = itemView.findViewById(R.id.order_time);
        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderDetails = itemView.findViewById(R.id.order_details);
        txtOrderTotal = itemView.findViewById(R.id.order_total);
        removeItem = itemView.findViewById(R.id.customer_item_remove);


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
