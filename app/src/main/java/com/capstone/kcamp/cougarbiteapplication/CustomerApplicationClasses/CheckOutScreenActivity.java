package com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.AboutScreenActivity;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Request;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.CartAdapter;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationGlobals.Global;
import com.capstone.kcamp.cougarbiteapplication.R;
import java.text.NumberFormat;
import java.util.Locale;
import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

/**
 * The CheckOutScreenActivity class is the activity that allows a customer to process an order
 * successfully before paying. This is the activity where the user will be able to view their
 * orders, delete anything undesirable, and confirm satisfaction by pressing the pay button. This
 * activity may be accessed through the MenuScreenActivity cart button, the FoodScreenActivity
 * cart button, or the NavigationBar.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class CheckOutScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView; //recycler view to be filled
    RecyclerView.LayoutManager layoutManager; //layout manager to assist in laying out the recycler view

    TextView txt_crt_name, txt_price; //text views to be filled
    public TextView txtTotalPrice; //text views to be filled, public because important for the adapter
    FButton btnPlace; //button to be activated

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Identifies all values within the xml layout to be filled with appropriate information.
     *      5. Initializes Paper, which is a key-value pair object for remember me functionality.
     *      6. Establishes drawer layout context.
     *      7. Creates an instance of the NavigationView.
     *      8. Binds action to button.
     *      9. Loads food list.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_check_out_screen);

        //see section 3.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Check Out");
        setSupportActionBar(toolbar);

        //see section 4.
        txt_crt_name = findViewById(R.id.cart_item_name);
        txt_price = findViewById(R.id.cart_item_price);

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);

        //see section 5.
        Paper.init(this);

        //see section 6.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //see section 7.
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //see section 8.
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.currentCart.isEmpty()) {
                    Toast.makeText(CheckOutScreenActivity.this, "Error: Cart is empty.", Toast.LENGTH_LONG).show();
                } else {
                    showAlertDialog();
                }
            }
        });

        //see section 9.
        loadListFood();
    }

    /**
     * The showAlertDialog does the following:
     *      1. Creates an instance of the TimePickerDialog to allow the customer to choose pick up time.
     *      2. Allows customer to confirm time.
     *      3. Does validation checking to ensure that time chosen is within appropriate range.
     *      4. Creates request and confirmation successful processing of cart.
     */
    private void showAlertDialog() {
        //see section 1.
        TimePickerDialog.Builder alertDialog = new TimePickerDialog.Builder(CheckOutScreenActivity.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your pick up time: ");
        final TimePicker edtTime = new TimePicker(CheckOutScreenActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtTime.setLayoutParams(lp);
        alertDialog.setView(edtTime);
        alertDialog.setIcon(R.drawable.ic_access_time_black_24dp);

        //see section 2.
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //see section 3.
                if ((edtTime.getHour()>=11) && (edtTime.getHour()<23)) {

                    //see section 4.
                    String orderDetails = createOrderDetails();
                    Global.orderRequest = new Request(
                            Global.presentCustomer.getPhone(),
                            Global.presentCustomer.getHNumber(),
                            "" + edtTime.getHour() + ":" + (edtTime.getMinute()<10 ? "0":"")+edtTime.getMinute(),
                            orderDetails,
                            txtTotalPrice.getText().toString()
                    );
                    Toast.makeText(CheckOutScreenActivity.this, "Cart processed!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckOutScreenActivity.this, PaymentMethodScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CheckOutScreenActivity.this, "Error: Hours from 11AM-11PM.", Toast.LENGTH_LONG).show();
                }
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

    /**
     * The loadListFood method does the following:
     *      1. Allows the CheckOutScreenActivity to fill the adapter and present the user with a list of their orders.
     *      2. Calculate total prices for all values and the Global.prices list.
     *      3. Ensure values are presented in the correct format.
     */
    private void loadListFood() {
        //see section 1.
        CartAdapter adapter = new CartAdapter(Global.currentCart, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Global.currentTotal = 0;

        //see section 2.
        for (int position = 0; position < Global.currentCart.size(); position++) { //loops through all cart values
            //calculates prices
            double price=(Double.parseDouble(Global.currentCart.get(position).getPrice())) * (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
            //makes necessary changes to the prices as dictated by the extras
            if (Global.currentCart.get(position).isBacon() || Global.currentCart.get(position).isAvocado()
                    ||Global.currentCart.get(position).isCheese() ||Global.currentCart.get(position).isChicken()
                    ||Global.currentCart.get(position).isPatty() ||Global.currentCart.get(position).isFried_egg()) {
                if (Global.currentCart.get(position).isBacon()) {
                    price+=1.59* (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
                }
                if (Global.currentCart.get(position).isAvocado()) {
                    price+=1.59* (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
                }
                if (Global.currentCart.get(position).isCheese()) {
                    price+=0.89* (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
                }
                if (Global.currentCart.get(position).isPatty()) {
                    price+=2.19* (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
                }
                if (Global.currentCart.get(position).isChicken()) {
                    price+=2.49* (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
                }
                if (Global.currentCart.get(position).isFried_egg()) {
                    price+=1.29* (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
                }
            }
            Global.currentTotal+=price; //update total price
            Global.currentCartPrices.add(price); //add the price to the price list
        }

        //see section 3.
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(Global.currentTotal));
    }

    /**
     * The createOrderDetails method creates the string that is sent to the request and will be
     * utilized by the employee and customer order status activities to view all the details in a
     * neat and organized fashion.
     * @return String of order details for every list in the order.
     */
    private String createOrderDetails() {
        String orderDetails="";
        for (int position = 0; position < Global.currentCart.size(); position++) { //loop through all order within the cart
            orderDetails=orderDetails+Global.currentCart.get(position).getProductname()+" x "+Global.currentCart.get(position).getQuantity()+":"; //creates title of order
            if (Global.currentCart.get(position).isLettuce() || Global.currentCart.get(position).isTomato()
                    || Global.currentCart.get(position).isOnion() || Global.currentCart.get(position).isPickle()
                    || Global.currentCart.get(position).isBeef() || Global.currentCart.get(position).isBreaded_chicken()
                    || Global.currentCart.get(position).isBlack_bean() || Global.currentCart.get(position).isTurkey()
                    || Global.currentCart.get(position).isGrilled_chicken() || Global.currentCart.get(position).isAmerican()
                    || Global.currentCart.get(position).isBleu() || Global.currentCart.get(position).isPepper_jack()
                    || Global.currentCart.get(position).isSwiss() || Global.currentCart.get(position).isProvolone()
                    || Global.currentCart.get(position).isWhite_kaiser() || Global.currentCart.get(position).isSpinach_wrap()
                    || Global.currentCart.get(position).isFlour_wrap() || Global.currentCart.get(position).isWheat_kaiser()
                    || Global.currentCart.get(position).isFlat_bread() || Global.currentCart.get(position).isGarlic_wrap()
                    || Global.currentCart.get(position).isGluten_free()) {
                if (Global.currentCart.get(position).isBeef()) { //create the order details string
                    orderDetails=orderDetails + " beef,";
                }
                if (Global.currentCart.get(position).isBreaded_chicken()) {
                    orderDetails=orderDetails + " breaded chicken,";
                }
                if (Global.currentCart.get(position).isBlack_bean()) {
                    orderDetails=orderDetails + " black bean,";
                }
                if (Global.currentCart.get(position).isTurkey()) {
                    orderDetails=orderDetails + " turkey,";
                }
                if (Global.currentCart.get(position).isGrilled_chicken()) {
                    orderDetails=orderDetails + " grilled chicken,";
                }
                if (Global.currentCart.get(position).isAmerican()) {
                    orderDetails=orderDetails + " american,";
                }
                if (Global.currentCart.get(position).isBleu()) {
                    orderDetails=orderDetails + " bleu,";
                }
                if (Global.currentCart.get(position).isPepper_jack()) {
                    orderDetails=orderDetails + " pepper jack,";
                }
                if (Global.currentCart.get(position).isSwiss()) {
                    orderDetails=orderDetails + " swiss,";
                }
                if (Global.currentCart.get(position).isProvolone()) {
                    orderDetails=orderDetails + " provolone,";
                }
                if (Global.currentCart.get(position).isWhite_kaiser()) {
                    orderDetails=orderDetails + " white kaiser,";
                }
                if (Global.currentCart.get(position).isSpinach_wrap()) {
                    orderDetails=orderDetails + " spinach wrap,";
                }
                if (Global.currentCart.get(position).isFlour_wrap()) {
                    orderDetails=orderDetails + " flour wrap,";
                }
                if (Global.currentCart.get(position).isWheat_kaiser()) {
                    orderDetails=orderDetails + " wheat kaiser,";
                }
                if (Global.currentCart.get(position).isFlat_bread()) {
                    orderDetails=orderDetails + " flat bread,";
                }
                if (Global.currentCart.get(position).isGarlic_wrap()) {
                    orderDetails=orderDetails + " garlic wrap,";
                }
                if (Global.currentCart.get(position).isGluten_free()) {
                    orderDetails=orderDetails + " gluten free,";
                }
                if (Global.currentCart.get(position).isLettuce()) {
                    orderDetails=orderDetails + " lettuce,";
                }
                if (Global.currentCart.get(position).isTomato()) {
                    orderDetails=orderDetails + " tomato,";
                }
                if (Global.currentCart.get(position).isOnion()) {
                    orderDetails=orderDetails + " onion,";
                }
                if (Global.currentCart.get(position).isPickle()) {
                    orderDetails=orderDetails + " pickle,";
                }
            }
            if (Global.currentCart.get(position).isBacon() || Global.currentCart.get(position).isAvocado()
                    || Global.currentCart.get(position).isCheese() || Global.currentCart.get(position).isChicken()
                    || Global.currentCart.get(position).isPatty() || Global.currentCart.get(position).isFried_egg()) {
                if (Global.currentCart.get(position).isBacon()) {
                    orderDetails=orderDetails + " bacon,";
                }
                if (Global.currentCart.get(position).isAvocado()) {
                    orderDetails=orderDetails + " avocado,";
                }
                if (Global.currentCart.get(position).isCheese()) {
                    orderDetails=orderDetails + " extra cheese,";
                }
                if (Global.currentCart.get(position).isPatty()) {
                    orderDetails=orderDetails + " extra patty,";
                }
                if (Global.currentCart.get(position).isChicken()) {
                    orderDetails=orderDetails + " chicken,";
                }
                if (Global.currentCart.get(position).isFried_egg()) {
                    orderDetails=orderDetails + " fried egg,";
                }
            }
            orderDetails=orderDetails.substring(0, orderDetails.length() - 1);
            if (!(position==Global.currentCart.size()-1)) {
                orderDetails = orderDetails + "\n\n";
            }
        }
        return orderDetails;
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
            Intent intent = new Intent(CheckOutScreenActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_check_out) {
            Intent intent = new Intent(CheckOutScreenActivity.this, CheckOutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(CheckOutScreenActivity.this, OrderStatusScreenActivity.class);
            startActivity(intent);
        }else if (id == R.id.navigation_about) {
            Intent intent = new Intent(CheckOutScreenActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_sign_out) {
            Paper.book().destroy();
            Intent intent = new Intent(CheckOutScreenActivity.this, CustomerSignInScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}