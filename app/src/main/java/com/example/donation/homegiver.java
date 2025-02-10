package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homegiver extends AppCompatActivity {
    private ImageView move1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homegiver);

        move1=findViewById(R.id.imageView30);
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( homegiver.this,welcomepage.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView1);

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
    }
}