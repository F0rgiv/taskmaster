package com.f0rgiv.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.f0rgiv.taskmaster.repository.TeamRepository;

import java.util.ArrayList;

public class Settings extends AnalyticsActivity {
  static ArrayList<CloudTeam> teams = new ArrayList<>();

  TeamRepository teamRepository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    Handler mainThreadHandler;

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

    findViewById(R.id.SettingsSignUpButton).setOnClickListener(view -> {
      Intent intent = new Intent(Settings.this, SignUp.class);
      startActivity(intent);
    });

    findViewById(R.id.SettingsSignInButton).setOnClickListener(view -> {
      Intent intent = new Intent(Settings.this, SignIn.class);
      startActivity(intent);
    });

    mainThreadHandler = new Handler(this.getMainLooper()) {
      @Override
      public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == 4) {
          Spinner spinner = findViewById(R.id.teamSpinner);
          spinner.setAdapter(new ArrayAdapter<>(Settings.this, android.R.layout.simple_spinner_dropdown_item, teams));
        }
      }
    };

    //populates teams
    TeamRepository.findAll(teamsResult -> {
      teams.clear();
      teams.addAll(teamsResult);
      mainThreadHandler.sendEmptyMessage(4);
    });
  }
}
