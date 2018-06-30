package com.amangarg.todoapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amangarg.todoapp.model.TaskData;
import com.amangarg.todoapp.R;
import com.amangarg.todoapp.sqlite.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amangarg on 6/29/18.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {
    List<TaskData> TaskDataArrayList;
    Context context;

    public TaskListAdapter(ArrayList<TaskData> taskDataArrayList, Context context) {
        this.TaskDataArrayList = taskDataArrayList;
        this.context = context;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_cardlayout, parent, false);
        TaskListViewHolder taskListViewHolder = new TaskListViewHolder(view, context);
        return taskListViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, final int position) {
        final TaskData td = TaskDataArrayList.get(position);

        holder.taskTitle.setText(td.getTaskTitle());

        if (td.getTaskStatus())
            holder.taskStatus.setText("Complete");
        else
            holder.taskStatus.setText("Pending");

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = td.getTaskID();
                SqliteHelper mysqlite = new SqliteHelper(view.getContext());
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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_add_task);
                dialog.show();
                EditText taskTitle = dialog.findViewById(R.id.titleET);
                EditText taskDesc = dialog.findViewById(R.id.descriptionET);
                LinearLayout lv = dialog.findViewById(R.id.linearLayout);
                lv.setVisibility(View.GONE);

                taskTitle.setText(td.getTaskTitle());
                taskDesc.setText(td.getTaskDescription());
                Button save = (Button) dialog.findViewById(R.id.btn_save);
                Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText todoText = (EditText) dialog.findViewById(R.id.input_task_desc);
                        EditText todoNote = (EditText) dialog.findViewById(R.id.input_task_notes);
                        CheckBox cb = (CheckBox) dialog.findViewById(R.id.checkbox);
                        if (todoText.getText().length() >= 2) {
                            RadioGroup proritySelection = (RadioGroup) dialog.findViewById(R.id.toDoRG);
                            String RadioSelection = new String();
                            if (proritySelection.getCheckedRadioButtonId() != -1) {
                                int id = proritySelection.getCheckedRadioButtonId();
                                View radiobutton = proritySelection.findViewById(id);
                                int radioId = proritySelection.indexOfChild(radiobutton);
                                RadioButton btn = (RadioButton) proritySelection.getChildAt(radioId);
                                RadioSelection = (String) btn.getText();
                            }
                            TaskData updateTd = new TaskData();
//                            updateTd.setToDoID(td.getToDoID());
//                            updateTd.setToDoTaskDetails(todoText.getText().toString());
//                            updateTd.setToDoTaskPrority(RadioSelection);
//                            updateTd.setToDoNotes(todoNote.getText().toString());
//                            if (cb.isChecked()) {
//                                updateTd.setToDoTaskStatus("Complete");
//                            } else {
//                                updateTd.setToDoTaskStatus("Incomplete");
//                            }
                            SqliteHelper mysqlite = new SqliteHelper(view.getContext());
                            Cursor b = mysqlite.updateTask(updateTd);
                            TaskDataArrayList.set(position, updateTd);
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
                            Toast.makeText(view.getContext(), "Please enter To Do Task", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }


    @Override
    public int getItemCount() {
        return TaskDataArrayList.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskStatus;
        ImageView edit, deleteButton;

        public TaskListViewHolder(View view, final Context context) {
            super(view);
            taskTitle = (TextView) view.findViewById(R.id.taskTitleTV);
            taskStatus = (TextView) view.findViewById(R.id.taskStatusTV);
            edit = (ImageView) view.findViewById(R.id.edit);
            deleteButton = (ImageView) view.findViewById(R.id.delete);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
