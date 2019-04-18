package com.capstone.kcamp.cougarbiteapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView about, title, appVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();
        about = (TextView) findViewById(R.id.about);
        title = (TextView) findViewById(R.id.title);
        appVersion = (TextView) findViewById(R.id.version);

    }
}

