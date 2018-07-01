package com.amangarg.todoapp.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amangarg.todoapp.R;
import com.amangarg.todoapp.adapters.TaskListAdapter;
import com.amangarg.todoapp.model.Picture;
import com.amangarg.todoapp.model.TaskData;
import com.amangarg.todoapp.sqlite.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;

public class MainTaskActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int SELECT_SINGLE_PICTURE = 101;
    private static final int SELECT_MULTIPLE_PICTURE = 201;
    public static final String IMAGE_TYPE = "image/*";
    private ImageView selectedImagePreview;
    FloatingActionButton addTaskFAB;
    RecyclerView taskRV;
    RecyclerView.Adapter taskAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TaskData> taskDataArrayList = new ArrayList<>();
    DatabaseHelper mysqlite;
    SwipeRefreshLayout swipeRefreshLayout;
    DatabaseHelper myDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_main);
        myDatabaseHelper = new DatabaseHelper(this);
        taskRV = findViewById(R.id.taskRV);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        addTaskFAB = findViewById(R.id.addTaskFAB);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshTask);

        Intent intent = getIntent();
        final Integer categoryId = intent.getIntExtra("UID", 0);

        taskAdapter = new TaskListAdapter(taskDataArrayList, getApplicationContext());
        taskRV.setLayoutManager(layoutManager);
        taskRV.setAdapter(taskAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent), getResources().getColor(R.color.divider));
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                updateCardView(categoryId);
            }
        });
        addTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainTaskActivity.this);
                dialog.setContentView(R.layout.dialog_add_task);
                dialog.show();
                Button save = dialog.findViewById(R.id.save_btn);
                Button cancel = dialog.findViewById(R.id.cancel_btn);

                dialog.findViewById(R.id.uploadPhotoBtn).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {

                        Intent intent = new Intent();
                        intent.setType(IMAGE_TYPE);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                getString(R.string.select_picture)), SELECT_SINGLE_PICTURE);
                    }
                });

                selectedImagePreview = (ImageView) findViewById(R.id.previewIV);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText taskTitleET = dialog.findViewById(R.id.titleET);
                        EditText taskDescET = dialog.findViewById(R.id.descriptionET);
                        SwitchCompat statusSwitch = dialog.findViewById(R.id.switchButton);
                        if (taskTitleET.getText().length() >= 1 && taskDescET.getText().length() >= 1) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("TaskTitle", taskTitleET.getText().toString());
                            contentValues.put("TaskDescription", taskDescET.getText().toString());
                            contentValues.put("TaskImage", taskDescET.getText().toString());
                            contentValues.put("TaskStatus", statusSwitch.isChecked());
                            mysqlite = new DatabaseHelper(getApplicationContext());
                            Boolean b = mysqlite.insertIntoTask(contentValues);
                            if (b) {
                                dialog.hide();
                                addAndUpdateCardView();
                            } else {
                                Toast.makeText(getApplicationContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter To Do Task", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });
    }

    public void updateCardView(Integer categoryId) {
        swipeRefreshLayout.setRefreshing(true);
        mysqlite = new DatabaseHelper(getApplicationContext());
        Cursor result = mysqlite.selectParticularCategoryTask(categoryId);
        if (result.getCount() == 0) {
            taskDataArrayList.clear();
            taskAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "No Tasks", Toast.LENGTH_SHORT).show();
        } else {
            taskDataArrayList.clear();
            taskAdapter.notifyDataSetChanged();
            while (result.moveToNext()) {
                TaskData taskData = new TaskData();
                taskData.setTaskID(result.getInt(0));
                taskData.setTaskTitle(result.getString(1));
                taskData.setTaskDescription(result.getString(2));
                taskData.setTaskImage(result.getString(3));
                taskData.setTaskStatus(result.getInt(4) > 0);
                taskDataArrayList.add(taskData);
            }
            taskAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    public void addAndUpdateCardView() {
        swipeRefreshLayout.setRefreshing(true);
        mysqlite = new DatabaseHelper(getApplicationContext());
        Cursor result = mysqlite.selectAllDataFromCategory();
        if (result.getCount() == 0) {
            taskDataArrayList.clear();
            taskAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "No Tasks", Toast.LENGTH_SHORT).show();
        } else {
            taskDataArrayList.clear();
            taskAdapter.notifyDataSetChanged();
            while (result.moveToNext()) {
                TaskData taskData = new TaskData();
                taskData.setTaskID(result.getInt(0));
                taskData.setTaskTitle(result.getString(1));
                taskData.setTaskDescription(result.getString(2));
                taskData.setTaskImage(result.getString(3));
                taskData.setTaskStatus(result.getInt(4) > 0);
                taskDataArrayList.add(taskData);
            }
            taskAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            Uri selectedImageUri = data.getData();
            try {
                selectedImagePreview.setImageBitmap(new Picture(selectedImageUri, getContentResolver()).getBitmap());
            } catch (IOException e) {
                Log.e(MainCategoryActivity.class.getSimpleName(), "Failed to load image", e);
            }
        }
    }
}
