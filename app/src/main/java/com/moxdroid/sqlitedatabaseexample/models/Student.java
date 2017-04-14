package com.moxdroid.sqlitedatabaseexample.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.moxdroid.sqlitedatabaseexample.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by moxdroid on 2017-04-13.
 */

public class Student {

    private int     studentId;
    private String  studentName;
    private double  totalMarks;
    private static ArrayList<Student>studentArrayList;

    public static String STUDENT_TABLE = "tblStudent";
    public static String KEY_STUDENT_ID = "id";
    public static String KEY_STUDENT_NAME = "studentName";
    public static String KEY_TOTAL_MARKS = "totalMarks";


    private DBHelper dbHelper;

    public Student() {

    }

    public Student(Context context) {
        dbHelper = new DBHelper(context);
    }

    public Student(int studentId, String studentName, double totalMarks) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.totalMarks = totalMarks;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public static ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }

    public static void initDefaultData()
    {
        studentArrayList = new ArrayList<>();
        studentArrayList.add(new Student(1,"Pritesh Patel",230.50));
        studentArrayList.add(new Student(2,"Satik Roy",430.00));
        studentArrayList.add(new Student(3,"Jaskirat",260.50));
        studentArrayList.add(new Student(4,"Ullah Sami",230.50));
        studentArrayList.add(new Student(5,"Test",130.50));
        studentArrayList.add(new Student(6,"Sultan Mirza",330.00));
        studentArrayList.add(new Student(7,"Lisa Roy",530.50));
        studentArrayList.add(new Student(8,"Britney Patel",280.00));
        studentArrayList.add(new Student(9,"Brand Pepsi",260.50));
        studentArrayList.add(new Student(10,"Rakesh Munna",220.00));

    }


    public int insertStudent(Student student)
    {
        SQLiteDatabase mSQLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_STUDENT_NAME,student.getStudentName());
        cv.put(KEY_TOTAL_MARKS,student.getTotalMarks());

        double sid = mSQLiteDatabase.insert(STUDENT_TABLE,null,cv);
        mSQLiteDatabase.close();
        return (int)sid;
    }

    public void updateStudent(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_STUDENT_NAME, student.getStudentName());
        values.put(Student.KEY_TOTAL_MARKS,student.getTotalMarks());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(STUDENT_TABLE, values, KEY_STUDENT_ID + "= ?", new String[] { String.valueOf(student.getStudentId()) });
        db.close(); // Closing database connection
        //? will prevent SQL Injection
        //"WHERE sid = " + student.studentId + " and snm = " +
    }

    public void deleteStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(STUDENT_TABLE, KEY_STUDENT_ID + "= ?", new String[] { String.valueOf(student.getStudentId()) });
        db.close(); // Closing database connection
    }


    //Get single Student object by student id
    public Student getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_STUDENT_ID + "," +
                Student.KEY_STUDENT_NAME + "," +
                Student.KEY_TOTAL_MARKS  +
                " FROM " + Student.STUDENT_TABLE
                + " WHERE " +
                Student.KEY_STUDENT_ID + "=?";


        int iCount =0;
        Student student=null;

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student = new Student();
                student.setStudentId(cursor.getInt(cursor.getColumnIndex(Student.KEY_STUDENT_ID)));
                student.setStudentName(cursor.getString(cursor.getColumnIndex(Student.KEY_STUDENT_NAME)));
                student.setTotalMarks(cursor.getInt(cursor.getColumnIndex(Student.KEY_TOTAL_MARKS)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }

    //Returns all students from table
    public ArrayList<HashMap<String, String>>  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_STUDENT_ID + "," +
                Student.KEY_STUDENT_NAME + "," +
                Student.KEY_TOTAL_MARKS +
                " FROM " + Student.STUDENT_TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put(KEY_STUDENT_ID, cursor.getString(cursor.getColumnIndex(Student.KEY_STUDENT_ID)));
                student.put(KEY_STUDENT_NAME, cursor.getString(cursor.getColumnIndex(Student.KEY_STUDENT_NAME)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    //Returns all students objects array list
    public ArrayList<Student> getStudentArrayListFromDB() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_STUDENT_ID + "," +
                Student.KEY_STUDENT_NAME + "," +
                Student.KEY_TOTAL_MARKS +
                " FROM " + Student.STUDENT_TABLE;

        //Student student = new Student();
        ArrayList<Student> studentList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setStudentId(cursor.getInt(cursor.getColumnIndex(Student.KEY_STUDENT_ID)));
                student.setStudentName(cursor.getString(cursor.getColumnIndex(Student.KEY_STUDENT_NAME)));
                student.setTotalMarks(cursor.getColumnIndex(Student.KEY_TOTAL_MARKS));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }
}
