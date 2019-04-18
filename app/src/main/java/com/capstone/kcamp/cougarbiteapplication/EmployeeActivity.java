package com.capstone.kcamp.cougarbiteapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Model.AppUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class EmployeeActivity extends AppCompatActivity {

    MaterialEditText password, phone;
    Button employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().hide();
        phone = (MaterialEditText)findViewById(R.id.Phone);
        password = (MaterialEditText)findViewById(R.id.Password);

        employee=(Button)findViewById(R.id.buttonEmployee);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("users");

        employee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(EmployeeActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(phone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                AppUser user = dataSnapshot.child(phone.getText().toString()).getValue(AppUser.class);
                                user.setPhone(phone.getText().toString());
                                if (user.getPassword().equals(password.getText().toString())) {
                                    Toast.makeText(EmployeeActivity.this, "Sign in success!", Toast.LENGTH_LONG).show();
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
