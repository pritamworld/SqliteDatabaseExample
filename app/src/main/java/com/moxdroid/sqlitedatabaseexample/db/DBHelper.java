package com.moxdroid.sqlitedatabaseexample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moxdroid.sqlitedatabaseexample.models.Student;

/**
 * Created by moxdroid on 2017-04-13.
 */

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "college.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Student.STUDENT_TABLE  + "("
                + Student.KEY_STUDENT_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Student.KEY_STUDENT_NAME + " TEXT, "
                + Student.KEY_TOTAL_MARKS + " INTEGER )";
        //CREATE TABLE tblStuDent(id INTEGER PRIMARY KEY, studentName TEXT,
        //totalMarks INTEGER);

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Student.STUDENT_TABLE);

        // Create tables again
        onCreate(db);

    }
}
