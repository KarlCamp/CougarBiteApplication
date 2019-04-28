package com.capstone.kcamp.cougarbiteapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.capstone.kcamp.cougarbiteapplication.Model.Request;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.EmployeeOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class EmployeeOrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, EmployeeOrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_order_status);

        db = FirebaseDatabase.getInstance();
        requests = db.getReference("employeerequests");

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
                requests
        ) {
            @Override
            protected void populateViewHolder(final EmployeeOrderViewHolder viewHolder, final Request model, final int position) {

            }
        };
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
