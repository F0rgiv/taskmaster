package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.models.Task;
import com.f0rgiv.taskmaster.service.DatabaseManager;

public class TaskDetailActivity extends AppCompatActivity {

  DatabaseManager databaseManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_detail);

    databaseManager = Room.databaseBuilder(getApplicationContext(), DatabaseManager.class, "f0rgiv_taskmaster")
      .allowMainThreadQueries()
      .build();

    Task task = databaseManager.taskDao().findById(getIntent().getLongExtra("taskId",0));
    ((TextView) findViewById(R.id.taskDetailTitle)).setText(task.title);
    ((TextView) findViewById(R.id.taskDetailDetail)).setText(task.description);
    ((TextView) findViewById(R.id.taskDetailStatus)).setText(task.state);
  }
}
