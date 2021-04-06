package com.example.testppe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AjoutItem extends AppCompatActivity
{
    Button ajouter;
    TextView code;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
        String barcodeDATA = getIntent().getStringExtra("EXTRA_SESSION_ID");
        ajouter = findViewById(R.id.button);
        code = findViewById(R.id.textCODE);

        code.setText(barcodeDATA);//affichage du code du nouvel objet 

    }
}

