package com.f0rgiv.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Button addTaskButton = findViewById(R.id.addTaskButton);
//      addTaskButton.setOnClickListener(view -> {
//        Intent goToAddTask = new Intent(MainActivity.this, AddTask.class);
//        MainActivity.this.startActivity(goToAddTask);
//      });

      ((Button) findViewById(R.id.addTaskButton)).setOnClickListener(view -> {
        Intent goToAddTask = new Intent(MainActivity.this, AddTask.class);
        MainActivity.this.startActivity(goToAddTask);
      });
    }
}
