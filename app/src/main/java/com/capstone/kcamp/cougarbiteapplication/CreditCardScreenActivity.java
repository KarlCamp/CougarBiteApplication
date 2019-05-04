package com.capstone.kcamp.cougarbiteapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.CreditCard;
import com.capstone.kcamp.cougarbiteapplication.Global.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;

import java.util.Objects;

import io.paperdb.Paper;

public class CreditCardScreenActivity extends AppCompatActivity {
    MaterialEditText cardNumber, creditCardName, CVV;
    Button processPayment;
    DatabaseReference ref, referenceemployee, referencecustomer;
    CreditCard creditCard;
    CheckBox ckbRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        cardNumber = findViewById(R.id.cardNumber);
        creditCardName = findViewById(R.id.creditCardName);
        CVV = findViewById(R.id.CVV);
        processPayment = findViewById(R.id.processCard);
        ckbRemember = findViewById(R.id.ckbRemember);
        ref = FirebaseDatabase.getInstance().getReference("bank");
        referenceemployee=FirebaseDatabase.getInstance().getReference("employeerequests");
        referencecustomer=FirebaseDatabase.getInstance().getReference("customerrequests");

        String number = Paper.book().read(Global.CARD_NUMBER);
        String name = Paper.book().read(Global.CARD_NAME);
        String cvv = Paper.book().read(Global.CARD_CVV);
        if (number != null && name != null && cvv != null) {
            if(!number.isEmpty() && !name.isEmpty() && !cvv.isEmpty()) {
                cardNumber.setText(number);
                creditCardName.setText(name);
                CVV.setText(cvv);
            }
        }
        processPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckbRemember.isChecked()) {
                    Paper.book().write(Global.CARD_NUMBER,cardNumber.getText().toString());
                    Paper.book().write(Global.CARD_NAME,creditCardName.getText().toString());
                    Paper.book().write(Global.CARD_CVV, CVV.getText().toString());
                }
                final ProgressDialog dialog = new ProgressDialog(CreditCardScreenActivity.this);
                dialog.setMessage("Loading...");
                dialog.show();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(cardNumber.getText().toString()).exists()) {
                            dialog.dismiss();
                            creditCard = dataSnapshot.child(cardNumber.getText().toString()).getValue(CreditCard.class);
                            assert creditCard != null;
                            creditCard.setNumber(cardNumber.getText().toString());
                            if (cardNumber.getText().toString().length() == 16) {
                                if (creditCard.getName().equals(creditCardName.getText().toString())) {
                                    if (CVV.getText().toString().length() == 3) {
                                        if (creditCard.getCvv().equals(CVV.getText().toString())) {
                                            Global.card = creditCard;
                                            if (Double.parseDouble(creditCard.getCredit())-Global.currentTotal>=0) {
                                                double value = Double.parseDouble(creditCard.getCredit())-Global.currentTotal;
                                                ref.child(Global.card.getNumber()).child("credit").setValue(""+Global.formatter.format(value));
                                                String key=String.valueOf(System.currentTimeMillis());
                                                referenceemployee.child(key).setValue(Global.orderRequest);
                                                referencecustomer.child(key).setValue(Global.orderRequest);
                                                Global.currentCart.clear();
                                                Global.currentCartPrices.clear();
                                                Global.currentTotal=0;
                                            } else {
                                                Toast.makeText(CreditCardScreenActivity.this, "Error: Insufficient funds to process transaction!", Toast.LENGTH_LONG).show();
                                            }
                                            Toast.makeText(CreditCardScreenActivity.this, "Payment processed successfully!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(CreditCardScreenActivity.this, MenuScreenActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(CreditCardScreenActivity.this, "Error: Incorrect CVV!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(CreditCardScreenActivity.this, "Error: Incorrect CVV format!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(CreditCardScreenActivity.this, "Error: Incorrect card name!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(CreditCardScreenActivity.this, "Error: Card number does not exist!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                            Toast.makeText(CreditCardScreenActivity.this, "Error: Please check format of card number!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseerror) {
                        Toast.makeText(CreditCardScreenActivity.this, "Error: Issue connecting to database!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}