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
import com.capstone.kcamp.cougarbiteapplication.Model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.paperdb.Paper;

public class StartScreenActivity extends AppCompatActivity {
    Button btnCust, btnEmp, btnAbt;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();//hide toolbar
        btnCust = (Button)findViewById(R.id.btnCust);
        btnEmp = (Button)findViewById(R.id.btnEmp);
        btnAbt = (Button)findViewById(R.id.btnAbt);
        Paper.init(this);
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
        String hnumber = Paper.book().read(Common.HNUMBER_KEY);
        String phone = Paper.book().read(Common.PHONE_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if (hnumber != null && pwd != null && phone != null) {
            if(!hnumber.isEmpty() && !pwd.isEmpty() && !phone.isEmpty()) {
                login(hnumber, phone, pwd);
            }
        }
    }
    private void login(final String hNumber, final String phone, final String password) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customers");
        final ProgressDialog dialog = new ProgressDialog(StartScreenActivity.this);
        dialog.setMessage("Loading...");
        dialog.show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(hNumber).exists()) {
                    dialog.dismiss();
                    customer = dataSnapshot.child(hNumber).getValue(Customer.class);
                    assert customer != null;
                    customer.setHNumber(hNumber);
                    if (customer.getPassword().equals(password)) {
                        if (phone.length() == 12) {
                            if (customer.getPhone().equals(phone)) {
                                Common.currentCustomer = customer;
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