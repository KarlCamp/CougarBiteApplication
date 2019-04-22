package com.capstone.kcamp.cougarbiteapplication.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.R;

public class EmployeeOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener  {
    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderTime;

    private ItemClickListener itemClickListener;

    public EmployeeOrderViewHolder(View itemView) {
        super(itemView);

        txtOrderTime = itemView.findViewById(R.id.employee_order_time);
        txtOrderId = itemView.findViewById(R.id.employee_order_id);
        txtOrderStatus = itemView.findViewById(R.id.employee_order_status);
        txtOrderPhone = itemView.findViewById(R.id.employee_order_phone);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select The Action");
        menu.add(0,0,getAdapterPosition(),"Update");
        menu.add(0,1,getAdapterPosition(),"Delete");
    }
}
