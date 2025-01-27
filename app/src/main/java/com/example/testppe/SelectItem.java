package com.example.testppe;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testppe.BDD.DBHelper;
import com.example.testppe.BDD.DBHelper_Composition;
import com.example.testppe.BDD.DBHelper_Emballage;
import com.example.testppe.BDD.DBHelper_Ingredient;
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
    TextView marque;
    TextView eau;
    TextView origine;
    ImageView note ;
    //création des BDD
    private DBHelper_Emballage emdb;
    private DBHelper_Materiaux madb;
    private DBHelper_Produit prodb;
    private DBHelper_ProduitTransport protdb;
    private DBHelper_Transport trandb;
    private DBHelper_Composition codb;
    private DBHelper_Ingredient inDB;
    private DBHelper HiDB;

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
        codb = new DBHelper_Composition(SelectItem.this.getBaseContext());
        inDB = new DBHelper_Ingredient(SelectItem.this.getBaseContext());
        HiDB = new DBHelper(SelectItem.this.getBaseContext());
        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        nom = getIntent().getStringExtra("numero");

        Button supprimer = findViewById(R.id.buttonsup);
        Button back = findViewById(R.id.buttonback); //initialisation des widget
        TextView Affiche = findViewById(R.id.textView6);
        recyclage = findViewById(R.id.textView17);
        defaut =  findViewById(R.id.textView12);
        qualite =  findViewById(R.id.textView14);
        alternative =  findViewById(R.id.textView18);

        marque = findViewById(R.id.textViewmarque);
        eau = findViewById(R.id.textVieweau);
        origine = findViewById(R.id.textVieworigine);
        note = findViewById(R.id.imageView10);
        Affiche.setText(nom);

        recuperation_elements(sessionId);
        back.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) { //retour en arrière
                Toast.makeText(SelectItem.this.getApplicationContext(), "Back to history", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) { //retour en arrière
                HiDB.deleteProduitN(nom);
            }
        });

    }

    private void recuperation_elements(String sessionId) {


        int produitId = prodb.getid(nom);
        String note1 = prodb.getNote(nom);

        if(note1.equals("A"))
        {
            //Drawable image = SelectItem.this.ImageOperations(SelectItem.this.getApplicationContext(),"a.png");
            ImageView imgView = new ImageView(SelectItem.this.getApplicationContext());
            note.setImageResource(R.drawable.a);
          //  note.setImageDrawable(image);
        }
        if(note1.equals("B"))
        {
            note.setImageResource(R.drawable.b);
        }
        if(note1.equals("C"))
        {
            note.setImageResource(R.drawable.c);
        }
        if(note1.equals("D"))
        {
          //  note.setImageBitmap("a.pgn");
            note.setImageResource(R.drawable.d);
        }
        if(note1.equals("E"))
        {
            //note.setImageBitmap("a.pgn");
            note.setImageResource(R.drawable.e);
        }

        //traitement marque
        String marqu = prodb.getmarque(produitId);
        marque.setText("Marque :" + marqu);

        //Traitement emballage
        int emballage = emdb.getIdmateriaux(String.valueOf(produitId));
        System.out.println("id mat"+emballage);
        String emballagefin = madb.getMateriaux(emballage);
        System.out.println(" nom "+emballagefin);

        //afficahge en fonction du type d'emballage
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
            if(emballagefin.equals("Metal"))
            {
                recyclage.setText("Emballage "+emballagefin+"\n Poubelle Jaune");

            }
            if(emballagefin.equals("Papier"))
            {
                recyclage.setText("Emballage "+emballagefin+"\n Poubelle Jaune");
            }



            //Traitement transport
        int idproduittransport = protdb.getIdtransport(String.valueOf(produitId));
        String provenance = protdb.getProvenance(String.valueOf(produitId));
        String typetransport = trandb.getTransport(idproduittransport);
        origine.setText("De: "+provenance);

            if(typetransport.equals("Camion"))
            {
                defaut.setText("Venant de  "+provenance+"\n Par : "+typetransport);
            }
            if(typetransport.equals("Bateau"))
            {
                defaut.setText("Venant de  "+provenance+"\n Par : "+typetransport);
            }
            if(typetransport.equals("Avion"))
            {
                defaut.setText("Venant de  "+provenance+"\n Par : "+typetransport);
            }

            //affichage des alternatives en fonction de la provenance
              if(!provenance.equals("France"))
              {
                  alternative.setText("Achetez chez un producteur local ");
              }

             if(provenance.equals("France"))
             {
                qualite.setText("Produit près de chez vous");
             }

      //Traitement composition
        int idproduitcompo = codb.getIdCompo(String.valueOf(produitId));
        int quantité = codb.getquantite(String.valueOf(produitId));
        String ingredient = inDB.getIngredient(idproduitcompo);
        String provenanceIn= inDB.getpro(idproduitcompo);
        String ea = inDB.geteau(idproduitcompo);
        if(ea.equals("unknown")) ea="";
        eau.setText("Consomme: "+ea+" \n L d'eau");


    }

}
