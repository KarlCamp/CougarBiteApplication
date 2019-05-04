package com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses;
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

import com.capstone.kcamp.cougarbiteapplication.AboutScreenActivity;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.MenuCategory;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders.MenuScreenViewHolder;
import com.capstone.kcamp.cougarbiteapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.paperdb.Paper;

/**
 * The MenuScreenActivity is the activity that displays all the available food categories in a given
 * menu. Here the user may choose a category of interest to explore. Depending on the choice
 * of category, it will redirect them either to the FoodScreenActivity or the
 * BuildYourOwnScreenActivity. This activity may be accessed by successful verification of
 * customer credentials from CustomerSignInScreenActivity.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class MenuScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference ref; //database reference to be utilized
    RecyclerView recView; //recycler view to be filled
    RecyclerView.LayoutManager layout; //layout manager to assist in laying out the recycler view
    FirebaseRecyclerAdapter<MenuCategory, MenuScreenViewHolder> adapt; //adapter to fill the recycler view

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Binds recycler view.
     *      5. Establishes references to the Firebase database.
     *      6. Initializes Paper, which is a key-value pair object for remember me functionality.
     *      7. Establishes drawer layout context.
     *      8. Creates an instance of the NavigationView.
     *      9. Binds an event to the FloatingActionButton.
     *      10. Fill the adapter.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_menu_screen);

        //see section 3.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //see section 4.
        recView = findViewById(R.id.menu_recycler_view);
        recView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        recView.setLayoutManager(layout);

        //see section 5.
        ref = FirebaseDatabase.getInstance().getReference("foodcategories");

        //see section 6.
        Paper.init(this);

        //see section 7.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //see section 8.
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //see section 9.
        FloatingActionButton cart = findViewById(R.id.menu_checkout_fab);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuScreenActivity.this, CheckOutScreenActivity.class);
                startActivity(intent);
            }
        });

        //see section 10.
        fillAdapter();
    }

    /**
     * The method fillAdapter does the following:
     *      1. Establishes adapter and fills it with appropriate object and view holder type.
     *      2. Populates the view holder by finding appropriate values based on id in xml.
     *      3. Binds on click activities to appropriate values.
     */
    private void fillAdapter() {
        //see section 1.
        adapt = new FirebaseRecyclerAdapter<MenuCategory, MenuScreenViewHolder>(MenuCategory.class,R.layout.menu_item, MenuScreenViewHolder.class,ref) {
            @Override
            protected void populateViewHolder(MenuScreenViewHolder viewHolder, MenuCategory model, int position) {
                //see section 2.
                viewHolder.categoryTitle.setText(model.getText());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.image);

                //see section 3.
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        if (Objects.equals(adapt.getRef(position).getKey(), "06")) { //do BuildYourOwnScreenActivity
                            Intent foodCategory = new Intent(MenuScreenActivity.this, BuildYourOwnScreenActivity.class);
                            startActivity(foodCategory);
                        } else { //do FoodScreenActivity
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
            Intent intent = new Intent(MenuScreenActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_check_out) {
            Intent intent = new Intent(MenuScreenActivity.this, CheckOutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(MenuScreenActivity.this, OrderStatusScreenActivity.class);
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