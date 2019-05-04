package com.capstone.kcamp.cougarbiteapplication;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Request;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.CartAdapter;
import com.capstone.kcamp.cougarbiteapplication.Global.Global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.NumberFormat;
import java.util.Locale;
import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class CheckOutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txt_crt_name, txt_price;
    public TextView txtTotalPrice;
    FButton btnPlace;
    ImageView removeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Check Out");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txt_crt_name = findViewById(R.id.cart_item_name);
        txt_price = findViewById(R.id.cart_item_price);
        database = FirebaseDatabase.getInstance();
        requests=database.getReference("requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);
        Paper.init(this);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.currentCart.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this, "Error: Cart is empty.", Toast.LENGTH_LONG).show();
                } else {
                    showAlertDialog();
                }
            }
        });
        loadListFood();
    }

    private void showAlertDialog() {
        TimePickerDialog.Builder alertDialog = new TimePickerDialog.Builder(CheckOutActivity.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your pick up time: ");
        final TimePicker edtTime = new TimePicker(CheckOutActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtTime.setLayoutParams(lp);
        alertDialog.setView(edtTime);
        alertDialog.setIcon(R.drawable.ic_access_time_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ((edtTime.getHour()>=11) && (edtTime.getHour()<23)) {
                    String orderDetails = createOrderDetails();
                    Global.orderRequest = new Request(
                            Global.presentCustomer.getPhone(),
                            Global.presentCustomer.getHNumber(),
                            "" + edtTime.getHour() + ":" + (edtTime.getMinute()<10 ? "0":"")+edtTime.getMinute(),
                            orderDetails,
                            txtTotalPrice.getText().toString(),
                            Global.currentCart
                    );
                    Toast.makeText(CheckOutActivity.this, "Cart processed!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckOutActivity.this, PaymentMethodActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CheckOutActivity.this, "Error: Hours from 11AM-11PM.", Toast.LENGTH_LONG).show();
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
    private void loadListFood() {
        CartAdapter adapter = new CartAdapter(Global.currentCart, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Global.currentTotal = 0;
        for (int position = 0; position < Global.currentCart.size(); position++) {
            double price=(Double.parseDouble(Global.currentCart.get(position).getPrice())) * (Double.parseDouble(Global.currentCart.get(position).getQuantity()));
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
            Global.currentTotal+=price;
            Global.currentCartPrices.add(price);
        }
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(Global.currentTotal));
    }
    private String createOrderDetails() {
        String orderDetails="";
        for (int position = 0; position < Global.currentCart.size(); position++) {
            orderDetails=orderDetails+Global.currentCart.get(position).getProductname()+" x "+Global.currentCart.get(position).getQuantity()+":";
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
                if (Global.currentCart.get(position).isBeef()) {
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
            Intent intent = new Intent(CheckOutActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_check_out) {
            Intent intent = new Intent(CheckOutActivity.this, CheckOutActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(CheckOutActivity.this, OrderStatusActivity.class);
            startActivity(intent);
        }else if (id == R.id.navigation_about) {
            Intent intent = new Intent(CheckOutActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_sign_out) {
            Paper.book().destroy();
            Intent intent = new Intent(CheckOutActivity.this, CustomerSignInScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}