package com.f0rgiv.taskmaster.service;

import android.app.Activity;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.analytics.UserProfile;
import com.amplifyframework.core.Amplify;

import java.util.Date;

public class AmplifyAnalytics {
  private static AmplifyAnalytics amplifyAnalytics;

  public static AmplifyAnalytics getAnalytics(){
    if (amplifyAnalytics == null){
      amplifyAnalytics = new AmplifyAnalytics();
      addUserData();
    }
    return amplifyAnalytics;
  }

  private static void addUserData(){
    if (Amplify.Auth.getCurrentUser() == null) return;
    UserProfile up = UserProfile.builder()
      .email(Amplify.Auth.getCurrentUser().getUsername())
      .build();
    Amplify.Analytics.identifyUser(Amplify.Auth.getCurrentUser().getUserId(), up);
  }

  public void getTimeSpentOnPage(Date start, Date end, String activity) {
    long durationInMS = end.getTime() - start.getTime();
    Amplify.Analytics.recordEvent(AnalyticsEvent.builder()
      .name("Time on Activity")
      .addProperty("Activity", activity)
      .addProperty("Seconds on page", ((int)durationInMS / 1000))
      .build());
  }
}
