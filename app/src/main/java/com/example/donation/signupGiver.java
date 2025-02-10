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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupGiver extends AppCompatActivity {
   private Button move;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_giver);

        final EditText name = findViewById(R.id.editTextText4);
        final EditText email= findViewById(R.id.editTextText3);
        final EditText password=findViewById(R.id.editTextText2);
        final EditText repassword=findViewById(R.id.editTextText);

        final Button registerBtn= findViewById(R.id.button11);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nameTxt = name.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String repasswordTxt = repassword.getText().toString();

                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("name",nameTxt);
                user.put("email",emailTxt );
                user.put("password",passwordTxt );
                user.put("mobile no",repasswordTxt );

// Add a new document with a generated ID
                db.collection("givers")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(signupGiver.this, "successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signupGiver.this, "fail", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        move = findViewById(R.id.button3);
        move.setOnClickListener(v -> {
            Intent intent = new Intent(signupGiver.this, logingiver.class);
            startActivity(intent);
        });


    }
}