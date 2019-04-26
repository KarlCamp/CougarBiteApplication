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
import java.util.Objects;
public class AboutScreenActivity extends AppCompatActivity {
    TextView about, title, version;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ref = FirebaseDatabase.getInstance().getReference("about");
        about = findViewById(R.id.abt);
        title = findViewById(R.id.title);
        version = findViewById(R.id.vers);
        ref.child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                title.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutScreenActivity.this, "Error: Issue connecting to database.", Toast.LENGTH_SHORT).show();
            }
        });
        ref.child("description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                about.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutScreenActivity.this, "Error: Issue connecting to database.", Toast.LENGTH_SHORT).show();
            }
        });
        ref.child("version").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                version.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutScreenActivity.this, "Error: Issue connecting to database.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}