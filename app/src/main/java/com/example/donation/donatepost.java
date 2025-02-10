package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class donatepost extends AppCompatActivity {
    private Button move1;
    private Button move2;

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
        move1=findViewById(R.id.button1);
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( donatepost.this,donationDone.class);
                startActivity(intent);
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