package com.f0rgiv.taskmaster.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

public class TeamSpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }

  public interface HandleSelected {
    void handleSelected(String val);
  }
}
