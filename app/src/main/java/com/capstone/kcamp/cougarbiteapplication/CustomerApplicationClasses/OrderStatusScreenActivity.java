package com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Request;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.OrderViewHolder;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationGlobals.Global;
import com.capstone.kcamp.cougarbiteapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * The OrderStatusScreenActivity is the activity that displays all current orders in process from
 * the customer side. The customer may delete the items as they please. The order will update based
 * on events happening from the employee side. This activity may be accessed from the Navigation Bar.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class OrderStatusScreenActivity extends AppCompatActivity {
    public RecyclerView recyclerView; //recycler view to be filled
    public RecyclerView.LayoutManager layoutManager; //layout manager to assist in laying out the recycler view
    FirebaseRecyclerAdapter<Request, OrderViewHolder>  adapter; //adapter to fill the recycler view
    DatabaseReference requests; //database reference to be utilized

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Binds recycler view.
     *      4. Establishes references to the Firebase database.
     *      5. Bind database to events listener.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_order_status);

        //see section 3.
        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //see section 4.
        requests = FirebaseDatabase.getInstance().getReference("customerrequests");

        //see section 5.
        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getIntent() == null)
                    loadOrders(Global.presentCustomer.getPhone()); // load orders based on phone number
                else
                    loadOrders(getIntent().getStringExtra("userPhone"));
                loadOrders(Global.presentCustomer.getPhone());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * The method fillAdapter does the following:
     *      1. Establishes adapter and fills it with appropriate object and view holder type.
     *      2. Populates the view holder by finding appropriate values based on id in xml.
     *      3. Binds on click activities to appropriate values.
     */
    private void loadOrders(String phone) {
        //see section 1.
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                        .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, final int position) {
                //see section 2.
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Global.statusConversion(model.getStatus()));
                viewHolder.txtOrderTime.setText(model.getPickUpTime());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderDetails.setText(model.getOrderDetails());
                viewHolder.txtOrderTotal.setText(model.getTotal());
                viewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //see section 3.
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