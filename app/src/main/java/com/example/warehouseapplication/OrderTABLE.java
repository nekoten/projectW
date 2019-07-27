package com.example.warehouseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OrderTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String TABLE_ORDER = "orderTABLE";
    public static final String COLUMN_ID_ORDER = "_id";
    public static final String COLUMN_OFFICER = "Officer";
    //public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_ORDERS = "Orders";
    public static final String COLUMN_ITEM = "Item";

    public OrderTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    } //Constructor

    public long addValueToOrder(String strOfficer, String strOrders, int intItem){

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_OFFICER, strOfficer);
        //objContentValues.put(COLUMN_DATE, strDate);
        objContentValues.put(COLUMN_ORDERS, strOrders);
        objContentValues.put(COLUMN_ITEM, intItem);

        return writeSQLite.insert(TABLE_ORDER, null, objContentValues);
    }
}// Main Class
