package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupGiver extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button move1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_giver);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        final EditText name = findViewById(R.id.editTextText4);
        final EditText email = findViewById(R.id.editTextText3);
        final EditText password = findViewById(R.id.editTextText2);
        final EditText mobileno = findViewById(R.id.editTextText);

        final Button registerBtn = findViewById(R.id.button11);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameTxt = name.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String mobilenoTxt = mobileno.getText().toString();

                // Validate input
                if (TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(nameTxt) || TextUtils.isEmpty(mobilenoTxt)) {
                    Toast.makeText(signupGiver.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new user in Firebase Authentication
                mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                        .addOnCompleteListener(signupGiver.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign up successful, now get the user and add additional info to Firestore
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Create a map to store additional user details in Firestore
                                        Map<String, Object> userDetails = new HashMap<>();
                                        userDetails.put("name", nameTxt);
                                        userDetails.put("email", emailTxt);
                                        userDetails.put("mobile no", mobilenoTxt);

                                        // Add user data to Firestore under the "users" collection using the user's UID
                                        db.collection("givers")
                                                .document(user.getUid()) // Use the Firebase Authentication UID to store user details
                                                .set(userDetails)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Data successfully saved to Firestore
                                                        Toast.makeText(signupGiver.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(signupGiver.this, logingiver.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Error occurred while saving data to Firestore
                                                        Toast.makeText(signupGiver.this, "Failed to save user details", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } else {
                                    // Handle sign-up failure
                                    Toast.makeText(signupGiver.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        move1 = findViewById(R.id.button3);
        move1.setOnClickListener(v -> {
            Intent intent = new Intent(signupGiver.this, logingiver.class);
            startActivity(intent);
        });
    }
}
