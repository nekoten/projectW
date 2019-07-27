package com.example.warehouseapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Warehouse.db";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE = "create table userTABLE (_id integer primary key, " + " User text, Password text, Officer text);";
    private static final String ORDER_TABLE = "create table orderTABLE (_id integer primary key, " + " Officer text, Orders text, Item integer);";
    private static final String FOOD_TABLE = "create table foodTABLE (_id integer primary key, "+" Food text, Price text);";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    } //Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE);
        db.execSQL(ORDER_TABLE);
        db.execSQL(FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}// Main Class
