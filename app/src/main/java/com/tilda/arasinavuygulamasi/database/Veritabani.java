package com.tilda.arasinavuygulamasi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tilda.arasinavuygulamasi.model.Film;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class Veritabani extends SQLiteOpenHelper {

    public Veritabani(@Nullable Context context) {
        super(context, "Favoriler.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS filmler(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "imdbID TEXT, " +
                "Title TEXT, " +
                "Type TEXT, " +
                "Year TEXT, " +
                "Poster TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long filmEkleParametrleri(String ad, String id, String tip, String yil, String poster){
        ContentValues veriler = new ContentValues();
        veriler.put("imdbID" , id);
        veriler.put("Title", ad);
        veriler.put("Type", tip);
        veriler.put("Year",yil);
        veriler.put("Poster", poster);

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.insert("filmler", null, veriler);
        return cevap;
    }

    public long filmEkle(Film yeniFilm){
        ContentValues veriler = new ContentValues();
        veriler.put("imdbID" , yeniFilm.getImdbID());
        veriler.put("Title", yeniFilm.getTitle());
        veriler.put("Type", yeniFilm.getType());
        veriler.put("Year", yeniFilm.getYear());
        veriler.put("Poster", yeniFilm.getPoster());

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.insert("filmler", null, veriler);
        return cevap;
    }

    public ArrayList<Film> filmleriListele(){
        ArrayList<Film> filmler = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, imdbID, Title, Type, Year, Poster FROM filmler", null);
        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                String imdbID = c.getString(1);
                String Title = c.getString(2);
                String Type = c.getString(3);
                String Year = c.getString(4);
                String Poster = c.getString(5);
                //System.out.println(id +" - "+ ad +" - " + yil +" - " + tur);
                Film f = new Film(id, null, imdbID, Title, Type, Year, Poster);
                filmler.add(f);
            }while(c.moveToNext());
        }
        c.close();
        return filmler;
    }
}
