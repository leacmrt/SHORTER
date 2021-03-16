package com.example.testppe.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.R;

public class Register extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button signIn = findViewById(R.id.button5);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              finish();
              //ajout BDD SQL_UTILISATEUR
            }
        });
    }
}
