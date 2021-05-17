package com.f0rgiv.taskmaster.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.f0rgiv.taskmaster.service.AmplifyAnalytics;

import java.util.Date;

public class AnalyticsActivity extends AppCompatActivity {
  Date start;

  @Override
  protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    start = new Date();
  }

  @Override
  protected void onResume() {
    super.onResume();
    start = new Date();
  }

  @Override
  protected void onPause() {
    super.onPause();
    AmplifyAnalytics.getAnalytics().getTimeSpentOnPage(start, new Date(), this.getClass().getName());
  }
}
