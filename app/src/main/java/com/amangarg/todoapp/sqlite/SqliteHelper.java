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

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ToDoDb.db";

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

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoryTableQuery = "create table if not exists " + CATEGORY_TABLE + " ( INTEGER PRIMARY KEY AUTOINCREMENT, CategoryTitle TEXT)";
        String createTaskTableQuery = "create table if not exists " + TASK_TABLE + " ( TaskUniqueId INTEGER PRIMARY KEY AUTOINCREMENT, TaskTitle TEXT, TaskDescription TEXT, TaskImage TEXT, TaskStatus BOOLEAN, FOREIGN KEY(CategoryUniqueId) REFERENCES Category(CategoryUniqueId))";
        db.execSQL(createCategoryTableQuery);
        db.execSQL(createTaskTableQuery);
    }

    // Drop Table for new table creation
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }

    // Insert into Table
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
