package com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Customer;
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
 * The CustomerSignInScreenActivity class is the activity that allows an employee to sign in
 * securely to process customer orders. The class will need their hNumber, phone number, and crf_email password
 * crf_email password to access their account. This activity may be accessed through the
 * StartScreenActivity customer button.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class CustomerSignInScreenActivity extends AppCompatActivity {
    MaterialEditText hNumber, password, phone; //text views to be filled
    Button custSignIn; //button to be activated
    DatabaseReference ref; //database reference to utilize
    Customer customer; //to store current customer information
    CheckBox ckbRemember; //store credentials if functionality requested

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Establishes references to the Firebase database.
     *      5. Identifies all values within the xml layout to be filled with appropriate information.
     *      6. Initializes Paper, which is a key-value pair object for remember me functionality.
     *      7. Adds event listeners to validate customer credential input.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_customer_sign_in_screen);

        //see section 3.
        Objects.requireNonNull(getSupportActionBar()).hide();

        //see section 4.
        ref = FirebaseDatabase.getInstance().getReference("customers");

        //see section 5.
        hNumber = findViewById(R.id.hNumber);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        custSignIn = findViewById(R.id.btn);
        ckbRemember = findViewById(R.id.ckbRemember);

        //see section 6.
        Paper.init(this);

        //see section 7.
        custSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check remember me functionality
                if (ckbRemember.isChecked()) {
                    Paper.book().write(Global.HNUMBER,hNumber.getText().toString());
                    Paper.book().write(Global.PHONE,phone.getText().toString());
                    Paper.book().write(Global.PASSWORD,password.getText().toString());
                }

                //display progress bar
                final ProgressDialog dialog = new ProgressDialog(CustomerSignInScreenActivity.this);
                dialog.setMessage("Loading...");
                dialog.show();

                //bind activity based on database information
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(hNumber.getText().toString()).exists()) { //check if key exists
                            dialog.dismiss();
                            customer = dataSnapshot.child(hNumber.getText().toString()).getValue(Customer.class); //get object, based Hnumber value
                            assert customer != null; //make sure the object is not empty
                            customer.setHNumber(hNumber.getText().toString());
                            if (customer.getPassword().equals(password.getText().toString())) { //check if password is correct
                                if (phone.getText().toString().length() == 12) { //check if phone format is correct
                                    if (customer.getPhone().equals(phone.getText().toString())) { //check if phone is correct
                                        //if successful, start menu activity
                                        Global.presentCustomer = customer;
                                        Intent intent = new Intent(CustomerSignInScreenActivity.this, MenuScreenActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(CustomerSignInScreenActivity.this, "Error: Incorrect phone!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(CustomerSignInScreenActivity.this, "Error: Incorrect phone format!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(CustomerSignInScreenActivity.this, "Error: Incorrect password!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            dialog.dismiss();
                            Toast.makeText(CustomerSignInScreenActivity.this, "Error: Customer does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseerror) {
                        Toast.makeText(CustomerSignInScreenActivity.this, "Error: Issue connecting to database!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}