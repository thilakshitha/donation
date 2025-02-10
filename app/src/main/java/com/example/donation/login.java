package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    private TextView move2;
    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        loginEmail = findViewById(R.id.editTextText1);
        loginPassword = findViewById(R.id.editTextText2);
        loginButton = findViewById(R.id.button3);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String pass = loginPassword.getText().toString().trim();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    authenticateUser(email, pass);
                } else {
                    Toast.makeText(login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        move2 = findViewById(R.id.textView11);
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    private void authenticateUser(String email, String password) {
        db.collection("users needy")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            // Login successful
                            Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, start.class); // Change to your next activity
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
