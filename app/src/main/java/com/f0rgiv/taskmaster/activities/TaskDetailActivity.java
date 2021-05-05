package com.f0rgiv.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.CloudTask;
import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.repository.TaskRepository;

public class TaskDetailActivity extends AppCompatActivity {
  CloudTask cloudTask;
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
          ((TextView) findViewById(R.id.taskDetailTitle)).setText(cloudTask.getName());
          ((TextView) findViewById(R.id.taskDetailDetail)).setText(cloudTask.getDescription());
          ((TextView) findViewById(R.id.taskDetailStatus)).setText(cloudTask.getState());
        }
      }
    };

    TaskRepository.findById(getIntent().getStringExtra("taskId"), result -> {
      cloudTask = result;
      mainThreadHandler.sendEmptyMessage(3);
    });
  }
}
