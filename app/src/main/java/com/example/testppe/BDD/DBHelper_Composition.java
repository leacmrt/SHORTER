package com.example.testppe.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DBHelper_Composition extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Composition";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_Composition(Context context) {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Composition" +
                        "(id_Produit integer,id_Ingredient integer,Quantite integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if(newVersion>oldVersion)
        {onCreate(db);}

        db.execSQL("DROP TABLE IF EXISTS Composition");
        onCreate(db);
    }

    public void insertComposition (int id_util, String re) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id_util);
        contentValues.put("avis",re);
        db.insert("Composition", null, contentValues);
    }

    public Cursor getData(int idP, int idI) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Composition where id_Produit="+idP+" and id_Ingredient="+ idI+" ", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }


    public Integer deleteComposition (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Composition",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public String getName(long id) {

        String rv = "not found";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "quantite=?";
        String[] whereargs = new String[]{String.valueOf(id)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("quantite"));
        }
        return rv;
    }

    public int getIdCompo(String valueOf) {
        int rv = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id_Produit=?";
        String[] whereargs = new String[]{String.valueOf(valueOf)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getInt(csr.getColumnIndex("id_Ingredient"));
        }
        return rv;
    }

    public int getquantite(String valueOf) {
        int rv = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id_Produit=?";
        String[] whereargs = new String[]{String.valueOf(valueOf)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getInt(csr.getColumnIndex("Quantite"));
        }
        return rv;
    }
}