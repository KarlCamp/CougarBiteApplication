package com.capstone.kcamp.cougarbiteapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Database.Database;
import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.Model.AppUser;
import com.capstone.kcamp.cougarbiteapplication.Model.FoodCategory;
import com.capstone.kcamp.cougarbiteapplication.Model.Order;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.CartAdapter;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.CartViewHolder;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class CheckOutActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();

    FirebaseRecyclerAdapter<Order, CartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        database = FirebaseDatabase.getInstance();
        //String id = Common.currentUser.getHNumber();
        request=database.getReference("orders");

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);

        loadListFood();
    }

    private void loadListFood() {
        adapter = new FirebaseRecyclerAdapter<Order, CartViewHolder>(Order.class,R.layout.cart_layout, CartViewHolder.class,request) {
            @Override
            protected void populateViewHolder(CartViewHolder viewHolder, Order model, int position) {
                viewHolder.txt_crt_name.setText(model.getProductname());
                double total = 0;
                total+=(Double.parseDouble(model.getPrice())) * (Double.parseDouble(model.getQuantity()));
                Locale locale = new Locale("en","US");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                txtTotalPrice.setText(fmt.format(total));
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
