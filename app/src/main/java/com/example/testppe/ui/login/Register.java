package com.example.testppe.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.BDD.DBHelper_Utilisateur;
import com.example.testppe.MainActivity;
import com.example.testppe.R;
import com.example.testppe.SQL_Utilisateur;

public class Register extends AppCompatActivity {

    SQL_Utilisateur BDD = null;
    EditText nom,prenom,mail,telephone,pass1,pass2= null;
    DBHelper_Utilisateur help;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        help = new DBHelper_Utilisateur(Register.this.getBaseContext());

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

                //ajout BDD SQL_UTILISATEUR
               /* Thread background = new Thread(new Runnable() {

                    public void run() {*/

                        if((pass1.getText().toString()).equals(pass2.getText().toString()))
                        {
                           /* Register.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Register.this, "SAME PASSWORD ", Toast.LENGTH_LONG).show();
                            }});*/
                             Toast.makeText(Register.this, "SAME PASSWORD ", Toast.LENGTH_LONG).show();
                                help.insertUtilisateur(nom.getText().toString(),prenom.getText().toString(),mail.getText().toString(),telephone.getText().toString(),pass1.getText().toString());


                          /*  BDD.ajout(Register.this,Register.this.getApplicationContext(),nom,prenom,pass1,pass2,mail,telephone);
                       */}else  {
                          /*  Register.this.runOnUiThread(new Runnable() {
                                public void run() {*/Toast.makeText(Register.this, " NOT THE SAME PASSWORD",Toast.LENGTH_LONG).show();
                             //   }});
                        }
           // }}
          //  );
               // background.start();
                finish();
            }});

      };
    }
