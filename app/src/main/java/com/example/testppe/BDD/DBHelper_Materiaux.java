package com.example.testppe.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

//Classe d'accès à la BDD SQLLITE Materiaux
public class DBHelper_Materiaux  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Materiaux";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_Materiaux(Context context)//constructeur
    {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)//creation de la bdd si elle n'est pas créée en interne
    {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Materiaux" + "(id integer, nom text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if(newVersion>oldVersion)
        {onCreate(db);}

        db.execSQL("DROP TABLE IF EXISTS Materiaux");
        onCreate(db);
    }

    public void insertMateriaux (int id_util, String re) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id_util);
        contentValues.put("avis",re);
        db.insert("Materiaux", null, contentValues);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Materiaux where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }


    public Integer deleteMateriaux (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Materiaux",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public String getMateriaux(int emballage)//récupert nom matériau d'un produit en fonction de son ID
    {
        String rv = "unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id=?";
        String[] whereargs = new String[]{String.valueOf(emballage)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("nom"));
        }
        return rv;
    }


}