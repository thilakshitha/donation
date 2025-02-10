package com.example.donation;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feed);

        // Set Home as selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_feed);

        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }






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