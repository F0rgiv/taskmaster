package com.f0rgiv.taskmaster.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CloudTask;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.adapters.TaskRecyclerAdapter;
import com.f0rgiv.taskmaster.repository.TaskRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AnalyticsActivity {//AppCompatActivity {
  public static List<CloudTask> cloudTasks = new ArrayList<>();
  static String TAG = "mainActivity";
  static String OPENED_APP_EVENT = "Application opened";

  Handler mainThreadHandler;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.settings, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.go_to_settings) {
      MainActivity.this.startActivity(new Intent(MainActivity.this, Settings.class));
      return true;
    }
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    configureAmplify();
    checkAndRequestPermissions();

    findViewById(R.id.addTaskButton).setOnClickListener(view ->
      MainActivity.this.startActivity(new Intent(MainActivity.this, AddTask.class)));

    findViewById(R.id.allTasks).setOnClickListener(view ->
      MainActivity.this.startActivity(new Intent(MainActivity.this, AllTasks.class)));

    RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
    RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(lm);
    recyclerView.setAdapter(new TaskRecyclerAdapter(vh -> {
      Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
      intent.putExtra("taskId", vh.cloudTask.getId());
      MainActivity.this.startActivity(intent);
    },
      cloudTasks));

    mainThreadHandler = new Handler(this.getMainLooper()) {
      @Override
      public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == 1) {
          Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
          Log.i(TAG, "handleMessage: recycler updated");
        }
      }
    };
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onResume() {
    Log.i(TAG, "Resumed: We are here");
    super.onResume();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String greeting = "Tasks";
    String username = preferences.getString("username", null);
    if (username != null) greeting = String.format(Locale.ENGLISH, "%s's tasks", username);
    ((TextView) findViewById(R.id.mainTasksGreetingLabel)).setText(greeting);

    String teamName = preferences.getString("teamname", "none");
    TaskRepository.findByTeam(teamName, result -> {
      cloudTasks.clear();
      cloudTasks.addAll(result);
      mainThreadHandler.sendEmptyMessage(1);
    });
  }

  private void configureAmplify() {
    try {
      Amplify.addPlugin(new AWSApiPlugin());
      Amplify.addPlugin(new AWSCognitoAuthPlugin());
      Amplify.addPlugin(new AWSS3StoragePlugin());
      Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(getApplication()));
      Amplify.configure(getApplicationContext());
      Log.i(TAG, "Initialized Amplify");
    } catch (AmplifyException error) {
      Log.e(TAG, "Could not initialize Amplify", error);
    }
  }


  private void checkAndRequestPermissions() {
    requestPermissions(new String[]{
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
      },
      1
    );

  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  void configureNotificationChannel() {
    String CHANNEL_ID = "100";
    NotificationChannel channel = new NotificationChannel(
      CHANNEL_ID,
      "Taskmaster Notifications",
      NotificationManager.IMPORTANCE_DEFAULT);
    channel.setDescription("new task!");
    NotificationManager notificationManager = getSystemService(NotificationManager.class);
    notificationManager.createNotificationChannel(channel);
  }
}
