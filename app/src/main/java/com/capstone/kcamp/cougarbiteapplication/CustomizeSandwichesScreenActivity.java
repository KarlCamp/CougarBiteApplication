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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.ItemCategory;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Order;
import com.capstone.kcamp.cougarbiteapplication.Global.Global;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class CustomizeSandwichesScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String foodId="";
    String foodName="";
    TextView food_name, food_price, food_description, food_nutrition;
    ElegantNumberButton numberButton;
    FloatingActionButton btnAdd;
    ItemCategory foodItem;
    boolean lettuceTopping, tomatoTopping, onionTopping, pickleTopping, baconTopping, cheeseTopping, avocadoTopping,
            fried_eggTopping, chickenTopping, pattyTopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_sandwiches_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Description");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("fooditems");
        final DatabaseReference orders=firebaseDatabase.getReference("orders");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        food_description = (TextView)findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_nutrition = (TextView) findViewById(R.id.food_nutrition);
        Paper.init(this);
        if(getIntent() !=null) {
            foodId=getIntent().getStringExtra("foodIdentificationNumber");
            if(!foodId.isEmpty()){
                getDetailFood(foodId);
            }
        }
        FloatingActionButton fab = findViewById(R.id.heyHey);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Order order = new Order(foodId, foodItem.getText(), numberButton.getNumber(), foodItem.getPrice(),
                            lettuceTopping, tomatoTopping, onionTopping, pickleTopping, baconTopping, cheeseTopping, avocadoTopping,
                            fried_eggTopping, chickenTopping, pattyTopping, false, false, false, false, false,
                            false, false, false, false, false, false, false, false, false, false, false, false);
                    Global.currentCart.add(order);
                    Toast.makeText(CustomizeSandwichesScreenActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDetailFood(String foodId) {
        databaseReference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foodItem = dataSnapshot.getValue(ItemCategory.class);
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
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.Lettuce: {
                if (checked) {
                    lettuceTopping = true;
                } else {
                    lettuceTopping = false;
                }
                break;
            }
            case R.id.Tomato: {
                if (checked) {
                    tomatoTopping = true;
                } else {
                    tomatoTopping = false;
                }
                break;
            }
            case R.id.Onion: {
                if (checked) {
                    onionTopping = true;
                } else {
                    onionTopping = false;
                }
                break;
            }
            case R.id.Pickle: {
                if (checked) {
                    pickleTopping = true;
                } else {
                    pickleTopping = false;
                }
                break;
            }
            case R.id.Bacon: {
                if (checked) {
                    baconTopping = true;
                } else {
                    baconTopping = false;
                }
                break;
            }
            case R.id.Cheese: {
                if (checked) {
                    cheeseTopping = true;
                } else {
                    cheeseTopping = false;
                }
                break;
            }
            case R.id.Avocado: {
                if (checked) {
                    avocadoTopping = true;
                } else {
                    avocadoTopping = false;
                }
                break;
            }
            case R.id.FriedEgg: {
                if (checked) {
                    fried_eggTopping = true;
                } else {
                    fried_eggTopping = false;
                }
                break;
            }
            case R.id.Chicken: {
                if (checked) {
                    chickenTopping = true;
                } else {
                    chickenTopping = false;
                }
                break;
            }
            case R.id.Patty: {
                if (checked) {
                    pattyTopping = true;
                } else {
                    pattyTopping = false;
                }
                break;
            }
        }
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_menu) {
            Intent intent = new Intent(CustomizeSandwichesScreenActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_check_out) {
            Intent intent = new Intent(CustomizeSandwichesScreenActivity.this, CheckOutActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(CustomizeSandwichesScreenActivity.this, OrderStatusActivity.class);
            startActivity(intent);
        }else if (id == R.id.navigation_about) {
            Intent intent = new Intent(CustomizeSandwichesScreenActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_sign_out) {
            Paper.book().destroy();
            Intent intent = new Intent(CustomizeSandwichesScreenActivity.this, CustomerSignInScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}