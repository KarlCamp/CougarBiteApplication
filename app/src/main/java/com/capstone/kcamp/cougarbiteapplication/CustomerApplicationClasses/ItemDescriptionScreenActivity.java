package com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.capstone.kcamp.cougarbiteapplication.AboutScreenActivity;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.ItemCategory;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Order;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationGlobals.Global;
import com.capstone.kcamp.cougarbiteapplication.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

/**
 * The ItemDescriptionScreenActivity is the activity that displays basic information about an item.
 * Within this activity the customer will be able to view details regarding the item of interest
 * and either add or remove the desired quantity to their personal cart. This activity may be
 * accessed through the appropriate FoodScreenActivity chosen from the recycler view.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class ItemDescriptionScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databaseReference; //database reference to be utilized
    String foodId=""; //storing id to retrieve food information
    String foodName=""; //storing name of food
    TextView food_name, food_price, food_description, food_nutrition; //text views to be filled
    ElegantNumberButton numberButton; //button that allows addition and subtraction of desired quantity
    ItemCategory foodItem; //object ot store foodItem

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Identifies all values within the xml layout to be filled with appropriate information.
     *      5. Establishes references to the Firebase database.
     *      6. Initializes Paper, which is a key-value pair object for remember me functionality.
     *      7. Establishes drawer layout context.
     *      8. Creates an instance of the NavigationView.
     *      9. Retrieves food item id to be utilized as a key.
     *      10. Binds an event to the FloatingActionButton.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_customize);

        //see section 3.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Description");
        setSupportActionBar(toolbar);

        //see section 4.
        databaseReference = FirebaseDatabase.getInstance().getReference("fooditems");

        //see section 5.
        numberButton = findViewById(R.id.number_button);
        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_nutrition = findViewById(R.id.food_nutrition);

        //see section 6.
        Paper.init(this);

        //see section 7.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //see section 8.
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //see section 9.
        if(getIntent() !=null) {
            foodId=getIntent().getStringExtra("foodIdentificationNumber");
            if(!foodId.isEmpty()){
                getDetailFood(foodId);
            }
        }

        //see section 10.
        FloatingActionButton fab = findViewById(R.id.itemDescriptionScreenActivityFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //adds order to the cart
                    Order order= new Order (foodId, foodItem.getText(), numberButton.getNumber(), foodItem.getPrice(),
                            false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
                            false, false, false, false, false, false, false, false, false, false, false);
                Global.currentCart.add(order);
                    Toast.makeText(ItemDescriptionScreenActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * The getDetailFood method does the following:
     *      1. Retrieves details about a food by querying the database using the foodId value.
     *      2. Populates the view holder by finding appropriate values based on id in xml.
     * @param foodId String that is the unique identifier of a food item
     */
    private void getDetailFood(String foodId) {
        //see section 1.
        databaseReference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //see section 2.
                foodItem = dataSnapshot.getValue(ItemCategory.class);
                assert foodItem != null;
                food_price.setText(foodItem.getPrice());
                food_name.setText(foodItem.getText());
                foodName=foodItem.getText();
                food_description.setText(foodItem.getDescription());
                food_nutrition.setText(foodItem.getNutritionalValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * The onBackPressed method is generated by default through the NavigationView implementation.
     * It's purpose is to deal with the back button pressed functionality that is possible with
     * the NavigationView.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * The onCreateOptionsMenu method is generated by default through the NavigationView
     * implementation. It's purpose is to create the options menu layout based on the xml and return
     * return true upon successful creation. It is deactivated in this app's case, therefore base
     * functionality is called.
     * @param menu object passed to ensure it binds correctly in the activity
     * @return confirmation of successful binding of the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    /**
     * The onOptionsItemSelected method is generated by default through the NavigationView implemenation.
     * It's purpose is to create the options menu layout. It is deactivated in this app's case,
     * therefore base functionality is called.
     * @param item item to be bound to an event
     * @return confirmation of successful binding of the options item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * The onNavigationItemSelected method is generated by default through the NavigationView
     * implementation. It's purpose is to create the navigation menu. This app utilizes the
     * navigation bar to move across various screens and sing out. This functionlity may be
     * seen in the code below.
     * @param item item to be bound to an event
     * @return confirmation of successful binding of the options item selected
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_menu) {
            Intent intent = new Intent(ItemDescriptionScreenActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_check_out) {
            Intent intent = new Intent(ItemDescriptionScreenActivity.this, CheckOutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(ItemDescriptionScreenActivity.this, OrderStatusScreenActivity.class);
            startActivity(intent);
        }else if (id == R.id.navigation_about) {
            Intent intent = new Intent(ItemDescriptionScreenActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_sign_out) {
            Paper.book().destroy();
            Intent intent = new Intent(ItemDescriptionScreenActivity.this, CustomerSignInScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}