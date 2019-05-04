package com.capstone.kcamp.cougarbiteapplication.EmployeeApplicationClasses;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Request;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.EmployeeOrderViewHolder;
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
 * The EmployeeOrderStatusScreenActivity class is the activity that allows an employee to process
 * through orders through an intuitive GUI. Here the employee may update view, update the status,
 * and remove an order. Updates will be send to the customer. This activity may be accessed
 * through successful verification from the EmployeeSignInScreenActivity.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class EmployeeOrderStatusScreenActivity extends AppCompatActivity {

    public RecyclerView recyclerView; //recycler view to be filled
    public RecyclerView.LayoutManager layoutManager; //layout manager to assist in laying out the recycler view
    FirebaseRecyclerAdapter<Request, EmployeeOrderViewHolder> adapter; //adapter to fill the recycler view
    DatabaseReference employeerequests, customerrequests; //database references to be utilized

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes references to the Firebase database.
     *      4. Establishes recycler view variables.
     *      5. Loads orders by filling recycler view.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_employee_order_status_screen);

        //see section 3.
        employeerequests = FirebaseDatabase.getInstance().getReference("employeerequests");
        customerrequests = FirebaseDatabase.getInstance().getReference("customerrequests");

        //see section 4.
        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //see section 5.
        employeerequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getIntent() == null)
                    loadOrders("0"); // load orders based on phone number
                else
                    loadOrders(getIntent().getStringExtra("Status"));
                loadOrders("0");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * The method loadOrders does the following:
     *      1. Establishes adapter and fills it with appropriate object and view holder type.
     *      2. Populates the view holder by finding appropriate values based on id in xml.
     *      3. Binds on click activities to appropriate values.
     */
    private void loadOrders(String status) {
        //see section 1.
        adapter = new FirebaseRecyclerAdapter<Request, EmployeeOrderViewHolder>(
                Request.class,
                R.layout.employee_order_layout,
                EmployeeOrderViewHolder.class,
                employeerequests.orderByChild("status")
                        .equalTo(status)
        ) {
            @Override

            protected void populateViewHolder(final EmployeeOrderViewHolder viewHolder, final Request model, final int position) {
                //see section 2.
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Global.statusConversion(model.getStatus()));
                viewHolder.txtOrderTime.setText(model.getPickUpTime());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderHNumber.setText(model.gethNumber());
                viewHolder.txtOrderDetails.setText(model.getOrderDetails());

                //see section 3.
                viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() { //binds delete image functionality.
                    @Override
                    public void onClick(View v) {
                        employeerequests.child(Objects.requireNonNull(adapter.getRef(position).getKey())).removeValue();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,adapter.getItemCount()-1);
                    }
                });
                viewHolder.upArrowImage.setOnClickListener(new View.OnClickListener() { //binds upArrow functionality.
                    @Override
                    public void onClick(View v) {
                        //update state
                        if (model.getStatus().equals("0")) {
                            Toast.makeText(EmployeeOrderStatusScreenActivity.this, "Error: Cannot update below placed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Request request = new Request(model.getPhone(), model.gethNumber(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal());
                            if (model.getStatus().equals("1")) {
                                model.setStatus("0");
                                request.setStatus("0");
                            } else if (model.getStatus().equals("2")) {
                                model.setStatus("1");
                                request.setStatus("1");
                            } else {
                                model.setStatus("2");
                                request.setStatus("2");
                            }
                            customerrequests.child(Objects.requireNonNull(adapter.getRef(position).getKey())).setValue(request);
                            viewHolder.txtOrderStatus.setText(Global.statusConversion(model.getStatus()));
                        }
                    }
                });
                viewHolder.upsideDownArrowImage.setOnClickListener(new View.OnClickListener() { //binds upsideDownArrow functionality.
                    @Override
                    public void onClick(View v) {
                        //update state
                        Request request = new Request(model.getPhone(), model.gethNumber(), model.getPickUpTime(), model.getOrderDetails(), model.getTotal());
                        if (model.getStatus().equals("0")) {
                            model.setStatus("1");
                            request.setStatus("1");
                        } else if (model.getStatus().equals("1")) {
                            model.setStatus("2");
                            request.setStatus("2");
                        } else {
                            model.setStatus("3");
                            request.setStatus("3");
                        }
                        customerrequests.child(Objects.requireNonNull(adapter.getRef(position).getKey())).setValue(request);
                        viewHolder.txtOrderStatus.setText(Global.statusConversion(model.getStatus()));
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter); //update adapter with new information
    }
}