package com.capstone.kcamp.cougarbiteapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.Model.Order;
import com.capstone.kcamp.cougarbiteapplication.Model.Request;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.EmployeeOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class EmployeeOrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, EmployeeOrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests, customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_order_status);

        db = FirebaseDatabase.getInstance();
        requests = db.getReference("employeerequests");
        customer = db.getReference("customerrequests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

       loadOrders();
    }
    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, EmployeeOrderViewHolder>(
                Request.class,
                R.layout.employee_order_layout,
                EmployeeOrderViewHolder.class,
                requests.orderByChild("status")
                        .equalTo("0")
        ) {
            @Override
            protected void populateViewHolder(final EmployeeOrderViewHolder viewHolder, final Request model, final int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderTime.setText(model.getPickUpTime());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderDetails.setText(model.getOrderDetails());
                viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requests.child(Objects.requireNonNull(adapter.getRef(position).getKey())).removeValue();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,adapter.getItemCount()-1);
                    }
                });
                viewHolder.upArrowImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getStatus().equals("0")) {
                            Toast.makeText(EmployeeOrderStatus.this, "Error: Cannot update below placed.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (model.getStatus().equals("1")) {
                                model.setStatus("0");
                                Request request = new Request(model.getPhone(), model.getName(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal(), model.getLs());
                                request.setStatus("0");
                                customer.child(adapter.getRef(position).getKey()).setValue(request);
                                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                            } else if (model.getStatus().equals("2")) {
                                model.setStatus("1");
                                Request request = new Request(model.getPhone(), model.getName(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal(), model.getLs());
                                request.setStatus("1");
                                customer.child(adapter.getRef(position).getKey()).setValue(request);
                                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                            } else {
                                model.setStatus("2");
                                Request request = new Request(model.getPhone(), model.getName(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal(), model.getLs());
                                request.setStatus("2");
                                customer.child(adapter.getRef(position).getKey()).setValue(request);
                                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                            }
                        }
                    }
                });
                viewHolder.upsideDownArrowImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getStatus().equals("0")) {
                            model.setStatus("1");
                            Request request = new Request(model.getPhone(), model.getName(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal(), model.getLs());
                            request.setStatus("1");
                            customer.child(adapter.getRef(position).getKey()).setValue(request);
                            viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                        } else if (model.getStatus().equals("1")) {
                            model.setStatus("2");
                            Request request = new Request(model.getPhone(), model.getName(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal(), model.getLs());
                            request.setStatus("2");
                            customer.child(adapter.getRef(position).getKey()).setValue(request);
                            viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                        } else {
                            model.setStatus("3");
                            Request request = new Request(model.getPhone(), model.getName(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal(), model.getLs());
                            request.setStatus("3");
                            customer.child(adapter.getRef(position).getKey()).setValue(request);
                            viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                        }
                    }
                });
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClik) {

                    }
                });
            }
        };
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
