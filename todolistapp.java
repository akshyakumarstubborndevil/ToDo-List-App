package com.kalyani.todolistapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextTask;
    Button buttonAdd;
    ListView listViewTasks;

    ArrayList<String> taskList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                taskList
        );

        listViewTasks.setAdapter(adapter);

        // Add Task
        buttonAdd.setOnClickListener(v -> {

            String task = editTextTask.getText().toString();

            if (!task.isEmpty()) {
                taskList.add(task);
                adapter.notifyDataSetChanged();
                editTextTask.setText("");
            }
        });

        // Delete Task
        listViewTasks.setOnItemLongClickListener((parent, view, position, id) -> {

            taskList.remove(position);
            adapter.notifyDataSetChanged();

            return true;
        });

        // Edit Task
        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {

            EditText editTask = new EditText(MainActivity.this);
            editTask.setText(taskList.get(position));

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Edit Task")
                    .setView(editTask)
                    .setPositiveButton("Save", (dialog, which) -> {

                        taskList.set(position, editTask.getText().toString());
                        adapter.notifyDataSetChanged();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}