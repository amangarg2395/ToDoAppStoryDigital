package com.amangarg.todoapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amangarg.todoapp.R;
import com.amangarg.todoapp.activity.MainTaskActivity;
import com.amangarg.todoapp.model.CategoryData;
import com.amangarg.todoapp.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {

    List<CategoryData> CategoryDataArrayList;
    Context context;

    public CategoryListAdapter(ArrayList<CategoryData> categoryDataArrayList, Context context) {
        this.CategoryDataArrayList = categoryDataArrayList;
        this.context = context;
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_cardlayout, parent, false);
        CategoryListViewHolder categoryListViewHolder = new CategoryListViewHolder(view, context);
        return categoryListViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryListViewHolder categoryListViewHolder, final int position) {
        final CategoryData categoryData = CategoryDataArrayList.get(position);
        categoryListViewHolder.categoryDetails.setText(categoryData.getCategoryTitle());

        categoryListViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = categoryData.getCategoryID();
                DatabaseHelper mysqlite = new DatabaseHelper(view.getContext());
                Cursor b = mysqlite.deleteTask(id);
                if (b.getCount() == 0) {
                    Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(view.getContext(), "Deleted else", Toast.LENGTH_SHORT).show();
                }


            }
        });
        categoryListViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();
                EditText todoText = (EditText) dialog.findViewById(R.id.input_task_desc);
                EditText todoNote = (EditText) dialog.findViewById(R.id.input_task_notes);
                CheckBox cb = (CheckBox) dialog.findViewById(R.id.checkbox);
                RadioButton rbHigh = (RadioButton) dialog.findViewById(R.id.high);
                RadioButton rbNormal = (RadioButton) dialog.findViewById(R.id.normal);
                RadioButton rbLow = (RadioButton) dialog.findViewById(R.id.low);
                LinearLayout lv = (LinearLayout) dialog.findViewById(R.id.linearLayout);
                TextView tv = (TextView) dialog.findViewById(R.id.Remainder);
                tv.setVisibility(View.GONE);
                lv.setVisibility(View.GONE);
                todoText.setText(categoryData.getCategoryTitle());
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
                        EditText categoryText = (EditText) dialog.findViewById(R.id.input_task_desc);
                        EditText categoryNote = (EditText) dialog.findViewById(R.id.input_task_notes);
                        CheckBox cb = (CheckBox) dialog.findViewById(R.id.checkbox);
                        if (categoryText.getText().length() >= 2) {

                            CategoryData updateCd = new CategoryData();
                            updateCd.setCategoryId(categoryData.getCategoryID());
//                            updateCd.setCategoryDetails(categoryText.getText().toString());
//
//                            updateCd.setCategoryNotes(categoryNote.getText().toString());

                            DatabaseHelper mysqlite = new DatabaseHelper(view.getContext());
                            Cursor b = mysqlite.updateCategory(updateCd);
                            CategoryDataArrayList.set(position, updateCd);
                            if (b.getCount() == 0) {
                                //Toast.makeText(view.getContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Code here will run in UI thread
                                        notifyDataSetChanged();
                                    }
                                });
                                dialog.hide();
                            } else {


                                dialog.hide();

                            }

                        } else {
                            Toast.makeText(view.getContext(), "Please enter a category", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        categoryListViewHolder.categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(context, MainTaskActivity.class);
                Integer UID =  categoryData.getCategoryID();
                categoryIntent.putExtra("UID", UID);
                context.startActivity(categoryIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return CategoryDataArrayList.size();
    }

    public class CategoryListViewHolder extends RecyclerView.ViewHolder {
        TextView categoryDetails, categoryNotes;
        ImageView editButton, deleteButton;
        CardView categoryCardView;

        public CategoryListViewHolder(View view, final Context context) {
            super(view);
            categoryDetails = view.findViewById(R.id.toDoTextDetails);
            categoryNotes = view.findViewById(R.id.toDoTextNotes);
            editButton = view.findViewById(R.id.edit);
            deleteButton = view.findViewById(R.id.delete);
            categoryCardView = view.findViewById(R.id.cardview);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
