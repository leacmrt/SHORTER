package com.example.testppe.ui.conseil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.R;
import com.example.testppe.ui.notifications.NotificationsFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fruit  extends AppCompatActivity {

    //date pour avoir la saison
    String saison;
    TextView afichesaison;
    Date currentTime = Calendar.getInstance().getTime();
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    ArrayList<String> listItems=new ArrayList<String>();
    ListView lv;
    ArrayAdapter<String> adapter;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        afichesaison = findViewById(R.id.textView9);
        String moi = currentDate.substring(3,5);
        String jour = currentDate.substring(0,2);
        lv = (ListView) findViewById(R.id.lv_transList);
        back = findViewById(R.id.buttonback);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        saison(moi,jour);
        afichesaison.setText(" Saison : "+saison);
        fruitdesaison();
    }

    private void saison(String moi, String jour) {
        System.out.println("lala  mois :"+moi+" jours : "+jour);
        int mois = Integer.valueOf(moi);
        int jours = Integer.valueOf(jour);
        // printemps fin dimanche 20 juin 2021
        //été => lundi 21 juin au mardi 21 septembre 2021
        //automne => mercredi 22 septembre au lundi 20 décembre 2021
        //hiver => mardi 21 décembre au samedi 19 mars 2022

        if(3<=mois&& mois <=6)//printemps
        {
            if(mois==3&&jours<19)
            {
                saison = "hiver";
            }
            else if(mois==6&&jours>20)
            {
                saison = "été";
            } else saison = "printemps";
        }

        if(6<=mois&& mois <=9)//été
        {
            if(mois==6&&jours<21)
            {
                saison = "printemps";
            }
            else if(mois==9&&jours>21)
            {
                saison = "automne";
            } else saison = "été";
        }

        if(9<=mois&& mois <=12)//automne
        {
            if(mois==9&&jours<22)
            {
                saison = "été";
            }
            else if(mois==12&&jours>20)
            {
                saison = "hiver";
            } else saison = "automne";
        }

        if(12==mois || mois <=3)//hiver
        {
            if(mois==12&&jours<21)
            {
                saison = "automne";
            }
            else if(mois==3&&jours>19)
            {
                saison = "printemps";
            } else saison = "hiver";
        }
    }

    private void fruitdesaison() {

        if(saison.equals("printemps"))
        {
        //pomme poire fraise pamplemousse
            String[] fruits = new String[] {
                    "Pomme",
                    "Poire",
                    "Fraise",
                    "Pamplemousse"
            };

            // Create a List from String Array elements
            listItems = new ArrayList<String>(Arrays.asList(fruits));
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  listItems);
            lv.setAdapter(adapter);
        }
        else  if(saison.equals("été")) {
        //abricot brugnon cerise prune tomate  pastèque melon
            String[] fruits = new String[] {
                    "Abricot",
                    "Brugnon",
                    "Cerise",
                    "Prune",
                    "Tomate",
                    "Pastèque",
                    "Melon"
            };

            // Create a List from String Array elements
            listItems = new ArrayList<String>(Arrays.asList(fruits));
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  listItems);
            lv.setAdapter(adapter);
        }
        else  if(saison.equals("automne"))
        {
        //mure amande sèche pruneaux mirabelle grosseile raison noisette coing
            String[] fruits = new String[] {
                    "Mure",
                    "Amande sèche",
                    "Pruneaux",
                    "Mirabelle",
                    "Groseille",
                    "Raisin",
                    "Noisette",
                    "Coing"
            };

            // Create a List from String Array elements
            listItems = new ArrayList<String>(Arrays.asList(fruits));
            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,  listItems);
            lv.setAdapter(adapter);
        }
        else if (saison.equals("hiver"))
        {
        //orange citron clémentine coing mandarine kaki pomme poire chataigne amande sèche
            String[] fruits = new String[] {
                    "Orange",
                    "Citron",
                    "Clémentine",
                    "Coing",
                    "Mandarine",
                    "Kaki",
                    "Pomme",
                    "Poire",
                    "Chataigne",
                    "Amande",
                    "Sèche"
            };

            // Create a List from String Array elements
            listItems = new ArrayList<String>(Arrays.asList(fruits));
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  listItems);
            lv.setAdapter(adapter);
        }
    }
}
