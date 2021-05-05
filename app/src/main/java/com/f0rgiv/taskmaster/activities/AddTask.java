package com.f0rgiv.taskmaster.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.CloudTask;
import com.amplifyframework.datastore.generated.model.CloudTeam;
import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.repository.TaskRepository;
import com.f0rgiv.taskmaster.repository.TeamRepository;

import java.util.ArrayList;
import java.util.Locale;

public class AddTask extends AppCompatActivity {
  static ArrayList<CloudTeam> teams = new ArrayList<>();
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
      TeamRepository.findByName(((Spinner) findViewById(R.id.addTaskTeamSpinner)).getSelectedItem().toString(),
        team -> {
          CloudTask cloudTask = CloudTask.builder()
            .team(team)
            .description(description)
            .name(title)
            .state("new")
            .build();
          TaskRepository.insert(cloudTask);
        });

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
        if (msg.what == 4) {
          Spinner spinner = (Spinner) findViewById(R.id.addTaskTeamSpinner);
          ArrayAdapter<CloudTeam> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, teams);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinner.setAdapter(adapter);
        }
      }
    };
  }

  @Override
  protected void onResume() {
    super.onResume();
    updateTotalTasks();
    TeamRepository.findAll(teamsResult -> {
      teams.addAll(teamsResult);
      mainThreadHandler.sendEmptyMessage(4);
    });
  }

  private void updateTotalTasks() {
    Log.i(TAG, "updateTotalTasks: about to update the task count");
    TaskRepository.findAll(result -> {
      taskCount = result.size();
      mainThreadHandler.sendEmptyMessage(2);
    });
  }
}
