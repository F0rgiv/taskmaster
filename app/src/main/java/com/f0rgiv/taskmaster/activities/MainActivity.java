package com.f0rgiv.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.adapters.TaskRecyclerAdapter;
import com.f0rgiv.taskmaster.models.Task;
import com.f0rgiv.taskmaster.service.DatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
  public static List<Task> tasks = new ArrayList<>();
  String TAG = "mainActivity";
  DatabaseManager databaseManager;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.settings, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.go_to_settings:
        MainActivity.this.startActivity(new Intent(MainActivity.this, Settings.class));
        return true;
      default:
        return true;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.addTaskButton).setOnClickListener(view ->
      MainActivity.this.startActivity(new Intent(MainActivity.this, AddTask.class)));

    findViewById(R.id.allTasks).setOnClickListener(view ->
      MainActivity.this.startActivity(new Intent(MainActivity.this, AllTasks.class)));


  }

  @Override
  protected void onResume() {
    Log.i(TAG, "Resumed: We are here");
    super.onResume();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String greeting = "Tasks";
    String username = preferences.getString("username", null);
    if (username != null) greeting = String.format(Locale.ENGLISH, "%s's tasks", username);
    ((TextView) findViewById(R.id.mainTasksGreetingLabel)).setText(greeting);

    databaseManager = Room.databaseBuilder(getApplicationContext(), DatabaseManager.class, "f0rgiv_taskmaster")
      .allowMainThreadQueries()
      .build();

    RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
    RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(lm);
    recyclerView.setAdapter(new TaskRecyclerAdapter(vh -> {
      Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
      intent.putExtra("taskId", vh.task.id);
      MainActivity.this.startActivity(intent);
    },
      databaseManager.taskDao().findAll()));
  }

//  TaskRecyclerAdapter.HandleOnClickTask handleOnClickTask = new TaskRecyclerAdapter.HandleOnClickTask() {
//    @Override
//    void handleClickOnTask(TaskRecyclerAdapter.TaskViewHolder taskViewHolder) {
//
//    }
//  }
}
