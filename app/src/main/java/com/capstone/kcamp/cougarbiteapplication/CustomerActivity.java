package com.capstone.kcamp.cougarbiteapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Model.AppUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class CustomerActivity extends AppCompatActivity {
    EditText hNumber,password, phone;
    Button customerSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getSupportActionBar().hide();
        hNumber = (MaterialEditText)findViewById(R.id.HNumber);
        password = (MaterialEditText)findViewById(R.id.Password);
        phone = (MaterialEditText)findViewById(R.id.Phone);
        customerSignIn = (Button)findViewById(R.id.button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("users");

        customerSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(CustomerActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(hNumber.getText().toString()).exists()) {
                            mDialog.dismiss();
                            AppUser user = dataSnapshot.child(hNumber.getText().toString()).getValue(AppUser.class);
                            user.setPhone(phone.getText().toString());
                            if (user.getPassword().equals(password.getText().toString())) {
                                Toast.makeText(CustomerActivity.this, "Sign in success!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CustomerActivity.this, "Sign in failed!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(CustomerActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
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

