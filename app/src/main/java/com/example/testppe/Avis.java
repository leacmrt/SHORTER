package com.example.testppe;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.ui.dashboard.DashboardViewModel;

public class Avis extends AppCompatActivity {

    private Button add =null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_avis);
            Button back = findViewById(R.id.button3);

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(Avis.this.getApplicationContext(), "Avis ajouté", Toast.LENGTH_LONG).show();
                }
            });

            Button back1 = findViewById(R.id.button4);

            back1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   // Toast.makeText(Avis.this.getApplicationContext(), "Avis ajouté", Toast.LENGTH_LONG).show();
                finish();
                }
            });


    }
    }
