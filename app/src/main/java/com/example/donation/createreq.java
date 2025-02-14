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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createreq extends AppCompatActivity {

    private Button move2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Home as selected
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createreq);

        final EditText name = findViewById(R.id.editTextText4);
        final EditText weight= findViewById(R.id.editTextText3);
        final EditText location=findViewById(R.id.editTextText2);
        final EditText gsize=findViewById(R.id.editTextText5);

        final Button post = findViewById(R.id.button1);



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nameTxt = name.getText().toString();
                final String weightTxt = weight.getText().toString();
                final String locationTxt = location.getText().toString();
                final String gsizeTxt = gsize.getText().toString();

                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("name",nameTxt);
                user.put("weight",weightTxt );
                user.put("location",locationTxt );
                user.put("group",gsizeTxt );

// Add a new document with a generated ID
                db.collection("request post")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(createreq.this, "successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(createreq.this, requestdone.class); // Change to your next activity
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(createreq.this, "fail", Toast.LENGTH_SHORT).show();
                            }
                        });


                Intent intent=new Intent( createreq.this,requestdone.class);
                startActivity(intent);
            }
        });
        move2=findViewById(R.id.button3);
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( createreq.this,start.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_req);



        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), start.class));
                overridePendingTransition(0, 0);
                return true; // Already on Home
            } else if (item.getItemId() == R.id.nav_feed) {
                startActivity(new Intent(getApplicationContext(), feed.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_req) {
                startActivity(new Intent(getApplicationContext(), createreq.class));
                overridePendingTransition(0, 0);
                return true;
            }else if (item.getItemId() == R.id.nav_message) {
                startActivity(new Intent(getApplicationContext(), message.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });


    }
}