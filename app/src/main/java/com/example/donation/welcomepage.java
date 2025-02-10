package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcomepage extends AppCompatActivity {
    private ConstraintLayout move1;
    private ConstraintLayout move2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcomepage);

        move1=findViewById(R.id.button1);
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( welcomepage.this,logingiver.class);
                startActivity(intent);
            }
        });
        move2=findViewById(R.id.button2);
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( welcomepage.this,login.class);
                startActivity(intent);
            }
        });


    }


}