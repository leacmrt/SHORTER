package com.example.testppe.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

//classe accès BDD SQLITE ingrédient
public class DBHelper_Ingredient extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Ingredient";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_Ingredient(Context context) //constructeur
    {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) //creation si pas encore fait
    {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Ingredient" +"(id integer, nom text, provenance text, eau text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) //upgrade si il le faut
    {
        // TODO Auto-generated method stub

        if(newVersion>oldVersion)
        {onCreate(db);}

        db.execSQL("DROP TABLE IF EXISTS Ingredient");
        onCreate(db);
    }

    public void insertingredient (int id_util, String re) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id_util);
        contentValues.put("avis",re);
        db.insert("Ingredient", null, contentValues);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Ingredient where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJET_TABLE_NAME);
        return numRows;
    }


    public Integer deleteIngredient (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Ingredient",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public String getIngredient(int idproduitcompo) //recupère le nom d'un ingrédient en fonction de son ID
    {
        String rv = "unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id=?";
        String[] whereargs = new String[]{String.valueOf(idproduitcompo)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("nom"));
        }
        return rv;
    }

    public String getpro(int idproduitcompo) //recupère la provenance d'un ingrédient en fonction de son ID
    {
        String rv = "unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id=?";
        String[] whereargs = new String[]{String.valueOf(idproduitcompo)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("provenance"));
        }
        return rv;
    }

    public String geteau(int idproduitcompo)//récupert le nombre de litre d'eau issus de la production d'un produit en fonction de son id
    {
        String rv = "unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id=?";
        String[] whereargs = new String[]{String.valueOf(idproduitcompo)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("eau"));
        }
        return rv;
    }
}