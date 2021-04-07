package com.example.testppe.BDD;


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

public class DBHelper<Date> extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db_pste1";
    public static final String PROJET_TABLE_NAME = "Historique";
    public static final String PROJET_COLUMN_ID = "id";
    public static final String PROJET_COLUMN_NAME1 = "Recherche";
    public static final String PROJET_COLUMN_NAME2 = "Date";
    public static final String PROJET_COLUMN_SCORE = "Donnees";
    Context myContext;
    SQLiteDatabase myDataBase;
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 4);
        myContext = context;
        myDataBase = this.getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Historique" +
                        "(id integer primary key,Recherche text,Date int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

            if(newVersion>oldVersion)
                onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS Historique");
        onCreate(db);
    }

    public boolean insertrecherche (String re) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Recherche",re);
        contentValues.put("Date",System.currentTimeMillis());
        db.insert("Historique", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Histoique where id="+id+"", null );
        return res;
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

    public ArrayList<String> getAllMatch2() {

        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select id from Historique", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            array_list.add(res.getString(res.getColumnIndex("id")));
            res.moveToNext();
        }
        return array_list;
    }


    public String getNom(int position) {
        String rv = "unknown";
        SQLiteDatabase db = this.getWritableDatabase();
        String whereclause = "id=?";
        String[] whereargs = new String[]{String.valueOf(position)};
        Cursor csr = db.query(PROJET_TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getString(csr.getColumnIndex("Recherche"));
        }
        return rv;
    }

    public ArrayList<String> getnote() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select Recherche from Historique", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            array_list.add(res.getString(res.getColumnIndex("Recherche")));
            res.moveToNext();
        }
        return array_list;
    }
}