package com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.CreditCard;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationGlobals.Global;
import com.capstone.kcamp.cougarbiteapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;

import java.util.Objects;

import io.paperdb.Paper;

/**
 * The CreditCardScreenActivity is the activity that allows the customer to process their credit
 * information towards payment. The class will take customer input in the form of credit card
 * number, name, and CVV. In addition, the Remember Me functionality may be checked, which will
 * store the users credit card credentials. This activity be accessed by clicking on the credit
 * card icon and clicking the pay button from the PaymentMethodScreenActivity.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class CreditCardScreenActivity extends AppCompatActivity {
    MaterialEditText cardNumber, creditCardName, CVV; //text views to be filled
    Button processPayment; //button to be activated
    DatabaseReference bank, referenceemployee, referencecustomer, backuprequests; //database references to be utilized
    CreditCard creditCard; //store credit card information
    CheckBox ckbRemember; //store credentials if functionality requested

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Establishes references to the Firebase database.
     *      5. Identifies all values within the xml layout to be filled with appropriate information.
     *      6. Reads values from if any are stored.
     *      7. Asserts if remember me values are null.
     *      8. Binds process payment button.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_credit_card_screen);

        //see section 3.
        Objects.requireNonNull(getSupportActionBar()).hide();

        //see section 4.
        bank = FirebaseDatabase.getInstance().getReference("bank");
        referenceemployee=FirebaseDatabase.getInstance().getReference("employeerequests");
        referencecustomer=FirebaseDatabase.getInstance().getReference("customerrequests");
        backuprequests = FirebaseDatabase.getInstance().getReference("backuprequests");

        //see section 5.
        cardNumber = findViewById(R.id.cardNumber);
        creditCardName = findViewById(R.id.creditCardName);
        CVV = findViewById(R.id.CVV);
        processPayment = findViewById(R.id.processCard);
        ckbRemember = findViewById(R.id.ckbRemember);

        //see section 6.
        String number = Paper.book().read(Global.CARD_NUMBER);
        String name = Paper.book().read(Global.CARD_NAME);
        String cvv = Paper.book().read(Global.CARD_CVV);

        //see section 7.
        if (number != null && name != null && cvv != null) {
            if(!number.isEmpty() && !name.isEmpty() && !cvv.isEmpty()) {
                cardNumber.setText(number);
                creditCardName.setText(name);
            }
        }

        //see section 8.
        processPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckbRemember.isChecked()) { //initialize remember me functionality
                    Paper.book().write(Global.CARD_NUMBER,cardNumber.getText().toString());
                    Paper.book().write(Global.CARD_NAME,creditCardName.getText().toString());
                    Paper.book().write(Global.CARD_CVV, CVV.getText().toString());
                }
                //create dialog
                final ProgressDialog dialog = new ProgressDialog(CreditCardScreenActivity.this);
                dialog.setMessage("Loading...");
                dialog.show();
                //bind event listener and validate values are in database
                bank.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(cardNumber.getText().toString()).exists()) { //key exists
                            dialog.dismiss();
                            creditCard = dataSnapshot.child(cardNumber.getText().toString()).getValue(CreditCard.class); //store credit card number
                            assert creditCard != null; //assert not dealing with null object
                            creditCard.setNumber(cardNumber.getText().toString());
                            if (cardNumber.getText().toString().length() == 16) { //check format of credit card number correct
                                if (creditCard.getName().equals(creditCardName.getText().toString())) { //check credit card name correct
                                    if (CVV.getText().toString().length() == 3) { //check CVV format correct
                                        if (creditCard.getCvv().equals(CVV.getText().toString())) { //check CVV correct
                                            Global.card = creditCard; //store credit card
                                            if (Double.parseDouble(creditCard.getCredit())-Global.currentTotal>=0) { //process payment
                                                double value = Double.parseDouble(creditCard.getCredit())-Global.currentTotal;
                                                bank.child(Global.card.getNumber()).child("credit").setValue(""+Global.formatter.format(value));
                                                String key=String.valueOf(System.currentTimeMillis());
                                                //send requests
                                                referenceemployee.child(key).setValue(Global.orderRequest);
                                                referencecustomer.child(key).setValue(Global.orderRequest);
                                                backuprequests.child(key).setValue(Global.orderRequest);
                                                //reset variables
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