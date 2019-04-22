package com.capstone.kcamp.cougarbiteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Model.FoodItem;
import com.capstone.kcamp.cougarbiteapplication.Model.Order;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomizeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String foodId="";
    String foodName="";

    TextView food_name, food_price, food_description, food_nutrition;
    ElegantNumberButton numberButton;
    FloatingActionButton btnAdd;
    FoodItem foodItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Description");
        setSupportActionBar(toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("fooditems");
        final DatabaseReference orders=firebaseDatabase.getReference("orders");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        //btnAdd = (FloatingActionButton)findViewById(R.id.heyHey);

        //btnAdd.setOnClickListener(new View.OnClickListener(){
            //@Override
            //public void onClick(View view) {
               // Random rand= new Random();
               // Integer i= rand.nextInt()%10000000;
               // Order order= new Order (foodId, foodItem.getText(), numberButton.getNumber(), foodItem.getPrice());
               // DatabaseReference mRef =  firebaseDatabase.getReference().child("orders").child(i.toString());
              //  mRef.setValue(mRef);
                //new Database(getBaseContext()).addToCart(new Order(
                //    foodId, foodItem.getText(), numberButton.getNumber(), foodItem.getPrice()
                //));

            //    Toast.makeText(CustomizeActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
          //  }
        //});
        food_description = (TextView)findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_nutrition = (TextView) findViewById(R.id.food_nutrition);

        if(getIntent() !=null) {
            foodId=getIntent().getStringExtra("FoodId");
            if(!foodId.isEmpty()){
                getDetailFood(foodId);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.heyHey);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order= new Order (foodId, foodItem.getText(), numberButton.getNumber(), foodItem.getPrice());
                DatabaseReference mRef =  firebaseDatabase.getReference().child("orders").child(Common.currentCustomer.getHNumber());
                mRef.setValue(order);
                //new Database(getBaseContext()).addToCart(new Order(
                //    foodId, foodItem.getText(), numberButton.getNumber(), foodItem.getPrice()
                //));

                Toast.makeText(CustomizeActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void getDetailFood(String foodId) {
        databaseReference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foodItem = dataSnapshot.getValue(FoodItem.class);

                food_price.setText(foodItem.getPrice());

                food_name.setText(foodItem.getText());

                foodName=foodItem.getText();

                food_description.setText(foodItem.getDescription());

                food_nutrition.setText(foodItem.getNutritionalValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            Intent intent = new Intent(CustomizeActivity.this, FoodCategories.class);
            startActivity(intent);
        } else if (id == R.id.nav_check_out) {
            Intent intent = new Intent(CustomizeActivity.this, CheckOutActivity.class);
            startActivity(intent);
        } else if (id == R.id.abt) {
            Intent intent = new Intent(CustomizeActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pay) {

        } else if (id == R.id.nav_log_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


