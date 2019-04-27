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
import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Model.Request;
import com.capstone.kcamp.cougarbiteapplication.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.NumberFormat;
import java.util.Locale;
import info.hoang8f.widget.FButton;
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

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.cart.isEmpty()) {
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
                    Common.request = new Request(
                            Common.currentCustomer.getPhone(),
                            Common.currentCustomer.getHNumber(),
                            "" + edtTime.getHour() + ":" + edtTime.getMinute(),
                            txtTotalPrice.getText().toString(),
                            Common.cart
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
        CartAdapter adapter = new CartAdapter(Common.cart, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Common.total = 0;
        for (int position = 0; position < Common.cart.size(); position++) {
            double price=(Double.parseDouble(Common.cart.get(position).getPrice())) * (Double.parseDouble(Common.cart.get(position).getQuantity()));
            if (Common.cart.get(position).isBacon() || Common.cart.get(position).isAvocado()
                    ||Common.cart.get(position).isCheese() ||Common.cart.get(position).isChicken()
                    ||Common.cart.get(position).isPatty() ||Common.cart.get(position).isFried_egg()) {
                if (Common.cart.get(position).isBacon()) {
                    price+=1.59* (Double.parseDouble(Common.cart.get(position).getQuantity()));
                }
                if (Common.cart.get(position).isAvocado()) {
                    price+=1.59* (Double.parseDouble(Common.cart.get(position).getQuantity()));
                }
                if (Common.cart.get(position).isCheese()) {
                    price+=0.89* (Double.parseDouble(Common.cart.get(position).getQuantity()));
                }
                if (Common.cart.get(position).isPatty()) {
                    price+=2.19* (Double.parseDouble(Common.cart.get(position).getQuantity()));
                }
                if (Common.cart.get(position).isChicken()) {
                    price+=2.49* (Double.parseDouble(Common.cart.get(position).getQuantity()));
                }
                if (Common.cart.get(position).isFried_egg()) {
                    price+=1.29* (Double.parseDouble(Common.cart.get(position).getQuantity()));
                }
            }
            Common.total+=price;
            Common.prices.add(price);
        }
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(Common.total));
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
        } else if (id == R.id.navigation_pay) {
            Intent intent = new Intent(CheckOutActivity.this, PaymentMethodActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(CheckOutActivity.this, OrderStatusActivity.class);
            startActivity(intent);
        }else if (id == R.id.navigation_about) {
            Intent intent = new Intent(CheckOutActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_sign_out) {
            Intent intent = new Intent(CheckOutActivity.this, CustomerSignInScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}