package com.f0rgiv.taskmaster.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.datastore.generated.model.CloudTask;
import com.amplifyframework.datastore.generated.model.CloudTeam;
import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.repository.TaskRepository;
import com.f0rgiv.taskmaster.repository.TeamRepository;
import com.f0rgiv.taskmaster.service.AmplifyS3;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddTask extends AnalyticsActivity {
  static ArrayList<CloudTeam> teams = new ArrayList<>();
  static int taskCount = 0;
  String TAG = "AddTask";
  Handler mainThreadHandler;
  File fileToUpload;
  FusedLocationProviderClient fusedLocationProviderClient;
  Geocoder geocoder;
  Address address;

  @RequiresApi(api = Build.VERSION_CODES.Q)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);
    updateTotalTasks();

    loadLocationProvider();
    getCurrentLocation();

    Intent intent = getIntent();
    if(intent.getType() != null && intent.getType().startsWith("image/")){
      Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
      try {
        fileToUpload = new File(getApplicationContext().getFilesDir(), "tempFile");
        InputStream is = getContentResolver().openInputStream(uri);
        FileUtils.copy(is, new FileOutputStream(fileToUpload));
      } catch (IOException e) {
        e.printStackTrace();
      }
      Log.i(TAG, "getFileFromPhone: Got the file from the opener");
    }

    findViewById(R.id.createNewTask).setOnClickListener(this::createNewTask);
    findViewById(R.id.getPhotoForNewTask).setOnClickListener(view -> getFileFromPhone());

    mainThreadHandler = new Handler(this.getMainLooper()) {
      @Override
      public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == 2) {
          ((TextView) findViewById(R.id.totalTasksText)).setText(String.format(Locale.ENGLISH,
            "Total tasks: %d",
            taskCount
          ));
          Log.i(TAG, "handleMessage: updated taskCount");
        }
        if (msg.what == 4) {
          Spinner spinner = findViewById(R.id.addTaskTeamSpinner);
          ArrayAdapter<CloudTeam> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, teams);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinner.setAdapter(adapter);
        }
        if (msg.what == 5) Log.i(TAG, "handleMessage: here???");
      }
    };
  }

  private void getFileFromPhone() {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("*/*");
    Log.i(TAG, "getFileFromPhone: Starting select photo intent");
    startActivityForResult(intent, 5);
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.i(TAG, "onActivityResult: entered");
    if (requestCode == 5) {
      fileToUpload = new File(getApplicationContext().getFilesDir(), "tempFile");
      try {
        Log.i(TAG, "onActivityResult: About to copy");
        InputStream is = getContentResolver().openInputStream(data.getData());
        FileUtils.copy(is, new FileOutputStream(fileToUpload));
        Log.i(TAG, "getFileFromPhone: Got the file");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    updateTotalTasks();
    TeamRepository.findAll(teamsResult -> {
      teams.clear();
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

  private void createNewTask(View view) {
    //create new task
    String title = ((TextView) findViewById(R.id.editTextNewTaskTitle)).getText().toString();
    String description = ((TextView) findViewById(R.id.editTextNewTaskDescription)).getText().toString();
    TeamRepository.findByName(((Spinner) findViewById(R.id.addTaskTeamSpinner)).getSelectedItem().toString(),
      team -> {
        CloudTask cloudTask = CloudTask.builder()
          .team(team)
          .description(description)
          .name(title)
          .state("new")
          .address(address.getAddressLine(0))
          .build();
        TaskRepository.insert(cloudTask);
        if (fileToUpload != null) {
          Log.i(TAG, "onCreate: file exists, file: " + fileToUpload.getTotalSpace());
          AmplifyS3.saveFileToS3(fileToUpload, cloudTask.getId());
        }
      });

    //update ui
    ((TextView) findViewById(R.id.submitMsgText)).setText(R.string.submittedMsg);
    updateTotalTasks();
  }


  private void getCurrentLocation() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    fusedLocationProviderClient.getLastLocation()
      .addOnSuccessListener(location -> {
        Log.i(TAG, "getCurrentLocationComplete: " + location);
        List<Address> address = null;
        try {
          address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
          this.address = address.get(0);
        } catch (IOException e) {
          e.printStackTrace();
        }
        Log.i(TAG, "getCurrentLocation: addresses" + address);
      })
      .addOnCompleteListener(location -> Log.i(TAG, "getCurrentLocation: " + location));
  }

  private void loadLocationProvider() {
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
  }
}
