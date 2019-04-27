package com.capstone.kcamp.cougarbiteapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.Model.Request;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.EmployeeOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import static com.capstone.kcamp.cougarbiteapplication.Common.Common.DELETE;
import static com.capstone.kcamp.cougarbiteapplication.Common.Common.UPDATE;

public class EmployeeOrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView deleteImage, upArrowImage, upsideDownArrowImage;
    FirebaseRecyclerAdapter<Request, EmployeeOrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_order_status);

        db = FirebaseDatabase.getInstance();
        requests = db.getReference("requests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders();
    }
    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, EmployeeOrderViewHolder>(
                Request.class, R.layout.employee_order_layout, EmployeeOrderViewHolder.class, requests
        ) {
            @Override
            protected void populateViewHolder(EmployeeOrderViewHolder viewHolder, final Request model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderTime.setText(model.getPickUpTime());
                viewHolder.txtOrderPhone.setText(model.getPhone());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(EmployeeOrderStatus.this, "I have been clicked!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
