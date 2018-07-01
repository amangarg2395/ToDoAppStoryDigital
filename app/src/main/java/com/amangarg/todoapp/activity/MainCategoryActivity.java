package com.amangarg.todoapp.activity;


import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amangarg.todoapp.R;
import com.amangarg.todoapp.adapters.CategoryListAdapter;
import com.amangarg.todoapp.model.CategoryData;
import com.amangarg.todoapp.sqlite.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by amangarg on 6/29/18.
 */

public class MainCategoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    FloatingActionButton addCategoryFAB;
    RecyclerView categoryRV;
    RecyclerView.Adapter categoryAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CategoryData> categoryDataArrayList = new ArrayList<>();
    DatabaseHelper mysqlite;
    SwipeRefreshLayout swipeRefreshLayout;
    DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);

        myDatabaseHelper = new DatabaseHelper(this);
        categoryRV = findViewById(R.id.categoryRV);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        addCategoryFAB = findViewById(R.id.addCategoryFAB);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        categoryAdapter = new CategoryListAdapter(categoryDataArrayList, getApplicationContext());
        categoryRV.setLayoutManager(layoutManager);
        categoryRV.setAdapter(categoryAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent), getResources().getColor(R.color.divider));
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                updateCardView();
            }
        });
        addCategoryFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainCategoryActivity.this);
                dialog.setContentView(R.layout.dialog_add_category);
                dialog.show();
                Button save = dialog.findViewById(R.id.btn_save);
                Button cancel = dialog.findViewById(R.id.btn_cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText categoryTitleET = dialog.findViewById(R.id.inputCategoryET);
                        if (categoryTitleET.getText().length() >= 1) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("CategoryTitle", categoryTitleET.getText().toString());
                            mysqlite = new DatabaseHelper(getApplicationContext());
                            Boolean b = mysqlite.insertIntoCategory(contentValues);
                            if (b) {
                                dialog.hide();
                                updateCardView();
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

    public void updateCardView() {
        swipeRefreshLayout.setRefreshing(true);
        mysqlite = new DatabaseHelper(getApplicationContext());
        Cursor result = mysqlite.selectAllDataFromCategory();
        if (result.getCount() == 0) {
            categoryDataArrayList.clear();
            categoryAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "No Categories", Toast.LENGTH_SHORT).show();
        } else {
            categoryDataArrayList.clear();
            categoryAdapter.notifyDataSetChanged();
            while (result.moveToNext()) {
                CategoryData categoryData = new CategoryData();
                categoryData.setCategoryId(result.getInt(0));
                categoryData.setCategoryTitle(result.getString(1));
                categoryDataArrayList.add(categoryData);
            }
            categoryAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        updateCardView();
    }
}
