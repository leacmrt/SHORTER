package com.example.testppe;

import android.app.Activity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL_Produit {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.2.2:3306/shorter?autoReconnect=true";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";


    public ArrayList<String> getAllItems() throws ClassNotFoundException  //liste de tout les noms des produits dans la BDD shorter
    {
        Connection conn = null;
        Statement stmt = null;
        ArrayList <String> item = new ArrayList<>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "SELECT Nom FROM Produit;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                String name = rs.getString("Nom");
                item.add(name);
            }



        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return item;
    }

    public ArrayList<Integer> getAllID() throws ClassNotFoundException { //liste de tout les ID des produits
        Connection conn = null;
        Statement stmt = null;
        ArrayList <Integer> item = new ArrayList<>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "SELECT id FROM Produit;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                int id= rs.getInt("id");
                item.add(id);
            }

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return item;
    }
}
