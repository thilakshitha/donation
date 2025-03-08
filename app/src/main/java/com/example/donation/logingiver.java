package com.example.donation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logingiver extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private TextView move2;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logingiver);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        // Check if user is already logged in
        if (isUserLoggedIn()) {
            redirectToHome();
        }

        loginEmail = findViewById(R.id.editTextText2);
        loginPassword = findViewById(R.id.editTextText3);
        loginButton = findViewById(R.id.button3);

        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                authenticateUser(email, password);
            } else {
                Toast.makeText(logingiver.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        });

        move2 = findViewById(R.id.textView11);
        move2.setOnClickListener(v -> {
            Intent intent = new Intent(logingiver.this, signupGiver.class);
            startActivity(intent);
        });
    }

    private void authenticateUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserSession(user.getUid());  // Save session
                            Toast.makeText(logingiver.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            redirectToHome();
                        }
                    } else {
                        Toast.makeText(logingiver.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserSession(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_ID", userId);
        editor.putBoolean("IS_LOGGED_IN", true);
        editor.apply();
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false);
    }

    private void redirectToHome() {
        Intent intent = new Intent(logingiver.this, homegiver.class);
        startActivity(intent);
        finish();
    }



}
