package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.f0rgiv.taskmaster.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
  String TAG = "main";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

    //data population

    String greeting = "Tasks";
    String username = preferences.getString("username", null);
    if (username != null) greeting = String.format(Locale.ENGLISH, "%s's tasks", username);
    ((TextView) findViewById(R.id.mainTasksGreetingLabel)).setText(greeting);

    //onClickListeners

    findViewById(R.id.addTaskButton).setOnClickListener(view ->
      MainActivity.this.startActivity(new Intent(MainActivity.this, AddTask.class)));

    findViewById(R.id.allTasks).setOnClickListener(view ->
      MainActivity.this.startActivity(new Intent(MainActivity.this, AllTasks.class)));

    findViewById(R.id.mainGoToSettingsButton).setOnClickListener(view ->
      MainActivity.this.startActivity(new Intent(MainActivity.this, Settings.class)));

    findViewById(R.id.task1button).setOnClickListener(HandleTaskButtonClick());
    findViewById(R.id.task2button).setOnClickListener(HandleTaskButtonClick());
    findViewById(R.id.task3button).setOnClickListener(HandleTaskButtonClick());
    findViewById(R.id.task4button).setOnClickListener(HandleTaskButtonClick());
  }

  //onClick callbacks

  private View.OnClickListener HandleTaskButtonClick() {
    return view ->{
      Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
      intent.putExtra("taskName", ((Button)view).getText().toString());
      MainActivity.this.startActivity(intent);
      };
  }
}
