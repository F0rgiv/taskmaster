package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.f0rgiv.taskmaster.R;

import java.util.Locale;

public class AddTask extends AppCompatActivity {
  String TAG = "AddTask";

  Integer count = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);


    findViewById(R.id.createNewTask).setOnClickListener(view -> {
      count += 1;
      ((TextView) findViewById(R.id.submitMsgText)).setText(R.string.submittedMsg);
      ((TextView) findViewById(R.id.totalTasksText)).setText(String.format(Locale.ENGLISH,"Total tasks: %d", count));
    });
  }
}
