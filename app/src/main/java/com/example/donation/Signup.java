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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button move1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

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
               db.collection("users needy")
                       .add(user)
                       .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                           @Override
                           public void onSuccess(DocumentReference documentReference) {
                               Toast.makeText(Signup.this, "successful", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(Signup.this, "fail", Toast.LENGTH_SHORT).show();
                           }
                       });
           }
       });


        move1 = findViewById(R.id.button3);
        move1.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, login.class);
            startActivity(intent);
        });




    }

}

