package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.f0rgiv.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_detail);

    Intent intent = getIntent();
    ((TextView) findViewById(R.id.taskDetailTitle)).setText(intent.getStringExtra("taskName"));
  }
}
