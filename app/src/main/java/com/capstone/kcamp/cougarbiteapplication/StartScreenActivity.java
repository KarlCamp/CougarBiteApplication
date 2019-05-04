package com.capstone.kcamp.cougarbiteapplication;
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
import com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses.CustomerSignInScreenActivity;
import com.capstone.kcamp.cougarbiteapplication.CustomerApplicationClasses.MenuScreenActivity;
import com.capstone.kcamp.cougarbiteapplication.EmployeeApplicationClasses.EmployeeSignInScreenActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.paperdb.Paper;

/**
 * The StartScreenActivity class is the initial point of access of the application for any user.
 * The screen provides buttons to access the application as an employee, customer, or simply check
 * what CougarBite is all about. This activity may be accessed by starting the application.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class StartScreenActivity extends AppCompatActivity {
    Button btnCust, btnEmp, btnAbt; //buttons to be activated
    Customer customer; //customer to store upon remember me functionality

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Initializes Paper, which is a key-value pair object for remember me functionality.
     *      5. Identifies all values within the xml layout to be filled with appropriate information.
     *      6. Adds event listeners to update button information.
     *      7. Retrieves remember me values from CommonApplicationGlobals.
     *      8. Asserts if remember me values are null.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_start_screen);

        //see section 3.
        Objects.requireNonNull(getSupportActionBar()).hide();//hide toolbar

        //see section 4.
        Paper.init(this);

        //see section 5.
        btnCust = findViewById(R.id.btnCust);
        btnEmp = findViewById(R.id.btnEmp);
        btnAbt = findViewById(R.id.btnAbt);

        //see section 6.
        btnCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartScreenActivity.this, CustomerSignInScreenActivity.class);
                startActivity(intent);
            }
        });
        btnEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartScreenActivity.this, EmployeeSignInScreenActivity.class);
                startActivity(intent);
            }
        });
        btnAbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartScreenActivity.this, AboutScreenActivity.class);
                startActivity(intent);
            }
        });

        //see section 7.
        String hnumber = Paper.book().read(Global.HNUMBER);
        String phone = Paper.book().read(Global.PHONE);
        String pwd = Paper.book().read(Global.PASSWORD);

        //see section 8.
        if (hnumber != null && pwd != null && phone != null) {
            if(!hnumber.isEmpty() && !pwd.isEmpty() && !phone.isEmpty()) {
                login(hnumber, phone, pwd);
            }
        }
    }

    /**
     * The login method does the following:
     *      1. Create Firebase database reference to table customers.
     *      2. Displays dialog loading screen.
     *      3. Creates an event listener that does basic information checking.
     *      4. Upon successful passing of all checks, the user will be sent to the MenuScreenActivity.
     *
     * @param hNumber customer hNumber from remember me customer login functionality.
     * @param phone customer phone number from remember me customer login functionality.
     * @param password customer crf_email password from remember me customer login functionality.
     */
    private void login(final String hNumber, final String phone, final String password) {
        //see section 1.
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customers");

        //see section 2.
        final ProgressDialog dialog = new ProgressDialog(StartScreenActivity.this);
        dialog.setMessage("Loading...");
        dialog.show();

        //see section 3.
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(hNumber).exists()) { //check key existence
                    dialog.dismiss();
                    customer = dataSnapshot.child(hNumber).getValue(Customer.class);
                    assert customer != null;
                    customer.setHNumber(hNumber);
                    if (customer.getPassword().equals(password)) { //check password is correct
                        if (phone.length() == 12) { //check phone number length is correct
                            if (customer.getPhone().equals(phone)) { //check phone number is correct

                                //see section 4.
                                Global.presentCustomer = customer; //store current customer information
                                Intent intent = new Intent(StartScreenActivity.this, MenuScreenActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(StartScreenActivity.this, "Error: Incorrect phone!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(StartScreenActivity.this, "Error: Incorrect phone format!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(StartScreenActivity.this, "Error: Incorrect password!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(StartScreenActivity.this, "Error: Customer does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {
                Toast.makeText(StartScreenActivity.this, "Error: Issue connecting to database!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}