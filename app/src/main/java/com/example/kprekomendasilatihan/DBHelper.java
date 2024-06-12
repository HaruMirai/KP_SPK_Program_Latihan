package com.example.kprekomendasilatihan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "wpmprogramlatihan.db";

    public DBHelper(Context context) {super(context, "wpmprogramlatihan.db", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table bobot(nomor TEXT primary key, b1 TEXT, b2 TEXT, b3 TEXT, b4 TEXT)");
        sqLiteDatabase.execSQL("create table alternatif(nomoralter TEXT primary key, persentase TEXT, intens TEXT, repetisi TEXT, leanbm TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists bobot");
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists alternatif");
        onCreate(sqLiteDatabase);
    }
    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean insertDataAkun(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result= db.insert("users", null, values);
        if (result == 1)
            return false;
        else
            return true;
    }
    //check username password
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean insertBobotData(String nomor, String b1, String b2, String b3, String b4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nomor", nomor);
        values.put("b1", b1);
        values.put("b2", b2);
        values.put("b3", b3);
        values.put("b4", b4);
        long result = db.insert("bobot", null, values);
        if (result == 1) return false;
        else
            return true;
    }

    public Boolean insertAlternatifData(String nomoralter, String persentase, String intents, String repetisi, String leanbm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nomoralter", nomoralter);
        values.put("persentase", persentase);
        values.put("intens", intents);
        values.put("repetisi", repetisi);
        values.put("leanbm", leanbm);
        long result = db.insert("alternatif", null, values);
        if (result == 1) return false;
        else
            return true;
    }

    public Boolean checknomor(String nomor) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bobot where nomor=?", new String[]{nomor});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checknomoralternatif(String nomoralter) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from alternatif where nomoralter=?", new String[]{nomoralter});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor tampilDataBobot() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bobot", null);
        return cursor;
    }

    public Cursor tampilDataAlternatif() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from alternatif", null);
        return cursor;
    }
    public Cursor ambildatabobot(String nomor){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from bobot where nomor=?", new String[]{nomor});
        return cursor;
    }
    public Cursor ambildataalternatif(String nomoralter){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from alternatif where nomoralter=?", new String[]{nomoralter});
        return cursor;
    }
    public Boolean hapusDataBobot(String nomor) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from bobot where nomor=?", new String[]{nomor});
        if (cursor.getCount() > 0) {
            long result = db.delete("bobot", "nomor=?", new String[]{nomor});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean hapusDataAlternatif(String nomoralter) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from alternatif where nomoralter=?", new String[]{nomoralter});
        if (cursor.getCount() > 0) {
            long result = db.delete("alternatif", "nomoralter=?", new String[]{nomoralter});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}