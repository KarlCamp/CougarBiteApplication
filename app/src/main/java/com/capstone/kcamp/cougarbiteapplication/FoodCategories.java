package com.capstone.kcamp.cougarbiteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.Model.FoodCategory;
import com.capstone.kcamp.cougarbiteapplication.Service.ListenOrder;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodCategories extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<FoodCategory, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("foodcategory");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(FoodCategories.this, CheckOutActivity.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadMenu();

        Intent service = new Intent(FoodCategories.this, ListenOrder.class);
        startService(service);

    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<FoodCategory,
                        MenuViewHolder>(FoodCategory.class,R.layout.menu_item, MenuViewHolder.class,reference) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, FoodCategory model, int position) {

                viewHolder.txtMenuName.setText(model.getText());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final FoodCategory clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodCategory = new Intent(FoodCategories.this, FoodItemActivity.class);
                        foodCategory.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(foodCategory);
                    }
                });
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
            Intent intent = new Intent(FoodCategories.this, FoodCategories.class);
            startActivity(intent);
        } else if (id == R.id.nav_check_out) {
            Intent intent = new Intent(FoodCategories.this, CheckOutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pay) {
            Intent intent = new Intent(FoodCategories.this, OrderStatusActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(FoodCategories.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_log_out) {
            Intent signIn = new Intent(FoodCategories.this, CustomerSignInScreenActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
