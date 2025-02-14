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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class logingiver extends AppCompatActivity {
    private Button move1;
    private TextView move2;
    private Button loginButton;
    private EditText loginEmail, loginPassword;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logingiver);

        db = FirebaseFirestore.getInstance();
        loginEmail = findViewById(R.id.editTextText2);
        loginPassword = findViewById(R.id.editTextText3);
        loginButton=findViewById(R.id.button3);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String pass = loginPassword.getText().toString().trim();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    authenticateUser(email, pass);
                } else {
                    Toast.makeText(logingiver.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }

            }
        });
        move2=findViewById(R.id.textView11);
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( logingiver.this, signupGiver.class);
                startActivity(intent);
            }
        });


    }
    private void authenticateUser(String email, String password) {
        db.collection("givers")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            // Login successful
                            Toast.makeText(logingiver.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(logingiver.this, homegiver.class); // Change to your next activity
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(logingiver.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(logingiver.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}