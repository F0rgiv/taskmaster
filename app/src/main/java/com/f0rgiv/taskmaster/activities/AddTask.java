package com.f0rgiv.taskmaster.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.models.Task;
import com.f0rgiv.taskmaster.repository.TaskRepository;

import java.util.Locale;

public class AddTask extends AppCompatActivity {
  static int taskCount = 0;
  String TAG = "AddTask";
  Handler mainThreadHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);
    updateTotalTasks();

    findViewById(R.id.createNewTask).setOnClickListener(view -> {
      //create new task
      String title = ((TextView) findViewById(R.id.editTextNewTaskTitle)).getText().toString();
      String description = ((TextView) findViewById(R.id.editTextNewTeskDescription)).getText().toString();
      Task task = new Task(title, description, "new");
      TaskRepository.insert(task);

      //update ui
      ((TextView) findViewById(R.id.submitMsgText)).setText(R.string.submittedMsg);
      updateTotalTasks();
    });

    mainThreadHandler = new Handler(this.getMainLooper()) {
      @Override
      public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == 2) {
          ((TextView) findViewById(R.id.totalTasksText)).setText(String.format(Locale.ENGLISH,
            "Total tasks: %d",
            taskCount
          ));
          Log.i(TAG, "handleMessage: updated taskcount");
        }
      }
    };
  }

  @Override
  protected void onResume() {
    super.onResume();
    updateTotalTasks();
  }

  private void updateTotalTasks() {
    Log.i(TAG, "updateTotalTasks: about to update the task count");
    TaskRepository.findAll(result -> {
      taskCount = result.size();
      mainThreadHandler.sendEmptyMessage(2);
    });
  }
}
