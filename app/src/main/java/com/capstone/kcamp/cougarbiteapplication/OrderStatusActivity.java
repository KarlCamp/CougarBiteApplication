package com.capstone.kcamp.cougarbiteapplication;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Request;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.OrderViewHolder;
import com.capstone.kcamp.cougarbiteapplication.Global.Global;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class OrderStatusActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, OrderViewHolder>  adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        database  = FirebaseDatabase.getInstance();
        requests = database.getReference("customerrequests");
        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getIntent() == null)
                    loadOrders(Global.presentCustomer.getPhone());
                else
                    loadOrders(getIntent().getStringExtra("userPhone"));
                loadOrders(Global.presentCustomer.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (getIntent() == null)
            loadOrders(Global.presentCustomer.getPhone());
        else
            loadOrders(getIntent().getStringExtra("userPhone"));
        loadOrders(Global.presentCustomer.getPhone());

    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                        .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, final int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Global.statusConversion(model.getStatus()));
                viewHolder.txtOrderTime.setText(model.getPickUpTime());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderDetails.setText(model.getOrderDetails());
                viewHolder.txtOrderTotal.setText(model.getTotal());
                viewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requests.child(Objects.requireNonNull(adapter.getRef(position).getKey())).removeValue();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,adapter.getItemCount()-1);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}