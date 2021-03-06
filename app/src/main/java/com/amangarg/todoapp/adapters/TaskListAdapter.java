package com.amangarg.todoapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amangarg.todoapp.model.TaskData;
import com.amangarg.todoapp.R;
import com.amangarg.todoapp.sqlite.DatabaseHelper;

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
    public void onBindViewHolder(TaskListAdapter.TaskListViewHolder taskListViewHolder, final int position) {
        final TaskData taskData = TaskDataArrayList.get(position);

        taskListViewHolder.taskTitle.setText(taskData.getTaskTitle());

        if (taskData.getTaskStatus())
            taskListViewHolder.taskStatus.setText("Complete");
        else
            taskListViewHolder.taskStatus.setText("Pending");

        taskListViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = taskData.getTaskID();
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
        taskListViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_add_task);
                dialog.show();
                EditText taskTitle = dialog.findViewById(R.id.titleET);
                EditText taskDesc = dialog.findViewById(R.id.descriptionET);

                taskTitle.setText(taskData.getTaskTitle());
                taskDesc.setText(taskData.getTaskDescription());
                Button save = (Button) dialog.findViewById(R.id.btn_save);
                Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
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
            taskTitle = view.findViewById(R.id.taskTitleTV);
            taskStatus = view.findViewById(R.id.taskStatusTV);
            edit = view.findViewById(R.id.edit);
            deleteButton = view.findViewById(R.id.delete);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
