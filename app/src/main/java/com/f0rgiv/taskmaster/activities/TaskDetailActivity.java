package com.f0rgiv.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.models.Task;
import com.f0rgiv.taskmaster.repository.TaskRepository;
import com.f0rgiv.taskmaster.service.DatabaseManager;

public class TaskDetailActivity extends AppCompatActivity {
  Task task;
  Handler mainThreadHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_detail);

    mainThreadHandler = new Handler(this.getMainLooper()) {
      @Override
      public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == 3) {
          ((TextView) findViewById(R.id.taskDetailTitle)).setText(task.title);
          ((TextView) findViewById(R.id.taskDetailDetail)).setText(task.description);
          ((TextView) findViewById(R.id.taskDetailStatus)).setText(task.state);
        }
      }
    };

    TaskRepository.findById(getIntent().getStringExtra("taskId"), result -> {
      task = result;
      mainThreadHandler.sendEmptyMessage(3);
    });
  }
}
