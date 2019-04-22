package com.capstone.kcamp.cougarbiteapplication;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Objects;
public class StartScreenActivity extends AppCompatActivity {
    Button btnCust, btnEmp, btnAbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();//hide toolbar
        btnCust = (Button)findViewById(R.id.btnCust);
        btnEmp = (Button)findViewById(R.id.btnEmp);
        btnAbt = (Button)findViewById(R.id.btnAbt);
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
    }
}