package com.example.donation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editprofilegiver extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone;
    private Button buttonUpdateProfile;
    private FirebaseFirestore db;
    private String userId;
    private FirebaseAuth auth;


    private Button cancel;
    private Button deleteProfileButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofilegiver);

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (user != null) {
            userId = user.getUid();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextText4);
        editTextEmail = findViewById(R.id.editTextText3);
        editTextPassword = findViewById(R.id.editTextText2);
        editTextPhone = findViewById(R.id.editTextText);

        buttonUpdateProfile = findViewById(R.id.button11);

        cancel = findViewById(R.id.button3);
        cancel.setOnClickListener(v -> {
            Intent intent = new Intent(editprofilegiver.this, homegiver.class);
            startActivity(intent);
        });
        deleteProfileButton = findViewById(R.id.button7);
        deleteProfileButton.setOnClickListener(v -> {
            deleteUserProfile();
        });

        // Load user data into the fields
        loadUserData();

        // Set update button click listener
        buttonUpdateProfile.setOnClickListener(view -> updateUserProfile());
    }

    private void deleteUserProfile() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            // Delete user data from Firestore (Example: Users Collection)
            db.collection("givers").document(userId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Log.d("DeleteProfile", "User data deleted from Firestore");

                        // Now delete user from Firebase Authentication
                        user.delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(editprofilegiver.this, "Profile Deleted", Toast.LENGTH_SHORT).show();

                                        // Redirect to Login Screen
                                        FirebaseAuth.getInstance().signOut();

                                        // Clear SharedPreferences (session)
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.apply();

                                        // Redirect to the login activity after logout
                                        Intent intent = new Intent(editprofilegiver.this, welcomepage.class);
                                        startActivity(intent);
                                        finish();  // Close the current activity
                                    } else {
                                        Toast.makeText(editprofilegiver.this, "Failed to delete user: " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(editprofilegiver.this, "Failed to delete Firestore data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Get email from FirebaseAuth (this is read-only)
            String email = user.getEmail();
            editTextEmail.setText(email);

            // Fetch the user's other details from Firestore
            db.collection("givers").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String password = documentSnapshot.getString("password");
                            String phone = documentSnapshot.getString("mobile no");

                            // Set data to EditText fields
                            editTextName.setText(name);
                            editTextPassword.setText(password);
                            editTextPhone.setText(phone);
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(editprofilegiver.this, "Failed to load data", Toast.LENGTH_SHORT).show());
        }
    }

    private void updateUserProfile() {
        String newName = editTextName.getText().toString().trim();
        String newEmail = editTextEmail.getText().toString().trim();
        String newPassword = editTextPassword.getText().toString().trim();
        String newPhone = editTextPhone.getText().toString().trim();

        if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update email and password if needed
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !newEmail.equals(user.getEmail())) {
            user.updateEmail(newEmail)
                    .addOnSuccessListener(aVoid -> Toast.makeText(editprofilegiver.this, "Email updated", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(editprofilegiver.this, "Failed to update email", Toast.LENGTH_SHORT).show());
        }

        if (user != null && !newPassword.equals("")) {
            user.updatePassword(newPassword)
                    .addOnSuccessListener(aVoid -> Toast.makeText(editprofilegiver.this, "Password updated", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(editprofilegiver.this, "Failed to update password", Toast.LENGTH_SHORT).show());
        }

        // Create a map with updated user data
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", newName);
        updatedData.put("email", newEmail);
        updatedData.put("password", newPassword); // Storing the password in Firestore is not a good practice
        updatedData.put("mobile no", newPhone);

        // Update user data in Firestore
        db.collection("givers").document(userId)
                .update(updatedData)
                .addOnSuccessListener(aVoid -> Toast.makeText(editprofilegiver.this, "Profile updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(editprofilegiver.this, "Failed to update profile", Toast.LENGTH_SHORT).show());
    }
}
