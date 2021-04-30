package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.models.Task;
import com.f0rgiv.taskmaster.service.DatabaseManager;

import java.util.Locale;

public class AddTask extends AppCompatActivity {
  String TAG = "AddTask";

  DatabaseManager databaseManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);

    databaseManager = Room.databaseBuilder(getApplicationContext(), DatabaseManager.class, "f0rgiv_taskmaster")
      .allowMainThreadQueries()
      .build();

    updateTotalTasks();

    findViewById(R.id.createNewTask).setOnClickListener(view -> {
      String title = ((TextView) findViewById(R.id.editTextNewTaskTitle)).getText().toString();
      String description = ((TextView) findViewById(R.id.editTextNewTeskDescription)).getText().toString();
      Task task = new Task(title,description,"new");
      databaseManager.taskDao().insert(task);
      ((TextView) findViewById(R.id.submitMsgText)).setText(R.string.submittedMsg);
      updateTotalTasks();
    });
  }

  private void updateTotalTasks() {
    ((TextView) findViewById(R.id.totalTasksText)).setText(String.format(Locale.ENGLISH,
      "Total tasks: %d",
      databaseManager.taskDao().getCount()
    ));
  }
}
