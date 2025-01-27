package com.example.testppe.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

//classe acces BDD SQLLITE Produit Transport
public class DBHelper_ProduitTransport  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "ProduitTransport";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_ProduitTransport (Context context) //constructeur
    {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table ProduitTransport" + "(id_produit integer, id_transport integer,provenance text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if(newVersion>oldVersion)
        {onCreate(db);}

        db.execSQL("DROP TABLE IF EXISTS ProduitTransport");
        onCreate(db);
    }

    public void insertProduitTransport (int id_util, String re) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id_util);
        contentValues.put("avis",re);
        db.insert("ProduitTransport", null, contentValues);
    }

    public Cursor getData(int idP, int idT) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from ProduitTransport where id_produit="+idP+" and id_transport="+idT+" ", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }


    public Integer deleteProduitTransport (Integer idP, Integer idT) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ProduitTransport",
                "id_produit = ? ",
                new String[] { Integer.toString(idP) });
    }

    public int getIdtransport(String valueOf) //recup id transport en fonction de l'id d'un produit
    {
        int rv = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id_produit=?";
        String[] whereargs = new String[]{String.valueOf(valueOf)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getInt(csr.getColumnIndex("id_transport"));
        }
        return rv;
    }

    public String getProvenance(String valueOf)// recup provenance d'un produit en fonction de son ID
    {
        String rv = "Unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id_produit=?";
        String[] whereargs = new String[]{String.valueOf(valueOf)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("provenance"));
        }
        return rv;
    }
}