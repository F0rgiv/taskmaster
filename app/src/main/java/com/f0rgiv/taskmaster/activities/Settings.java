package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.f0rgiv.taskmaster.R;

public class Settings extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor preferencesEditor = preferences.edit();

    ((TextView) findViewById(R.id.currentuUernameText)).setText(preferences.getString("username", ""));

    findViewById(R.id.saveSettingsButton).setOnClickListener(view -> {
      String username = ((TextView) findViewById(R.id.editTextUsername)).getText().toString();
      ((TextView) findViewById(R.id.currentuUernameText)).setText(username);
      preferencesEditor.putString("username", username);
      preferencesEditor.apply();
    });
  }
}
