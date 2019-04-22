package com.capstone.kcamp.cougarbiteapplication;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
public class AboutScreenActivity extends AppCompatActivity {
    TextView about, title, version;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("about");
        about = (TextView) findViewById(R.id.abt);
        title = (TextView) findViewById(R.id.title);
        version = (TextView) findViewById(R.id.vers);
        reference.child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                title.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutScreenActivity.this, "Error: issue connecting to database.", Toast.LENGTH_SHORT).show();
            }
        });
        reference.child("description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                about.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutScreenActivity.this, "Error: issue connecting to database.", Toast.LENGTH_SHORT).show();
            }
        });
        reference.child("version").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                version.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutScreenActivity.this, "Error: issue connecting to database.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}