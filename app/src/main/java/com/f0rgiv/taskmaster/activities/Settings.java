package com.f0rgiv.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.CloudTeam;
import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.adapters.TeamSpinnerActivity;
import com.f0rgiv.taskmaster.repository.TeamRepository;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
  static ArrayList<CloudTeam> teams = new ArrayList<>();

  TeamRepository teamRepository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    Handler mainThreadHandler;

    teamRepository = new TeamRepository();

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor preferencesEditor = preferences.edit();

    ((TextView) findViewById(R.id.currentuUernameText)).setText(preferences.getString("username", ""));

    findViewById(R.id.saveSettingsButton).setOnClickListener(view -> {
      String username = ((TextView) findViewById(R.id.editTextUsername)).getText().toString();
      String teamName = ((Spinner) findViewById(R.id.teamSpinner)).getSelectedItem().toString();
      ((TextView) findViewById(R.id.currentuUernameText)).setText(username);
      preferencesEditor.putString("username", username);
      preferencesEditor.putString("teamname", teamName);
      preferencesEditor.apply();
    });

    mainThreadHandler = new Handler(this.getMainLooper()) {
      @Override
      public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == 4) {
          Spinner spinner = (Spinner) findViewById(R.id.teamSpinner);
          ArrayAdapter<CloudTeam> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, teams);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinner.setAdapter(adapter);
        }
      }
    };

    //populates teams
    TeamRepository.findAll(teamsResult -> {
      for (CloudTeam team : teamsResult) teams.add(team);
      mainThreadHandler.sendEmptyMessage(4);
    });


  }
}
