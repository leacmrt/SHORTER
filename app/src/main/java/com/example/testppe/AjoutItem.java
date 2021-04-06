package com.example.testppe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AjoutItem extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
        String barcodeDATA = getIntent().getStringExtra("EXTRA_SESSION_ID");
    }
}

