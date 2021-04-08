package com.example.testppe.BDD;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

//classe acces SQLLITE bdd AVIS utilisateur
public class DBHelper_Avis<Date> extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Avis";
    public static final String PROJET_COLUMN_ID = "id";
    public static final String PROJET_COLUMN_NAME1 = "Recherche";
    public static final String PROJET_COLUMN_NAME2 = "Date";
    public static final String PROJET_COLUMN_SCORE = "Donnees";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_Avis(Context context) {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //creation si elle n'existe pas
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Avis" +"(id integer,avis text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)//upgrade si il le faut
    {
        // TODO Auto-generated method stub

        if(newVersion>oldVersion)
        {onCreate(db);}

        db.execSQL("DROP TABLE IF EXISTS Avis");
        onCreate(db);
    }

    public void insertavis (int id_util, String re) //insertion d'un avis dans la base de données
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id_util);
        contentValues.put("avis",re);
        db.insert("Avis", null, contentValues);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Avis where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }


    public Integer deleteMatch (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Projet",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
}