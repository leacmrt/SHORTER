package com.example.testppe.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DBHelper_Emballage extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Emballage";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_Emballage(Context context) {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Emballage" +
                        "(id_materiaux integer, id_produit integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if(newVersion>oldVersion)
        {onCreate(db);}

        db.execSQL("DROP TABLE IF EXISTS Emballage");
        onCreate(db);
    }

    public void insertEmballage (int id_util, String re) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id_util);
        contentValues.put("avis",re);
        db.insert("Emballage", null, contentValues);
    }

    public Cursor getData(int idmat,int idprod) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Emballage where id_materiaux="+idmat+" and id_produit="+idprod+" " ,null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }


    public Integer deleteIngredient (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Emballage",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public int getIdmateriaux(String sessionId) {

        int rv = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id_produit=?";
        String[] whereargs = new String[]{String.valueOf(sessionId)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getInt(csr.getColumnIndex("id_materiaux"));
        }
        return rv;
    }

}