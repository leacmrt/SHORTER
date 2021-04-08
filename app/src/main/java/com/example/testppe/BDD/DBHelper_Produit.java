package com.example.testppe.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Classe accès BDD SQLLITE produit
public class DBHelper_Produit extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Produit";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper_Produit(Context context) //constructeur
    {

        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) //creation de BDD si ce n'est pas encore fait
    {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Produit(id_Produit integer,nom text, marque text,code text,Prix REAL, Note text )"
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

    public int getid(String toString)//recupert l'id d'un produit en fonction de son nom
    {

        int rv = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "nom=?";
        String[] whereargs = new String[]{String.valueOf(toString)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getInt(csr.getColumnIndex("id_Produit"));
        }
        return rv;
    }

    public String getidfromcode(String toString) //recupert l'id d'un prduit en fonction de son code barre
    {

        String rv = "unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "code=?";
        String[] whereargs = new String[]{String.valueOf(toString)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("nom"));
        }
        return rv;
    }



    public ArrayList<String> getAllProduit() //tout les produit de la bdd SHORTER
    {

        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from Produit", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("nom")));
            res.moveToNext();
        }
        return array_list;
    }

    public String getmarque(int produitId)//marque du produit en fonction de l'id
    {
        String rv = "unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id_Produit=?";
        String[] whereargs = new String[]{String.valueOf(produitId)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("marque"));
        }
        return rv;
    }

    public boolean insertProduit2 (String nom,String marque,String code) //insertion d'un produit par un utilisateur si il n'est pas encore présent
    {
        int o  = this.numberOfRows();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Nom,Prenom,Mail,Telephone,Password
        contentValues.put("id_Produit",o+1);
        contentValues.put("Nom",nom);
        contentValues.put("Marque",marque);
        contentValues.put("Code",code);
        db.insert("Produit", null, contentValues);
        return true;
    }

    public ArrayList<String> getProBud(Integer valueOf) //tout les produits en dessous du budget encoyé par l'utilisateur
    {
        ArrayList<String> map = new ArrayList<>();
        map.add("chou");
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "Prix < ?";
        String[] whereargs = new String[]{String.valueOf(valueOf)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        csr.moveToFirst();

        while(csr.isAfterLast() == false)
        { System.out.println("la");
           int id = csr.getInt(csr.getColumnIndex("id_Produit"));
           String nom = csr.getString(csr.getColumnIndex("nom"));
           map.add(nom);
           csr.moveToNext();

        }
        return map;
    }

    public String getNote(String s)//note produit en fonction de son nom
    {
        String rv = "E";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "nom=?";
        String[] whereargs = new String[]{String.valueOf(s)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("Note"));
        }
        return rv;
    }
}

