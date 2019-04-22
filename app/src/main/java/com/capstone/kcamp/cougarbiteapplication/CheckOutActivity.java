package com.capstone.kcamp.cougarbiteapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Database.Database;
import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.Model.AppUser;
import com.capstone.kcamp.cougarbiteapplication.Model.FoodCategory;
import com.capstone.kcamp.cougarbiteapplication.Model.Order;
import com.capstone.kcamp.cougarbiteapplication.Model.Request;
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

public class CheckOutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;
    DatabaseReference requests;

    TextView txtTotalPrice;
    FButton btnPlace;

    String edtTime;
    List<Order> cart = new ArrayList<>();

    FirebaseRecyclerAdapter<Order, CartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Check Out");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        //String id = Common.currentUser.getHNumber();
        request=database.getReference("orders");
        requests=database.getReference("requests");

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CheckOutActivity.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your time: ");
        final EditText edtTime = new EditText(CheckOutActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtTime.setLayoutParams(lp);
        alertDialog.setView(edtTime);
        alertDialog.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Create new Request
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getHNumber(),
                        edtTime.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //Delete cart
                //new Database(getBaseContext()).cleanCart();
                Toast.makeText(CheckOutActivity.this, "Cart processed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckOutActivity.this, PaymentMethodActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            }
        });

        alertDialog.show();
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
                cart.add(model);
            }
        };
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.food_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Intent intent = new Intent(CheckOutActivity.this, FoodCategories.class);
            startActivity(intent);
        } else if (id == R.id.nav_check_out) {
            Intent intent = new Intent(CheckOutActivity.this, CheckOutActivity.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(CheckOutActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pay) {

        } else if (id == R.id.nav_log_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}