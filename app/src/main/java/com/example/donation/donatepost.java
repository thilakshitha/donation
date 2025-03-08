package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class donatepost extends AppCompatActivity {

    private Button move2;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donatepost);

        // Set Home as selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_req);

        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

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

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final EditText name = findViewById(R.id.editTextText4);
        final EditText weight= findViewById(R.id.editTextText3);
        final EditText location=findViewById(R.id.editTextText2);
        final EditText gsize=findViewById(R.id.editTextText6);

        final Button post = findViewById(R.id.button1);


       post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    String userID = currentUser.getUid(); // Get logged-in user ID

                    final String nameTxt = name.getText().toString().trim();
                    final String weightTxt = weight.getText().toString().trim();
                    final String locationTxt = location.getText().toString().trim();
                    final String gsizeTxt = gsize.getText().toString().trim();

                    // Create a new request with user ID
                    Map<String, Object> request = new HashMap<>();
                    request.put("userID", userID);
                    request.put("name", nameTxt);
                    request.put("weight", weightTxt);
                    request.put("location", locationTxt);
                    request.put("group", gsizeTxt);

                    // Save to Firestore
                    db.collection("donate post")
                            .add(request)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(donatepost.this, "Request posted successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(donatepost.this, donationDone.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(donatepost.this, "Failed to post request", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(donatepost.this, "User not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
        move2=findViewById(R.id.button3);
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( donatepost.this,homegiver.class);
                startActivity(intent);
            }
        });


    }
}