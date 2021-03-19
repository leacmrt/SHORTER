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

import com.example.testppe.R;
import com.example.testppe.SQL_Produit;

import java.util.ArrayList;

public class SelectItem extends AppCompatActivity {
    ArrayList<String> listtmp;
    ArrayList<Integer> listid;
    int position,id;

    private SQL_Produit BDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_item);
        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        Toast.makeText(SelectItem.this.getApplicationContext(), sessionId, Toast.LENGTH_LONG).show();
        Button back = findViewById(R.id.buttonback);
        TextView Affiche = findViewById(R.id.textView6);
        Affiche.setText(sessionId);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(SelectItem.this.getApplicationContext(), "Back to history", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

}
