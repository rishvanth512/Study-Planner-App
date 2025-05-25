package com.example.studyplanapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "studyplanner.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Column Names
    public static final String TABLE_LECTURES = "lectures";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String TABLE_ASSIGNMENTS = "assignments";
    public static final String TABLE_EXAMS = "exams";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Lectures Table
        String CREATE_LECTURES_TABLE = "CREATE TABLE " + TABLE_LECTURES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(CREATE_LECTURES_TABLE);

        // Create Assignments Table
        String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_ASSIGNMENTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(CREATE_ASSIGNMENTS_TABLE);

        // Create Exams Table
        String CREATE_EXAMS_TABLE = "CREATE TABLE " + TABLE_EXAMS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(CREATE_EXAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAMS);
        onCreate(db);
    }

    // Insert Data into Tables
    public void addRecord(String table, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        db.insert(table, null, values);
        db.close();
    }

    // Retrieve All Data from a Table
    public Cursor getAllRecords(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + table, null);
    }

    // Delete a Record
    public void deleteRecord(String table, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Update a Record
    public void updateRecord(String table, int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        db.update(table, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
