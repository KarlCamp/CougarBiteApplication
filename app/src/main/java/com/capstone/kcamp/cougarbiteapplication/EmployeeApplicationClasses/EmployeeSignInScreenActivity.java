package com.capstone.kcamp.cougarbiteapplication.EmployeeApplicationClasses;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Employee;
import com.capstone.kcamp.cougarbiteapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

/**
 * The EmployeeSignInScreenActivity class is the activity that allows an employee to sign in
 * securely to process customer orders. The class will need their name and crf_email password
 * to access their account. This activity may be accessed through the StartScreenActivity
 * employee button.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class EmployeeSignInScreenActivity extends AppCompatActivity {
    MaterialEditText password, name; //text views to be filled
    Button empSignIn; //button to be activated
    DatabaseReference ref; //database reference to be utilized

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Establishes references to the Firebase database.
     *      5. Identifies all values within the xml layout to be filled with appropriate information.
     *      6. Adds event listeners to validate employee credential input.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_employee_sign_in_screen);

        //see section 3.
        Objects.requireNonNull(getSupportActionBar()).hide();

        //see section 4.
        ref = FirebaseDatabase.getInstance().getReference("employees");

        //see section 5.
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        empSignIn= findViewById(R.id.btn);

        //see section 6.
        empSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //displays dialog loading screen
                final ProgressDialog dialog = new ProgressDialog(EmployeeSignInScreenActivity.this);
                dialog.setMessage("Loading...");
                dialog.show();

                //binds event listener on data change
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(name.getText().toString()).exists()) { //check name exists
                            dialog.dismiss();
                            Employee employee = dataSnapshot.child(name.getText().toString()).getValue(Employee.class);
                            assert employee != null;
                            if (employee.getPassword().equals(password.getText().toString())) { //check password is correct

                                //start new activity
                                Intent intent = new Intent(EmployeeSignInScreenActivity.this, EmployeeOrderStatusScreenActivity.class);
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