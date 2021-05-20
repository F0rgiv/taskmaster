package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.f0rgiv.taskmaster.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AllTasks extends AnalyticsActivity {
  AdView adView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_tasks);

    MobileAds.initialize(getApplicationContext());
    adView = findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);
  }
}
