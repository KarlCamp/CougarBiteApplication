package com.capstone.kcamp.cougarbiteapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Model.AppUser;
import com.capstone.kcamp.cougarbiteapplication.Model.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class EmployeeActivity extends AppCompatActivity {

    MaterialEditText password, name;
    Button employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().hide();
        name = (MaterialEditText)findViewById(R.id.name);
        password = (MaterialEditText)findViewById(R.id.password);

        employee=(Button)findViewById(R.id.button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("employees");

        employee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(EmployeeActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(name.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Employee employee = dataSnapshot.child(name.getText().toString()).getValue(Employee.class);
                                if (employee.getPassword().equals(password.getText().toString())) {
                                    Intent foodCategories = new Intent(EmployeeActivity.this, EmployeeOrderStatus.class);
                                    startActivity(foodCategories);
                                    finish();
                                } else {
                                    Toast.makeText(EmployeeActivity.this, "Sign in failed!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(EmployeeActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseerror) {

                        }
                    });
                }
            });
        }
    }

