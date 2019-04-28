package com.capstone.kcamp.cougarbiteapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.capstone.kcamp.cougarbiteapplication.Model.MenuCategory;
import com.capstone.kcamp.cougarbiteapplication.Service.ListenOrder;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.MenuScreenViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;


public class MenuScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference ref;
    RecyclerView recView;
    RecyclerView.LayoutManager layout;
    FirebaseRecyclerAdapter<MenuCategory, MenuScreenViewHolder> adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Paper.init(this);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        ref = FirebaseDatabase.getInstance().getReference("foodcategories");
        FloatingActionButton cart = findViewById(R.id.menu_checkout_fab);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuScreenActivity.this, CheckOutActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        recView = findViewById(R.id.menu_recycler_view);
        recView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        recView.setLayoutManager(layout);
        fillAdapter();
        Intent intent = new Intent(MenuScreenActivity.this, ListenOrder.class);
        startService(intent);
    }
    private void fillAdapter() {
        adapt = new FirebaseRecyclerAdapter<MenuCategory, MenuScreenViewHolder>(MenuCategory.class,R.layout.menu_item, MenuScreenViewHolder.class,ref) {
            @Override
            protected void populateViewHolder(MenuScreenViewHolder viewHolder, MenuCategory model, int position) {
                viewHolder.categoryTitle.setText(model.getText());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        if (adapt.getRef(position).getKey().equals("06")) {
                            Intent foodCategory = new Intent(MenuScreenActivity.this, BuildYourOwnScreenActivity.class);
                            startActivity(foodCategory);
                        } else {
                        Intent foodCategory = new Intent(MenuScreenActivity.this, FoodScreenActivity.class);
                        foodCategory.putExtra("categoryIdentificationNumber", adapt.getRef(position).getKey());
                        startActivity(foodCategory);
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_menu) {
            Intent intent = new Intent(MenuScreenActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_check_out) {
            Intent intent = new Intent(MenuScreenActivity.this, CheckOutActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_pay) {
            Intent intent = new Intent(MenuScreenActivity.this, PaymentMethodActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(MenuScreenActivity.this, OrderStatusActivity.class);
            startActivity(intent);
        }else if (id == R.id.navigation_about) {
            Intent intent = new Intent(MenuScreenActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_sign_out) {
            Paper.book().destroy();
            Intent intent = new Intent(MenuScreenActivity.this, CustomerSignInScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}