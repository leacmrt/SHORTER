package com.example.testppe;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

//page d'affichage des informations de SHORTER
public class Informations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);
        Button back = findViewById(R.id.button2);

        back.setOnClickListener(new View.OnClickListener()
        {
            //bouton retour en arrière vers la page d'acceuille
            public void onClick(View v) {
                finish();
            }
        });

    }
}