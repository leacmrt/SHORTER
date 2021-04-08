package com.example.testppe;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.BDD.DBHelper_Avis;
import com.example.testppe.ui.dashboard.DashboardViewModel;

//classe d'ajout avis utilisateurs de l'application
public class Avis extends AppCompatActivity {

    private Button add =null;
    private EditText avis =null;
    private DBHelper_Avis help= null;
    private SQL_Utilisateur BDD = null;
    private int util_id=0;
    public Avis(){


    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_avis);
            Button back = findViewById(R.id.button3);
            avis = findViewById(R.id.editText);
            BDD = new SQL_Utilisateur();
            help = new DBHelper_Avis(Avis.this);
            Bundle extras = getIntent().getExtras();

            if (extras != null)
            {
                util_id= getIntent().getIntExtra("EXTRA_SESSION_ID",0);//recup de l'id de l'utilisateur
            }
            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                   /* Thread background = new Thread(new Runnable() {

                        public void run() {

                            BDD.ajoutAvis(avis,util_id);}});
                    background.start();*/
                    help.insertavis(util_id,avis.getText().toString()); //insertion dans la BDD
                    Toast.makeText(Avis.this.getApplicationContext(), "Avis ajouté", Toast.LENGTH_LONG).show();
                    avis.setText("");//remise à 0
                }
            });

            Button back1 = findViewById(R.id.button4);

            back1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                finish(); //retour en arrière
                }
            });


    }
    }
