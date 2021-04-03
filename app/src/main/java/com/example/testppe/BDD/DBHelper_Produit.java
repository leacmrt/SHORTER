package com.example.testppe.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.HashMap;

public class DBHelper_Produit extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Produit";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_Produit(Context context) {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Produit(id_Produit integer,nom text, marque text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if(newVersion>oldVersion)
        {onCreate(db);}

        db.execSQL("DROP TABLE IF EXISTS Produit");
        onCreate(db);
    }

    public void insertProduit (int id_util, String re) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id_util);
        contentValues.put("avis",re);
        db.insert("Avis", null, contentValues);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Produit where id_Produit="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }


    public Integer deleteProduit (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Produit",
                "id_Produit = ? ",
                new String[] { Integer.toString(id) });
    }

    public int getid(String toString) {

            int id =0;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res =  db.rawQuery( "select id_Produit from Produit", null );
            res.moveToFirst();

            while(res.isAfterLast() == false){

              id = Integer.parseInt(res.getString(res.getColumnIndex("id")));
              res.moveToNext();
            }
            return id;

    }
}