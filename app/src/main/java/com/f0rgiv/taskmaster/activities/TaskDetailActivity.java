package com.f0rgiv.taskmaster.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.CloudTask;
import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.repository.TaskRepository;
import com.f0rgiv.taskmaster.service.AmplifyS3;

public class TaskDetailActivity extends AnalyticsActivity {
  static String TAG = "taskDetail";
  CloudTask cloudTask;
  Handler mainThreadHandler;
  String taskId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_detail);
    taskId = getIntent().getStringExtra("taskId");

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

    TaskRepository.findById(taskId, result -> {
      cloudTask = result;
      mainThreadHandler.sendEmptyMessage(3);
    });

    ImageView iv = findViewById(R.id.taskDetailImageView);
    Log.i(TAG, "onCreate: taskid: " + taskId);
    AmplifyS3.loadFileFromS3(getApplicationContext(), taskId, bm -> {
      Log.i(TAG, "onCreate: got image");
      Log.i(TAG, bm.toString());
      iv.setImageBitmap(bm);
    });
  }
}
