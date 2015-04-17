package com.app.phant0m.agenda;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    // Creamos el constructor
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Se crea la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table agendaa (identificador integer primary key,nombre text,apellidos text,direccion text,numero integer) ");

    }

    // borrar la tabla y crear la nueva tabla
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists agendaa");
        db.execSQL("create table agendaa (identificador integer primary key,nombre text,apellidos text,direccion text,numero integer) ");
    }
}
