package com.capstone.kcamp.cougarbiteapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.Objects;
public class CustomerSignInScreenActivity extends AppCompatActivity {
    MaterialEditText hNumber, password, phone;
    Button custSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_in_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        hNumber = findViewById(R.id.hNumber);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        custSignIn = findViewById(R.id.btn);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("customers");
        custSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = new ProgressDialog(CustomerSignInScreenActivity.this);
                dialog.setMessage("Loading...");
                dialog.show();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(hNumber.getText().toString()).exists()) {
                            dialog.dismiss();
                            Customer customer = dataSnapshot.child(hNumber.getText().toString()).getValue(Customer.class);
                            assert customer != null;
                            customer.setHNumber(hNumber.getText().toString());
                            if (customer.getPassword().equals(password.getText().toString())) {
                                if (phone.getText().toString().length() == 12) {
                                    if (customer.getPhone().equals(phone.getText().toString())) {
                                        Common.currentCustomer = customer;
                                        Intent intent = new Intent(CustomerSignInScreenActivity.this, FoodCategories.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(CustomerSignInScreenActivity.this, "Error: incorrect phone!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(CustomerSignInScreenActivity.this, "Error: incorrect phone format!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(CustomerSignInScreenActivity.this, "Error: incorrect password!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            dialog.dismiss();
                            Toast.makeText(CustomerSignInScreenActivity.this, "Error: customer does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseerror) {
                        Toast.makeText(CustomerSignInScreenActivity.this, "Error: issue connecting to database!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}