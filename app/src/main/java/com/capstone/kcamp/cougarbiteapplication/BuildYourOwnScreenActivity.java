package com.capstone.kcamp.cougarbiteapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class BuildYourOwnScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    boolean lettuceTopping, tomatoTopping, onionTopping, pickleTopping,
            baconTopping, cheeseTopping, avocadoTopping, fried_eggTopping, chickenTopping, pattyTopping,
            beefTopping, breaded_chickenTopping, black_beanTopping, turkeyTopping, grilled_chickenTopping,
            americanTopping, bleuTopping, pepper_jackTopping, swiss_Topping, provoloneTopping,
            white_kaiserTopping, spinach_wrapTopping, flour_wrapTopping, wheat_kaiserTopping, flat_breadTopping, garlic_wrapTopping, gluten_freeTopping;
    CheckBox lettuce, tomato, onion, pickle, bacon, cheese, avocado, fried_egg, chicken, patty,
            beef, breaded_chicken, black_bean, turkey, grilled_chicken, american, bleu, pepper_jack, swiss, provolone,
            white_kaiser, spinach_wrap, flour_wrap, wheat_kaiser, flat_bread, garlic_wrap, gluten_free;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_your_own_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Build Your Own");
        setSupportActionBar(toolbar);

        lettuce=findViewById(R.id.Lettuce);
        tomato=findViewById(R.id.Tomato);
        onion=findViewById(R.id.Onion);
        pickle=findViewById(R.id.Pickle);

        bacon=findViewById(R.id.Bacon);
        cheese=findViewById(R.id.Cheese);
        avocado=findViewById(R.id.Avocado);
        fried_egg=findViewById(R.id.FriedEgg);
        chicken=findViewById(R.id.Chicken);
        patty=findViewById(R.id.Patty);

        beef=findViewById(R.id.Beef);
        breaded_chicken=findViewById(R.id.Breaded_Chicken);
        black_bean=findViewById(R.id.Black_Bean);
        turkey=findViewById(R.id.Turkey);
        grilled_chicken=findViewById(R.id.Grilled_Chicken);

        american=findViewById(R.id.American);
        bleu=findViewById(R.id.Bleu);
        pepper_jack=findViewById(R.id.Pepper_Jack);
        swiss=findViewById(R.id.Swiss);
        provolone=findViewById(R.id.Provolone);

        white_kaiser=findViewById(R.id.White_Kaiser);
        spinach_wrap=findViewById(R.id.Spinach_Wrap);
        flour_wrap=findViewById(R.id.Flour_Wrap);
        wheat_kaiser=findViewById(R.id.Wheat_Kaiser);
        flat_bread=findViewById(R.id.Flat_Bread);
        garlic_wrap=findViewById(R.id.Garlic_Wrap);
        gluten_free=findViewById(R.id.Gluten_Free);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FloatingActionButton fab = findViewById(R.id.heyHey);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beefTopping || breaded_chickenTopping || black_beanTopping || turkeyTopping || grilled_chickenTopping) {
                    if (white_kaiserTopping || wheat_kaiserTopping || spinach_wrapTopping || flat_breadTopping || flour_wrapTopping || garlic_wrapTopping || gluten_freeTopping) {
                        Order order = new Order("", "Build Your Own", "1", "8.00",
                                lettuceTopping, tomatoTopping, onionTopping, pickleTopping, baconTopping, cheeseTopping, avocadoTopping,
                                fried_eggTopping, chickenTopping, pattyTopping, beefTopping, breaded_chickenTopping, black_beanTopping, turkeyTopping, grilled_chickenTopping,
                                americanTopping, bleuTopping, pepper_jackTopping, swiss_Topping, provoloneTopping, white_kaiserTopping, wheat_kaiserTopping, spinach_wrapTopping,
                                flour_wrapTopping, flat_breadTopping, garlic_wrapTopping, gluten_freeTopping);
                        Common.cart.add(order);
                        Toast.makeText(BuildYourOwnScreenActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BuildYourOwnScreenActivity.this, "Error: Pick one bread.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BuildYourOwnScreenActivity.this, "Error: Pick one meat.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        beef.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    beefTopping=true;
                    breaded_chicken.setEnabled(false);
                    black_bean.setEnabled(false);
                    turkey.setEnabled(false);
                    grilled_chicken.setEnabled(false);
                } else {
                    beefTopping=false;
                    breaded_chicken.setEnabled(true);
                    black_bean.setEnabled(true);
                    turkey.setEnabled(true);
                    grilled_chicken.setEnabled(true);
                }
            }
        });
        breaded_chicken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    breaded_chickenTopping=true;
                    beef.setEnabled(false);
                    black_bean.setEnabled(false);
                    turkey.setEnabled(false);
                    grilled_chicken.setEnabled(false);
                } else {
                    breaded_chickenTopping=false;
                    beef.setEnabled(true);
                    black_bean.setEnabled(true);
                    turkey.setEnabled(true);
                    grilled_chicken.setEnabled(true);
                }
            }
        });
        black_bean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    black_beanTopping=true;
                    beef.setEnabled(false);
                    breaded_chicken.setEnabled(false);
                    turkey.setEnabled(false);
                    grilled_chicken.setEnabled(false);
                } else {
                    black_beanTopping=false;
                    beef.setEnabled(true);
                    breaded_chicken.setEnabled(true);
                    turkey.setEnabled(true);
                    grilled_chicken.setEnabled(true);
                }
            }
        });
        turkey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turkeyTopping=true;
                    beef.setEnabled(false);
                    breaded_chicken.setEnabled(false);
                    black_bean.setEnabled(false);
                    grilled_chicken.setEnabled(false);
                } else {
                    turkeyTopping=false;
                    beef.setEnabled(true);
                    breaded_chicken.setEnabled(true);
                    black_bean.setEnabled(true);
                    grilled_chicken.setEnabled(true);
                }
            }
        });
        grilled_chicken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    grilled_chickenTopping=true;
                    beef.setEnabled(false);
                    breaded_chicken.setEnabled(false);
                    black_bean.setEnabled(false);
                    turkey.setEnabled(false);
                } else {
                    grilled_chickenTopping=false;
                    beef.setEnabled(true);
                    breaded_chicken.setEnabled(true);
                    black_bean.setEnabled(true);
                    turkey.setEnabled(true);
                }
            }
        });
        american.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    americanTopping=true;
                    bleu.setEnabled(false);
                    pepper_jack.setEnabled(false);
                    swiss.setEnabled(false);
                    provolone.setEnabled(false);
                } else {
                    americanTopping=false;
                    bleu.setEnabled(true);
                    pepper_jack.setEnabled(true);
                    swiss.setEnabled(true);
                    provolone.setEnabled(true);
                }
            }
        });
        bleu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bleuTopping=true;
                    american.setEnabled(false);
                    pepper_jack.setEnabled(false);
                    swiss.setEnabled(false);
                    provolone.setEnabled(false);
                } else {
                    bleuTopping=false;
                    american.setEnabled(true);
                    pepper_jack.setEnabled(true);
                    swiss.setEnabled(true);
                    provolone.setEnabled(true);
                }
            }
        });
        pepper_jack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pepper_jackTopping=true;
                    american.setEnabled(false);
                    bleu.setEnabled(false);
                    swiss.setEnabled(false);
                    provolone.setEnabled(false);
                } else {
                    pepper_jackTopping=false;
                    american.setEnabled(true);
                    bleu.setEnabled(true);
                    swiss.setEnabled(true);
                    provolone.setEnabled(true);
                }
            }
        });
        swiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swiss_Topping=true;
                    american.setEnabled(false);
                    bleu.setEnabled(false);
                    pepper_jack.setEnabled(false);
                    provolone.setEnabled(false);
                } else {
                    swiss_Topping=false;
                    american.setEnabled(true);
                    bleu.setEnabled(true);
                    pepper_jack.setEnabled(true);
                    provolone.setEnabled(true);
                }
            }
        });
        provolone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    provoloneTopping=true;
                    american.setEnabled(false);
                    bleu.setEnabled(false);
                    pepper_jack.setEnabled(false);
                    swiss.setEnabled(false);
                } else {
                    provoloneTopping=false;
                    american.setEnabled(true);
                    bleu.setEnabled(true);
                    pepper_jack.setEnabled(true);
                    swiss.setEnabled(true);
                }
            }
        });
        white_kaiser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    white_kaiserTopping=true;
                    spinach_wrap.setEnabled(false);
                    flour_wrap.setEnabled(false);
                    wheat_kaiser.setEnabled(false);
                    flat_bread.setEnabled(false);
                    garlic_wrap.setEnabled(false);
                    gluten_free.setEnabled(false);
                } else {
                    white_kaiserTopping=false;
                    spinach_wrap.setEnabled(true);
                    flour_wrap.setEnabled(true);
                    wheat_kaiser.setEnabled(true);
                    flat_bread.setEnabled(true);
                    garlic_wrap.setEnabled(true);
                    gluten_free.setEnabled(true);
                }
            }
        });
        spinach_wrap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinach_wrapTopping=true;
                    white_kaiser.setEnabled(false);
                    flour_wrap.setEnabled(false);
                    wheat_kaiser.setEnabled(false);
                    flat_bread.setEnabled(false);
                    garlic_wrap.setEnabled(false);
                    gluten_free.setEnabled(false);
                } else {
                    spinach_wrapTopping=false;
                    white_kaiser.setEnabled(true);
                    flour_wrap.setEnabled(true);
                    wheat_kaiser.setEnabled(true);
                    flat_bread.setEnabled(true);
                    garlic_wrap.setEnabled(true);
                    gluten_free.setEnabled(true);
                }
            }
        });
        flour_wrap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flour_wrapTopping=true;
                    white_kaiser.setEnabled(false);
                    spinach_wrap.setEnabled(false);
                    wheat_kaiser.setEnabled(false);
                    flat_bread.setEnabled(false);
                    garlic_wrap.setEnabled(false);
                    gluten_free.setEnabled(false);
                } else {
                    flour_wrapTopping=false;
                    white_kaiser.setEnabled(true);
                    spinach_wrap.setEnabled(true);
                    wheat_kaiser.setEnabled(true);
                    flat_bread.setEnabled(true);
                    garlic_wrap.setEnabled(true);
                    gluten_free.setEnabled(true);
                }
            }
        });
        wheat_kaiser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wheat_kaiserTopping=true;
                    white_kaiser.setEnabled(false);
                    spinach_wrap.setEnabled(false);
                    flour_wrap.setEnabled(false);
                    flat_bread.setEnabled(false);
                    garlic_wrap.setEnabled(false);
                    gluten_free.setEnabled(false);
                } else {
                    wheat_kaiserTopping=false;
                    white_kaiser.setEnabled(true);
                    spinach_wrap.setEnabled(true);
                    flour_wrap.setEnabled(true);
                    flat_bread.setEnabled(true);
                    garlic_wrap.setEnabled(true);
                    gluten_free.setEnabled(true);
                }
            }
        });
        flat_bread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flat_breadTopping=true;
                    white_kaiser.setEnabled(false);
                    spinach_wrap.setEnabled(false);
                    flour_wrap.setEnabled(false);
                    wheat_kaiser.setEnabled(false);
                    garlic_wrap.setEnabled(false);
                    gluten_free.setEnabled(false);
                } else {
                    flat_breadTopping=false;
                    white_kaiser.setEnabled(true);
                    spinach_wrap.setEnabled(true);
                    flour_wrap.setEnabled(true);
                    wheat_kaiser.setEnabled(true);
                    garlic_wrap.setEnabled(true);
                    gluten_free.setEnabled(true);
                }
            }
        });
        garlic_wrap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    garlic_wrapTopping=true;
                    white_kaiser.setEnabled(false);
                    spinach_wrap.setEnabled(false);
                    flour_wrap.setEnabled(false);
                    wheat_kaiser.setEnabled(false);
                    flat_bread.setEnabled(false);
                    gluten_free.setEnabled(false);
                } else {
                    garlic_wrapTopping=false;
                    white_kaiser.setEnabled(true);
                    spinach_wrap.setEnabled(true);
                    flour_wrap.setEnabled(true);
                    wheat_kaiser.setEnabled(true);
                    flat_bread.setEnabled(true);
                    gluten_free.setEnabled(true);
                }
            }
        });
        gluten_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gluten_freeTopping=true;
                    white_kaiser.setEnabled(false);
                    spinach_wrap.setEnabled(false);
                    flour_wrap.setEnabled(false);
                    wheat_kaiser.setEnabled(false);
                    flat_bread.setEnabled(false);
                    garlic_wrap.setEnabled(false);
                } else {
                    gluten_freeTopping=false;
                    white_kaiser.setEnabled(true);
                    spinach_wrap.setEnabled(true);
                    flour_wrap.setEnabled(true);
                    wheat_kaiser.setEnabled(true);
                    flat_bread.setEnabled(true);
                    garlic_wrap.setEnabled(true);
                }
            }
        });
        lettuce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lettuceTopping = true;
                } else {
                    lettuceTopping = false;
                }
            }
        });
        tomato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tomatoTopping = true;
                } else {
                    tomatoTopping = false;
                }
            }
        });
        onion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onionTopping = true;
                } else {
                    onionTopping = false;
                }
            }
        });
        pickle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pickleTopping = true;
                } else {
                    pickleTopping = false;
                }
            }
        });
        bacon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    baconTopping = true;
                } else {
                    baconTopping = false;
                }
            }
        });
        cheese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cheeseTopping = true;
                } else {
                    cheeseTopping = false;
                }
            }
        });
        avocado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    avocadoTopping = true;
                } else {
                    avocadoTopping = false;
                }
            }
        });
        fried_egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fried_eggTopping = true;
                } else {
                    fried_eggTopping = false;
                }
            }
        });
        chicken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chickenTopping = true;
                } else {
                    chickenTopping = false;
                }
            }
        });
        patty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pattyTopping = true;
                } else {
                    pattyTopping = false;
                }
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
            Intent intent = new Intent(BuildYourOwnScreenActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_check_out) {
            Intent intent = new Intent(BuildYourOwnScreenActivity.this, CheckOutActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_pay) {
            Intent intent = new Intent(BuildYourOwnScreenActivity.this, PaymentMethodActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_order_status) {
            Intent intent = new Intent(BuildYourOwnScreenActivity.this, OrderStatusActivity.class);
            startActivity(intent);
        }else if (id == R.id.navigation_about) {
            Intent intent = new Intent(BuildYourOwnScreenActivity.this, AboutScreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_sign_out) {
            Intent intent = new Intent(BuildYourOwnScreenActivity.this, CustomerSignInScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}