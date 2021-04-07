package com.example.testppe.ui.conseil;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.BDD.DBHelper_Produit;
import com.example.testppe.BDD.DBHelper_Utilisateur;
import com.example.testppe.R;
import com.example.testppe.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Budget  extends AppCompatActivity {
    Button Ajouter;
    Button back;
    DBHelper_Utilisateur dbutil;
    DBHelper_Produit  dbpro;
    TextView budget;
    int idutil = 1;
    ArrayList<String> map = new ArrayList<>();
    ListView lv;
    ArrayAdapter<String> adapter;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        Ajouter = findViewById(R.id.button12);
        dbutil = new DBHelper_Utilisateur(Budget.this.getApplicationContext());
        dbpro = new DBHelper_Produit(Budget.this.getApplicationContext());
        lv = (ListView) findViewById(R.id.list);

        back = findViewById(R.id.buttonback);
        budget = findViewById(R.id.textView39);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(budget.getText().toString()!="")
                {
                System.out.println("Ajouter");
                dbutil.ajouterbudget(idutil,Integer.valueOf(budget.getText().toString()));
                    map= dbpro.getProBud(Integer.valueOf(budget.getText().toString()));
                    adapter=new ArrayAdapter<String>(Budget.this.getApplicationContext(), android.R.layout.simple_list_item_1, map);
                    lv.setAdapter(adapter);
                }
                else
                    {
                    Toast.makeText(Budget.this.getApplicationContext(), "Veuillez rentrer un budget", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
