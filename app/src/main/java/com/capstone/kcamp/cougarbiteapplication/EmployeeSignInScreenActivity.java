package com.capstone.kcamp.cougarbiteapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.Model.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;
public class EmployeeSignInScreenActivity extends AppCompatActivity {
    MaterialEditText password, name;
    Button empSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_sign_in_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        empSignIn= findViewById(R.id.btn);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("employees");
        empSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = new ProgressDialog(EmployeeSignInScreenActivity.this);
                dialog.setMessage("Loading...");
                dialog.show();
                reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(name.getText().toString()).exists()) {
                                dialog.dismiss();
                                Employee employee = dataSnapshot.child(name.getText().toString()).getValue(Employee.class);
                                assert employee != null;
                                if (employee.getPassword().equals(password.getText().toString())) {
                                    Intent intent = new Intent(EmployeeSignInScreenActivity.this, EmployeeOrderStatus.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(EmployeeSignInScreenActivity.this, "Error: incorrect password!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                dialog.dismiss();
                                Toast.makeText(EmployeeSignInScreenActivity.this, "Error: employee does not exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseerror) {
                            Toast.makeText(EmployeeSignInScreenActivity.this, "Error: issue connecting to database.", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }
}