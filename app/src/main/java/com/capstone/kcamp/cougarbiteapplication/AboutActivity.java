package com.capstone.kcamp.cougarbiteapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends AppCompatActivity {

    TextView about, title, appVersion;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("about");
        about = (TextView) findViewById(R.id.about);
        title = (TextView) findViewById(R.id.title);
        appVersion = (TextView) findViewById(R.id.version);

        reference.child("text").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                about.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference.child("appName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appName = dataSnapshot.getValue(String.class);
                title.setText(appName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference.child("version").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                appVersion.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

