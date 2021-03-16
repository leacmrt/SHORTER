package com.example.testppe.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.R;
import com.example.testppe.SQL_Utilisateur;

public class Register extends AppCompatActivity {

    SQL_Utilisateur BDD = null;
    EditText nom,prenom,mail,telephone,pass1,pass2= null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button signIn = findViewById(R.id.button5);
        nom=findViewById(R.id.editTextTextPersonName3);
        prenom=findViewById(R.id.editTextTextPersonName4);
        mail=findViewById(R.id.editTextTextEmailAddress2);
        telephone=findViewById(R.id.editTextPhone2);
        pass1=findViewById(R.id.editTextTextPassword);
        pass2=findViewById(R.id.editTextTextPassword2);

        BDD = new SQL_Utilisateur();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              finish();
              //ajout BDD SQL_UTILISATEUR
                BDD.ajout(Register.this,Register.this.getApplicationContext(),nom,prenom,pass1,pass2,mail,telephone);
            }
        });
    }
}
