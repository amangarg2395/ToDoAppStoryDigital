package com.amangarg.todoapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.amangarg.todoapp.model.CategoryData;
import com.amangarg.todoapp.model.TaskData;

/**
 * Created by amangarg on 6/29/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "toDoDatabase";

    public static final int DATABASE_VERSION = 1;

    public static final String CATEGORY_TABLE = "Category";
    public static final String CATEGORY_UNIQUE_ID = "CategoryUniqueId";
    public static final String CATEGORY_TITLE = "CategoryTitle";
//    public static final String Col_3_TD = "ToDoTaskPrority";
//    public static final String Col_4_TD = "ToDoTaskStatus";
//    public static final String Col_5_TD = "ToDoNotes";

    public static final String TASK_TABLE = "Task";
    public static final String TASK_UNIQUE_ID = "TaskUniqueId";
    public static final String TASK_TITLE = "TaskTitle";
    public static final String TASK_DESCRIPTION = "TaskDescription";
    public static final String TASK_IMAGE = "TaskImage";
    public static final String TASK_STATUS = "TaskStatus";
    public static final String CATAGORY_UNIQUE_ID_FK = "CategoryUniqueId";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CATEGORY_TABLE = "CREATE TABLE "
                + CATEGORY_TABLE + "(" + CATEGORY_UNIQUE_ID + " INTEGER PRIMARY KEY,"
                + CATEGORY_TITLE + " TEXT," + ")";

        String CREATE_TASK_TABLE = "CREATE TABLE "
                + TASK_TABLE + "(" + TASK_UNIQUE_ID + " INTEGER PRIMARY KEY,"
                + TASK_TITLE + " TEXT," + TASK_DESCRIPTION + " TEXT,"
                + TASK_IMAGE + " TEXT," + TASK_STATUS + " INTEGER,"
                + CATAGORY_UNIQUE_ID_FK + " INTEGER" + ")";

        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }

    public boolean insertIntoCategory(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(CATEGORY_TABLE, null, cv);
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertIntoTask(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(TASK_TABLE, null, cv);
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }


    // Select * from Table i.e get all data
    public Cursor selectAllDataFromCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + CATEGORY_TABLE;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public Cursor selectAllDataFromTask() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TASK_TABLE;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    // Update specific Task
    public Cursor updateTask(TaskData td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + CATEGORY_TABLE
                + " SET "
                + "' WHERE " + CATEGORY_UNIQUE_ID + "='" + td.getTaskID() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    public Cursor updateCategory(CategoryData categoryData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + CATEGORY_TABLE
                + " SET "
                + CATEGORY_TITLE + "='" + categoryData.getCategoryTitle()
                + "', "
                + CATAGORY_UNIQUE_ID_FK + "='" + categoryData.getCategoryTitle()
                + "' WHERE " + CATEGORY_UNIQUE_ID + "='" + categoryData.getCategoryID() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    // Delete specific Task
    public Cursor deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + CATEGORY_TABLE
                + " WHERE "
                + CATEGORY_UNIQUE_ID + "='"
                + id + "'";
        Cursor result = db.rawQuery(query, null);
        return result;
    }
}
