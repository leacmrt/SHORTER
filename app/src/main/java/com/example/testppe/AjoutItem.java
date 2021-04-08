package com.example.testppe;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.BDD.DBHelper_Produit;

import java.util.ArrayList;
import java.util.List;

//classe ajout d'un item dans la base de donnée
public class AjoutItem extends AppCompatActivity
{
    Button ajouter;
    TextView code;
    TextView nom;
    TextView marque;
    String transport = " Unknown";
    String provonance = "Unknown";
    String Nom="";
    String Marque=" ";
    String barcodeDATA;
    DBHelper_Produit prodb;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
        barcodeDATA = getIntent().getStringExtra("EXTRA_SESSION_ID");
        ajouter = findViewById(R.id.button);
        code = findViewById(R.id.textCODE);
        nom = findViewById(R.id.textView42);
        marque=findViewById(R.id.textView43);
        code.setText(barcodeDATA);//affichage du code du nouvel objet
        prodb = new DBHelper_Produit(AjoutItem.this.getBaseContext());

        //liste de pays limité pour la provenance des produits
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Unknown");
        spinnerArray.add("France");
        spinnerArray.add("Espagne");
        spinnerArray.add("Etat-Unis");
        spinnerArray.add("Maroc");
        spinnerArray.add("Italie");
        spinnerArray.add("Allemagne");
        spinnerArray.add("Russie");
        spinnerArray.add("Guadeloupe");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        //liste des choix de mode de transport
        List<String> spinnerArray2 =  new ArrayList<String>();
        spinnerArray2.add("Unknown");
        spinnerArray2.add("Train");
        spinnerArray2.add("Camion");
        spinnerArray2.add("Avion");
        spinnerArray2.add("Bateau");



        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner2);
        sItems2.setAdapter(adapter2);

        provonance=sItems.toString();
        transport=sItems2.toString();

        ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if(!marque.getText().toString().equals("")&&!nom.getText().toString().equals(""))//si les données ont bien été rentrées
               {
                   Marque=marque.getText().toString();
                   Nom = nom.getText().toString();

                   //insertion d'un nouveau produit
                   prodb.insertProduit2(Nom,Marque,barcodeDATA);
                   finish();

                   
               }
            }
        });



    }
}

