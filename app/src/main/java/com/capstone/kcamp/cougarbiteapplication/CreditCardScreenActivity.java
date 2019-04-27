package com.capstone.kcamp.cougarbiteapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Model.CreditCard;
import com.capstone.kcamp.cougarbiteapplication.Model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.Objects;
public class CreditCardScreenActivity extends AppCompatActivity {
    MaterialEditText cardNumber, creditCardName, CVV;
    Button processPayment;
    DatabaseReference ref, reference;
    CreditCard creditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        cardNumber = findViewById(R.id.cardNumber);
        creditCardName = findViewById(R.id.creditCardName);
        CVV = findViewById(R.id.CVV);
        processPayment = findViewById(R.id.processCard);
        ref = FirebaseDatabase.getInstance().getReference("bank");
        reference = FirebaseDatabase.getInstance().getReference("requests");;
        processPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                            Common.creditCard = creditCard;
                                            processPayment.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (Double.parseDouble(creditCard.getCredit())-Common.total>=0) {
                                                        double value = Double.parseDouble(creditCard.getCredit())-Common.total;
                                                        ref.child(Common.creditCard.getNumber()).child("credit").setValue(""+Common.df.format(value));
                                                        reference.child(String.valueOf(System.currentTimeMillis()))
                                                                .setValue(Common.request);
                                                        Common.cart.clear();
                                                        Common.prices.clear();
                                                        Common.total=0;
                                                        Toast.makeText(CreditCardScreenActivity.this, "Payment processed!", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(CreditCardScreenActivity.this, MenuScreenActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(CreditCardScreenActivity.this, "Error: Insufficient funds to process transaction!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
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