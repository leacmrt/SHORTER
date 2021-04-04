package com.example.testppe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testppe.BDD.DBHelper_Emballage;
import com.example.testppe.BDD.DBHelper_Materiaux;
import com.example.testppe.BDD.DBHelper_Produit;
import com.example.testppe.BDD.DBHelper_ProduitTransport;
import com.example.testppe.BDD.DBHelper_Transport;
import com.example.testppe.R;
import com.example.testppe.SQL_Produit;

import java.util.ArrayList;

public class SelectItem extends AppCompatActivity {
    ArrayList<String> listtmp;
    ArrayList<Integer> listid;
    int position,id;
    String nom;
    TextView recyclage;
    TextView defaut;
    TextView qualite ;
    TextView alternative;
    private DBHelper_Emballage emdb;
    private DBHelper_Materiaux madb;
    private DBHelper_Produit prodb;
    private DBHelper_ProduitTransport protdb;
    private DBHelper_Transport trandb;

    private SQL_Produit BDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_item);

        //initialisation des bases de données
        emdb = new DBHelper_Emballage(SelectItem.this.getBaseContext());
        madb = new DBHelper_Materiaux(SelectItem.this.getBaseContext());
        prodb = new DBHelper_Produit(SelectItem.this.getBaseContext());
        protdb = new DBHelper_ProduitTransport(SelectItem.this.getBaseContext());
        trandb = new DBHelper_Transport(SelectItem.this.getBaseContext());

        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        nom = getIntent().getStringExtra("numero");
        Toast.makeText(SelectItem.this.getApplicationContext(), sessionId, Toast.LENGTH_LONG).show();

        Button back = findViewById(R.id.buttonback); //initialisation des widget
        TextView Affiche = findViewById(R.id.textView6);
        recyclage = findViewById(R.id.textView17);
        defaut =  findViewById(R.id.textView12);
        qualite =  findViewById(R.id.textView14);
        alternative =  findViewById(R.id.textView18);


        Affiche.setText(nom);

        recuperation_elements(sessionId);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(SelectItem.this.getApplicationContext(), "Back to history", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void recuperation_elements(String sessionId) {


        int produitId = prodb.getid(nom);


        //Traitement emballage
        int emballage = emdb.getIdmateriaux(String.valueOf(produitId));
        System.out.println("id mat"+emballage);
        String emballagefin = madb.getMateriaux(emballage);
        System.out.println(" nom "+emballagefin);

            if(emballagefin.equals("Carton"))
            {
                recyclage.setText("Emballage "+emballagefin+"\n Poubelle Jaune");
            }
            if(emballagefin.equals("Plastique"))
            {
                recyclage.setText("Emballage "+emballagefin+"\n Poubelle Grise");
            }
            if(emballagefin.equals("Verre"))
            {
                recyclage.setText("Emballage "+emballagefin+"\n Poubelle Grise");
            }

            //Traitement transport
        int idproduittransport = protdb.getIdtransport(String.valueOf(produitId));
        String provenance = protdb.getProvenance(String.valueOf(produitId));
        System.out.println("id mat"+emballage);
        String typetransport = trandb.getTransport(idproduittransport);
        System.out.println(" nom "+typetransport);

        if(typetransport.equals("Camion"))
        {
            defaut.setText("Venant de  "+emballagefin+"\n Par : "+provenance);
        }
        if(typetransport.equals("Bateau"))
        {
            defaut.setText("Venant de  "+emballagefin+"\n Par : "+provenance);
        }
        if(typetransport.equals("Avion"))
        {
            defaut.setText("Venant de  "+emballagefin+"\n Par : "+provenance);
        }



    }

}
