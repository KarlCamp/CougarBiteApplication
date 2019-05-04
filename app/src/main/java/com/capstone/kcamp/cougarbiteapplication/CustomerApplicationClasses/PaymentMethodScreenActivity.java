package com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationGlobals.Global;
import com.capstone.kcamp.cougarbiteapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

/**
 * The PaymentMethodScreenActivity is the activity where the customer may choose the payment method
 * to process their order. They have the option of meals, cash, both, or credit card payments.
 * Further, if they choose credit card they will be sent to the CreditCardScreenActivity to validate
 * their card information. All other forms of payment are already established through logging in.
 * This activity my be accessed by clicking the checkout button the CheckOutScreenActivity.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class PaymentMethodScreenActivity extends AppCompatActivity {
    DatabaseReference referenceemployee, referencecustomer, backuprequests, customer; //database references to utilize
    ImageView meals, cougarCash, creditCard, both; //image views to populate
    FButton pay; //button to activate
    boolean isMeals, isCash, isBoth, isCard; //booleans to deactivate/activate chosen payment method

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Establishes references to the Firebase database.
     *      5. Initializes Paper, which is a key-value pair object for remember me functionality.
     *      6. Identifies all values within the xml layout to be filled with appropriate information.
     *      8. Bind events to pay button.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_payment_method_screen);

        //see section 3.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Payment Method");

        //see section 4.
        customer = FirebaseDatabase.getInstance().getReference("customers");
        referenceemployee = FirebaseDatabase.getInstance().getReference("employeerequests");
        referencecustomer = FirebaseDatabase.getInstance().getReference("customerrequests");
        backuprequests = FirebaseDatabase.getInstance().getReference("backuprequests");

        //see section 5.
        Paper.init(this);

        //see section 6.
        meals = findViewById(R.id.meals);
        cougarCash = findViewById(R.id.cougar_cash);
        creditCard = findViewById(R.id.creditcard);
        both = findViewById(R.id.both);
        pay = findViewById(R.id.pay);
        pay.setEnabled(false);

        //see section 7.
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.currentCart.isEmpty() && Global.currentTotal <= 0) {
                    Toast.makeText(PaymentMethodScreenActivity.this, "Error: Cart is empty!", Toast.LENGTH_LONG).show();
                } else {
                    if (isMeals) { //if meals, utilize meals payment algorithm
                        if (Integer.parseInt(Global.presentCustomer.getMeals()) * 5 - Global.currentTotal >= 0) {
                            int value = 0;
                            Double numberOfMeals = Global.currentTotal / 5;
                            if (Global.currentTotal % 5 == 0) {
                                value = Integer.parseInt(Global.presentCustomer.getMeals()) - (numberOfMeals.intValue());
                            } else {
                                value = Integer.parseInt(Global.presentCustomer.getMeals()) - ((numberOfMeals.intValue()) + 1);
                            }
                            customer.child(Global.presentCustomer.getHNumber()).child("meals").setValue("" + value);
                            Global.presentCustomer.setMeals("" + value);
                            //send requests
                            String key=String.valueOf(System.currentTimeMillis());
                            referenceemployee.child(key).setValue(Global.orderRequest);
                            referencecustomer.child(key).setValue(Global.orderRequest);
                            backuprequests.child(key).setValue(Global.orderRequest);
                            //clear values
                            Global.currentCart.clear();
                            Global.currentCartPrices.clear();
                            Global.currentTotal = 0;
                            Toast.makeText(PaymentMethodScreenActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PaymentMethodScreenActivity.this, "Error: You are short on meals!", Toast.LENGTH_LONG).show();
                            isMeals = false;
                            pay.setEnabled(false);
                        }
                    } else if (isCash) { //if cash, utilize cash payment algorithm
                        if (Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal >= 0) {
                            double newTotal = Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal;
                            customer.child(Global.presentCustomer.getHNumber()).child("cash").setValue("" + Global.formatter.format(newTotal));
                            Global.presentCustomer.setCash("" + Global.formatter.format(newTotal));
                            //send requests
                            String key=String.valueOf(System.currentTimeMillis());
                            referenceemployee.child(key).setValue(Global.orderRequest);
                            referencecustomer.child(key).setValue(Global.orderRequest);
                            backuprequests.child(key).setValue(Global.orderRequest);
                            //clear values
                            Global.currentCart.clear();
                            Global.currentCartPrices.clear();
                            Global.currentTotal = 0;
                            Toast.makeText(PaymentMethodScreenActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PaymentMethodScreenActivity.this, "Error: You are short on cougar cash!", Toast.LENGTH_LONG).show();
                            isCash = false;
                            pay.setEnabled(false);
                        }
                    } else if (isBoth) { //if both, utilize both payment algorithm
                        if ((Double.parseDouble(Global.presentCustomer.getCash()) + Integer.parseInt(Global.presentCustomer.getMeals()) * 5) - Global.currentTotal >= 0) {
                            if (Integer.parseInt(Global.presentCustomer.getMeals()) * 5 - Global.currentTotal >= 0) {
                                int value = 0;
                                double newTotal = 0;
                                Double numberOfMeals = Global.currentTotal / 5;
                                if (Global.currentTotal % 5 == 0) {
                                    value = Integer.parseInt(Global.presentCustomer.getMeals()) - (numberOfMeals.intValue());
                                } else {
                                    value = Integer.parseInt(Global.presentCustomer.getMeals()) - ((numberOfMeals.intValue()));
                                    Global.currentTotal = Global.currentTotal - (numberOfMeals.intValue() * 5);
                                    if (Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal >= 0) {
                                        newTotal = Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal;
                                    } else {
                                        value--;
                                    }
                                }
                                customer.child(Global.presentCustomer.getHNumber()).child("meals").setValue("" + value);
                                customer.child(Global.presentCustomer.getHNumber()).child("cash").setValue("" + newTotal);
                                Global.presentCustomer.setCash("" + newTotal);
                                Global.presentCustomer.setMeals("" + value);
                                //send requests
                                String key=String.valueOf(System.currentTimeMillis());
                                referenceemployee.child(key).setValue(Global.orderRequest);
                                referencecustomer.child(key).setValue(Global.orderRequest);
                                backuprequests.child(key).setValue(Global.orderRequest);
                                //clear values
                                Global.currentCart.clear();
                                Global.currentCartPrices.clear();
                                Global.currentTotal = 0;
                                Toast.makeText(PaymentMethodScreenActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                            } else {
                                int value = 0;
                                double newTotal = 0;
                                Global.currentTotal = Global.currentTotal - Integer.parseInt(Global.presentCustomer.getMeals()) * 5;
                                newTotal = Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal;
                                customer.child(Global.presentCustomer.getHNumber()).child("meals").setValue("" + value);
                                customer.child(Global.presentCustomer.getHNumber()).child("cash").setValue("" + Global.formatter.format(newTotal));
                                Global.presentCustomer.setCash("" + Global.formatter.format(newTotal));
                                Global.presentCustomer.setMeals("" + value);
                                //send requests
                                String key=String.valueOf(System.currentTimeMillis());
                                referenceemployee.child(key).setValue(Global.orderRequest);
                                referencecustomer.child(key).setValue(Global.orderRequest);
                                backuprequests.child(key).setValue(Global.orderRequest);
                                //clear values
                                Global.currentCart.clear();
                                Global.currentCartPrices.clear();
                                Global.currentTotal = 0;
                                Toast.makeText(PaymentMethodScreenActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(PaymentMethodScreenActivity.this, "Error: You are short on meals and cash!", Toast.LENGTH_LONG).show();
                            isMeals = false;
                            pay.setEnabled(false);
                        }
                    } else if (isCard) { //if credit card, go to new screen
                        Intent intent = new Intent(PaymentMethodScreenActivity.this, CreditCardScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        //logic for activating/deactivating payment button based selected payment methods
        meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMeals) {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFF6BBBB);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFF000000);
                    meals.setBackgroundDrawable(gd);
                    isMeals = true;
                    if (isCash) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        cougarCash.setBackgroundDrawable(g);
                        isCash = false;
                        pay.setEnabled(false);
                    }
                    if (isBoth) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        both.setBackgroundDrawable(g);
                        isBoth = false;
                        pay.setEnabled(false);
                    }
                    if (isCard) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        creditCard.setBackgroundDrawable(g);
                        isCard = false;
                        pay.setEnabled(false);
                    }
                    pay.setEnabled(true);
                } else {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFFFFFFF);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFFFFFFFF);
                    meals.setBackgroundDrawable(gd);
                    isMeals = false;
                    pay.setEnabled(false);
                }
            }
        });

        cougarCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCash) {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFF6BBBB);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFF000000);
                    cougarCash.setBackgroundDrawable(gd);
                    isCash=true;
                    if (isMeals) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        meals.setBackgroundDrawable(g);
                        isMeals = false;
                        pay.setEnabled(false);
                    }
                    if (isBoth) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        both.setBackgroundDrawable(g);
                        isBoth = false;
                        pay.setEnabled(false);
                    }
                    if (isCard) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        creditCard.setBackgroundDrawable(g);
                        isCard = false;
                        pay.setEnabled(false);
                    }
                    pay.setEnabled(true);
                } else {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFFFFFFF);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFFFFFFFF);
                    cougarCash.setBackgroundDrawable(gd);
                    isCash = false;
                    pay.setEnabled(false);
                }
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBoth) {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFF6BBBB);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFF000000);
                    both.setBackgroundDrawable(gd);
                    isBoth=true;
                    if (isMeals) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        meals.setBackgroundDrawable(g);
                        isMeals = false;
                        pay.setEnabled(false);
                    }
                    if (isCash) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        cougarCash.setBackgroundDrawable(g);
                        isCash = false;
                        pay.setEnabled(false);
                    }
                    if (isCard) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        creditCard.setBackgroundDrawable(g);
                        isCard = false;
                        pay.setEnabled(false);
                    }
                    pay.setEnabled(true);
                } else {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFFFFFFF);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFFFFFFFF);
                    both.setBackgroundDrawable(gd);
                    isBoth = false;
                    pay.setEnabled(false);
                }
            }
        });
        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCard) {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFF6BBBB);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFF000000);
                    creditCard.setBackgroundDrawable(gd);
                    isCard = true;
                    if (isCash) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        cougarCash.setBackgroundDrawable(g);
                        isCash = false;
                        pay.setEnabled(false);
                    }
                    if (isBoth) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        both.setBackgroundDrawable(g);
                        isBoth = false;
                        pay.setEnabled(false);
                    }
                    if (isMeals) {
                        GradientDrawable g = new GradientDrawable();
                        g.setColor(0xFFFFFFFF);
                        g.setCornerRadius(10);
                        g.setStroke(1, 0xFFFFFFFF);
                        meals.setBackgroundDrawable(g);
                        isMeals = false;
                        pay.setEnabled(false);
                    }
                    pay.setEnabled(true);
                } else if (isCard){
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFFFFFFF);
                    gd.setCornerRadius(10);
                    gd.setStroke(1, 0xFFFFFFFF);
                    creditCard.setBackgroundDrawable(gd);
                    isCard = false;
                    pay.setEnabled(false);
                }
            }
        });
    }
}