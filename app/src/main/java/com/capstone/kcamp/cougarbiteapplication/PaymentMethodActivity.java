package com.capstone.kcamp.cougarbiteapplication;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.Global.Global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class PaymentMethodActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference referenceemployee, referencecustomer, customer;
    ImageView meals, cougarCash, creditCard, both;
    FButton pay;
    boolean isMeals, isCash, isBoth, isCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Payment Method");

        database = FirebaseDatabase.getInstance();
        customer = FirebaseDatabase.getInstance().getReference("customers");

        meals = findViewById(R.id.meals);
        cougarCash = findViewById(R.id.cougar_cash);
        creditCard = findViewById(R.id.creditcard);
        both = findViewById(R.id.both);
        pay = findViewById(R.id.pay);
        pay.setEnabled(false);
        database = FirebaseDatabase.getInstance();
        referenceemployee=database.getReference("employeerequests");
        referencecustomer=database.getReference("customerrequests");
        Paper.init(this);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.currentCart.isEmpty() && Global.currentTotal <= 0) {
                    Toast.makeText(PaymentMethodActivity.this, "Error: Cart is empty!", Toast.LENGTH_LONG).show();
                } else {
                    if (isMeals) {
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
                            String key=String.valueOf(System.currentTimeMillis());
                            referenceemployee.child(key).setValue(Global.orderRequest);
                            referencecustomer.child(key).setValue(Global.orderRequest);
                            Global.currentCart.clear();
                            Global.currentCartPrices.clear();
                            Global.currentTotal = 0;
                            Toast.makeText(PaymentMethodActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PaymentMethodActivity.this, "Error: You are short on meals!", Toast.LENGTH_LONG).show();
                            isMeals = false;
                            pay.setEnabled(false);
                        }
                    } else if (isCash) {
                        if (Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal >= 0) {
                            double newTotal = Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal;
                            customer.child(Global.presentCustomer.getHNumber()).child("cash").setValue("" + Global.formatter.format(newTotal));
                            Global.presentCustomer.setCash("" + Global.formatter.format(newTotal));
                            String key=String.valueOf(System.currentTimeMillis());
                            referenceemployee.child(key).setValue(Global.orderRequest);
                            referencecustomer.child(key).setValue(Global.orderRequest);
                            Global.currentCart.clear();
                            Global.currentCartPrices.clear();
                            Global.currentTotal = 0;
                            Toast.makeText(PaymentMethodActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PaymentMethodActivity.this, "Error: You are short on cougar cash!", Toast.LENGTH_LONG).show();
                            isCash = false;
                            pay.setEnabled(false);
                        }
                    } else if (isBoth) {
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
                                String key=String.valueOf(System.currentTimeMillis());
                                referenceemployee.child(key).setValue(Global.orderRequest);
                                referencecustomer.child(key).setValue(Global.orderRequest);
                                Global.currentCart.clear();
                                Global.currentCartPrices.clear();
                                Global.currentTotal = 0;
                                Toast.makeText(PaymentMethodActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                            } else {
                                int value = 0;
                                double newTotal = 0;
                                Global.currentTotal = Global.currentTotal - Integer.parseInt(Global.presentCustomer.getMeals()) * 5;
                                newTotal = Double.parseDouble(Global.presentCustomer.getCash()) - Global.currentTotal;
                                customer.child(Global.presentCustomer.getHNumber()).child("meals").setValue("" + value);
                                customer.child(Global.presentCustomer.getHNumber()).child("cash").setValue("" + Global.formatter.format(newTotal));
                                Global.presentCustomer.setCash("" + Global.formatter.format(newTotal));
                                Global.presentCustomer.setMeals("" + value);
                                String key=String.valueOf(System.currentTimeMillis());
                                referenceemployee.child(key).setValue(Global.orderRequest);
                                referencecustomer.child(key).setValue(Global.orderRequest);
                                Global.currentCart.clear();
                                Global.currentCartPrices.clear();
                                Global.currentTotal = 0;
                                Toast.makeText(PaymentMethodActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(PaymentMethodActivity.this, "Error: You are short on meals and cash!", Toast.LENGTH_LONG).show();
                            isMeals = false;
                            pay.setEnabled(false);
                        }
                    } else if (isCard) {
                        Intent intent = new Intent(PaymentMethodActivity.this, CreditCardScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

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