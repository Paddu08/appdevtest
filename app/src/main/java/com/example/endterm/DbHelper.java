package com.example.endterm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
     static final String DATABASE_NAME = "identity.db";
     static final int DATABASE_VERSION = 1;
     static final String TABLE_NAME = "identity";
     static final String COLUMN_ID = "id";
     static final String COLUMN_NAME = "name";
     static final String COLUMN_PHONE = "phone";
     static final String COLUMN_ID_NUMBER = "id_number";
     static final String COLUMN_ID_TYPE = "id_type";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PHONE + " TEXT," +
                COLUMN_ID_NUMBER + " TEXT," +
                COLUMN_ID_TYPE + " TEXT)";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertIdentity(String name, String phone, String idNumber, String idType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_ID_NUMBER, idNumber);
        values.put(COLUMN_ID_TYPE, idType);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public Cursor getAllIdentities() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + ", " +
                COLUMN_NAME + ", " +
                COLUMN_PHONE + ", " +
                COLUMN_ID_NUMBER + ", " +
                COLUMN_ID_TYPE +
                " FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getDLIdentities() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + ", " +
                COLUMN_NAME + ", " +
                COLUMN_PHONE + ", " +
                COLUMN_ID_NUMBER + ", " +
                COLUMN_ID_TYPE +
                " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_TYPE + " = 'Driving License'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public int getTotalUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public Cursor getIdentityByAadhar(String aadhar) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_NAME + ", " + COLUMN_PHONE +
                " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_NUMBER + " = '" + aadhar + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
}