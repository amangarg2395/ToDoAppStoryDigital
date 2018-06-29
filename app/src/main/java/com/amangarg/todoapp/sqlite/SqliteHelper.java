package com.amangarg.todoapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.amangarg.todoapp.model.CategoryData;
import com.amangarg.todoapp.model.ToDoData;

/**
 * Created by amangarg on 6/29/18.
 */
public class SqliteHelper extends SQLiteOpenHelper {
    // Constants
    public static final String DatabaseName = "ToDoDb.db";
    public static final String TableName_TD = "ToDoTask";
    public static final String Col_1_TD = "ToDoID";
    public static final String Col_2_TD = "ToDoTaskDetails";
    public static final String Col_3_TD = "ToDoTaskPrority";
    public static final String Col_4_TD = "ToDoTaskStatus";
    public static final String Col_5_TD = "ToDoNotes";

    public static final String TableName_C = "Category";
    public static final String Col_1_C = "CategoryID";
    public static final String Col_2_C = "CategoryDetails";
    public static final String Col_3_C = "CategoryNotes";

    // Create DB to store To Do Tasks
    public SqliteHelper(Context context) {
        super(context, DatabaseName, null, 1);

    }

    // Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createToDoTableQuery = "create table if not exists " + TableName_TD + " ( ToDoID INTEGER PRIMARY KEY AUTOINCREMENT, ToDoTaskDetails TEXT, ToDoTaskPrority TEXT, ToDoTaskStatus TEXT, ToDoNotes TEXT)";
        String createCategoryTableQuery = "create table if not exists " + TableName_C + " ( CategoryID INTEGER PRIMARY KEY AUTOINCREMENT, CategoryDetails TEXT, ToDoNotes TEXT)";
        db.execSQL(createToDoTableQuery);
        db.execSQL(createCategoryTableQuery);
    }

    // Drop Table for new table creation
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName_TD);
        db.execSQL("DROP TABLE IF EXISTS " + TableName_C);
        onCreate(db);
    }

    // Insert into Table
    public boolean insertInto(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(TableName_TD, null, cv);
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Select * from Table i.e get all data
    public Cursor selectAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TableName_TD;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    // Update specific Task
    public Cursor updateTask(ToDoData td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + TableName_TD
                + " SET "
                + Col_2_TD + "='" + td.getToDoTaskDetails()
                + "', "
                + Col_3_TD + "='" + td.getToDoTaskPrority()
                + "', "
                + Col_4_TD + "='" + td.getToDoTaskStatus()
                + "', "
                + Col_5_TD + "='" + td.getToDoNotes()
                + "' WHERE " + Col_1_TD + "='" + td.getToDoID() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    public Cursor updateCategory(CategoryData categoryData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + TableName_TD
                + " SET "
                + Col_2_TD + "='" + categoryData.getCategoryDetails()
                + "', "
                + Col_5_TD + "='" + categoryData.getCategoryNotes()
                + "' WHERE " + Col_1_TD + "='" + categoryData.getCategoryID() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    // Delete specific Task
    public Cursor deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TableName_TD
                + " WHERE "
                + Col_1_TD + "='"
                + id + "'";
        Cursor result = db.rawQuery(query, null);
        return result;
    }
}
