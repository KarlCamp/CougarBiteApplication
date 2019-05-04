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

/**
 * The AboutScreenActivity class is the activity that presents the information
 * about CougarBite, including it's purpose, how to use it, and the version number.
 * This activity may be accessed either from the StartActivityScreen or through
 * the NavigationView toolbar.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class AboutScreenActivity extends AppCompatActivity {
    TextView about, title, version; //text views to be filled
    DatabaseReference ref; //database reference to be utilized

    /**
     * The onCreate method does the following:
     *      1. Utilizes inheritance for the current saved instance of the state.
     *      2. Establishes the xml content view to be utilized from the layout folder.
     *      3. Establishes action bar context.
     *      4. Establishes references to the Firebase database.
     *      5. Identifies all values within the xml layout to be filled with appropriate information.
     *      6. Adds event listeners to update information when there is a change in the data.
     * @param savedInstanceState stores an instance of saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //see section 1.
        super.onCreate(savedInstanceState);

        //see section 2.
        setContentView(R.layout.activity_about_screen);

        //see section 3.
        Objects.requireNonNull(getSupportActionBar()).hide();

        //see section 4.
        ref = FirebaseDatabase.getInstance().getReference("about");

        //see section 5.
        about = findViewById(R.id.abt);
        title = findViewById(R.id.title);
        version = findViewById(R.id.vers);

        //see section 6.
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