package com.f0rgiv.taskmaster.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.CloudTask;
import com.amplifyframework.datastore.generated.model.CloudTeam;
import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.repository.TaskRepository;
import com.f0rgiv.taskmaster.repository.TeamRepository;

import java.util.Locale;

public class AddTask extends AppCompatActivity {
  static int taskCount = 0;
  String TAG = "AddTask";
  Handler mainThreadHandler;
  CloudTeam team;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);
    updateTotalTasks();

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String teamName = preferences.getString("teamname", null);
    TeamRepository.findByName(teamName, result -> this.team = result);

    findViewById(R.id.createNewTask).setOnClickListener(view -> {
      //create new task
      String title = ((TextView) findViewById(R.id.editTextNewTaskTitle)).getText().toString();
      String description = ((TextView) findViewById(R.id.editTextNewTeskDescription)).getText().toString();
      CloudTask cloudTask = CloudTask.builder()
        .team(team)
        .description(description)
        .name(title)
        .state("new")
        .build();
      TaskRepository.insert(cloudTask);

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
