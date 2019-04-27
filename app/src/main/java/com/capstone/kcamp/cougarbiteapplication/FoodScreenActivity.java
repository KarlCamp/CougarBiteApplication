package com.capstone.kcamp.cougarbiteapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.Model.FoodItem;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.FoodScreenViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class FoodScreenActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recView;
    RecyclerView.LayoutManager layout;
    String categoryIdentificationNumber="";
    FirebaseRecyclerAdapter<FoodItem, FoodScreenViewHolder> adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Food");
        setSupportActionBar(toolbar);
        databaseReference = FirebaseDatabase.getInstance().getReference("fooditems");
        FloatingActionButton fab = findViewById(R.id.food_checkout_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodScreenActivity.this, CheckOutActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        recView = findViewById(R.id.food_recycler_view);
        recView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        recView.setLayoutManager(layout);
        Paper.init(this);
        if(getIntent() != null) {
            categoryIdentificationNumber = getIntent().getStringExtra("categoryIdentificationNumber");
        }
        if (!categoryIdentificationNumber.isEmpty()) {
            switch (categoryIdentificationNumber) {
                case "01":
                    toolbar.setTitle("Sandwiches");
                    break;
                case "02":
                    toolbar.setTitle("Favorites");
                    break;
                case "03":
                    toolbar.setTitle("Sides");
                    break;
                case "04":
                    toolbar.setTitle("Sauces");
                    break;
                case "05":
                    toolbar.setTitle("Drinks");
                    break;
                case "06":
                    toolbar.setTitle("Build Your Own");
                    break;
            }
            fillAdapter(categoryIdentificationNumber);
        }
    }
    private void fillAdapter(String categoryId) {
        adapt = new FirebaseRecyclerAdapter<FoodItem,
                FoodScreenViewHolder>(FoodItem.class,R.layout.food_item, FoodScreenViewHolder.class,databaseReference.orderByChild("foodIdentificationNumber").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodScreenViewHolder viewHolder, FoodItem model, int position) {
                viewHolder.txtFoodName.setText(model.getText());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        if (categoryIdentificationNumber.equals("01")) {
                            Intent foodDetail = new Intent(FoodScreenActivity.this, CustomizeSandwichesScreenActivity.class);
                            foodDetail.putExtra("foodIdentificationNumber", adapt.getRef(pos).getKey());
                            startActivity(foodDetail);
                        } else {
                            Intent foodDetail = new Intent(FoodScreenActivity.this, CustomizeActivity.class);
                            foodDetail.putExtra("foodIdentificationNumber", adapt.getRef(pos).getKey());
                            startActivity(foodDetail);
                        }
                    }
                });
            }
        };
        recView.setAdapter(adapt);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}