package com.example.donation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class homegiver extends AppCompatActivity {
    private ImageView move1;
    private ImageView profedit;
    private ImageView messagebtn;
    private ImageView historybtn;
    private SharedPreferences sharedPreferences;
    private FirebaseFirestore db;
    private TextView textViewUserName;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homegiver);

        textViewUserName = findViewById(R.id.textView); // TextView to show the name
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            loadUserName();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }


        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        move1=findViewById(R.id.imageView30);
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();  // Call your logout method here
            }
        });

        move1=findViewById(R.id.imageView30);
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();  // Call your logout method here
            }
        });
        profedit=findViewById(R.id.imageView28);
        profedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( homegiver.this,editprofilegiver.class);
                startActivity(intent);
            }
        });
        messagebtn=findViewById(R.id.imageView27);
        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( homegiver.this,givermessage.class);
                startActivity(intent);
            }
        });
        historybtn=findViewById(R.id.imageView25);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( homegiver.this,historyGiver.class);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView1);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), homegiver.class));
                overridePendingTransition(0, 0);
                return true; // Already on Home
            } else if (item.getItemId() == R.id.nav_feed) {
                startActivity(new Intent(getApplicationContext(), giverfeed.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_req) {
                startActivity(new Intent(getApplicationContext(), donatepost.class));
                overridePendingTransition(0, 0);
                return true;
            }else if (item.getItemId() == R.id.nav_message) {
                startActivity(new Intent(getApplicationContext(), givermessage.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
    public void logoutUser() {
        // Sign out from Firebase Authentication
        FirebaseAuth.getInstance().signOut();

        // Clear SharedPreferences (session)
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to the login activity after logout
        Intent intent = new Intent(homegiver.this, welcomepage.class);
        startActivity(intent);
        finish();  // Close the current activity
    }
    private void loadUserName() {
        String userId = currentUser.getUid();

        db.collection("givers").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        textViewUserName.setText("Welcome, " + name + "!");
                    } else {
                        Toast.makeText(homegiver.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(homegiver.this, "Failed to load user data", Toast.LENGTH_SHORT).show());
    }
}