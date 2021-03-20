package com.example.testppe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.testppe.ui.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL_Utilisateur {
    // JDBC driver name and database URL

    ProgressDialog pd;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.2.2:3306/shorter?autoReconnect=true";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";


    public void ajout(Activity a, Context c, EditText Name, EditText Prenom, EditText Pass1, EditText Pass2,EditText Mail, EditText Phone) {

       /* a.runOnUiThread(new Runnable() {
            public void run() {
                pd = new ProgressDialog(c);
                pd.setTitle("Send");
                pd.setMessage("Sending..Please wait");
                pd.show();
            }});*/
        Connection conn = null;

        Statement stmt = null;
        //this.c = c;


        //INPUT EDITTEXTS


        //GET TEXTS FROM EDITEXTS
       String nom= Name.getText().toString();
       String prenom=Prenom.getText().toString();
       String Pass = Pass1.getText().toString();
       String mail =Mail.getText().toString();
       String phone = Phone.getText().toString();


        String SQL = "INSERT INTO Utilisateur(Nom,Prenom,Mail,Telephone,Password ) "
                + "VALUES(?,?,?,?,?)";

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            //STEP 4: Execute a query
            System.out.println("Creating statement...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (

                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, mail);
            pstmt.setString(4, phone);
            pstmt.setString(5, Pass);


            int affectedRows = pstmt.executeUpdate();
            a.runOnUiThread(new Runnable() {
                public void run() {


                  //  pd.dismiss();

                    if(affectedRows!= 0)
                    {
                        //SUCCESS

                        Name.setText("Surname");
                        Prenom.setText("Name");
                        Mail.setText("Mail");
                        Phone.setText("Phone");
                        Pass1.setText("");
                        Pass2.setText("");


                    }else
                    {
                        //NO SUCCESS
                        Toast.makeText(c,"Unsuccessful "+affectedRows,Toast.LENGTH_LONG).show();
                    }
                }
            });

            // check the affected rows

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public String verification(Activity a,String mail,String mdp) throws ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String nametmp = " ";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "SELECT Password FROM Utilisateur WHERE Mail ='"+mail+"';";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                String name = rs.getString("Password");
                nametmp=name;
                return nametmp;

            }



        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
      return "unknown";
    }

    public void ajoutAvis(EditText avis, int util_id) {//ajout avis dans BDD interne
        Connection conn = null;
        Statement stmt = null;

        String SQL = "INSERT INTO Avis_Utilisateur(id_util,Avis ) "
                + "VALUES(?,?)";

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            //STEP 4: Execute a query
            System.out.println("Creating statement...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (

                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, util_id);
            pstmt.setString(2, avis.getText().toString());



            int affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getId(String toString) {
        Connection conn = null;
        Statement stmt = null;
        int nametmp =0;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "SELECT id FROM Utilisateur WHERE Mail ='"+toString+"';";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                int name = rs.getInt("id");
                nametmp=name;
                System.out.println("testid"+name);
                return nametmp;

            }



        }
        catch (SQLException | ClassNotFoundException throwables)
        {
            throwables.printStackTrace();
        }
        return 0;
    }
}


