package com.example.testppe;



import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBHelper_Utilisateur extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste";
    public static final String PROJET_TABLE_NAME = "PSTE";
    public static final String PROJET_COLUMN_ID = "id";
    public static final String PROJET_COLUMN_NAME1 = "Recherche";
    public static final String PROJET_COLUMN_NAME2 = "Date";
    public static final String PROJET_COLUMN_SCORE = "Donnees";



    public DBHelper_Utilisateur(@Nullable Context context) {
       super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Utilisateur" +
                        "(id integer primary key,Nom text,Prenom int,Mail text,Telephone text,Password text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Historique");
        onCreate(db);
    }

    public boolean insertUtilisateur (String nom,String prenom,String phone,String mail,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Nom,Prenom,Mail,Telephone,Password
        contentValues.put("Nom",nom);
        contentValues.put("Prenom",prenom);
        contentValues.put("Mail",mail);
        contentValues.put("Telephone",phone);
        contentValues.put("Password",password);
        db.insert("Utilisateur", null, contentValues);
        return true;
    }



    public String getData(String mail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rendu = "vide";
        Cursor res =  db.rawQuery( "select Password from Utilisateur where Mail = ?", new String[] {mail});


        if (res.moveToFirst()){
            do {
                // Passing values
                rendu = res.getString(1);

            } while(res.moveToNext());
        }
        res.close();
        return rendu;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }

    public boolean updateMatch (int id ,String name1, String name2, String score, String strength,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name1);
        contentValues.put("name2", name2);
        contentValues.put("score", score);
        contentValues.put("strength", strength);
        contentValues.put("date", date);
        db.update("Projet", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteMatch (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Projet",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllMatch() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Historique", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            long yourmilliseconds = res.getLong(res.getColumnIndex(PROJET_COLUMN_NAME2));
            String da = DateFormat.getDateInstance().format(yourmilliseconds);


            array_list.add(res.getString(res.getColumnIndex(PROJET_COLUMN_NAME1))+" : "+da);
            res.moveToNext();
        }
        return array_list;
    }
}
