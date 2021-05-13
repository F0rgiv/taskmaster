package com.f0rgiv.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.f0rgiv.taskmaster.R;

public class SignIn extends AppCompatActivity {
  String TAG = "signin";
  Handler handler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login2);

    handler = new Handler(getMainLooper()) {
      @Override
      public void handleMessage(Message msg) {
        if (msg.what == 1) {
          Log.i(TAG, "handleMessage: success signin");
          Toast.makeText(SignIn.this, "signed in", Toast.LENGTH_LONG).show();
          startActivity(new Intent(SignIn.this, MainActivity.class));
        } else if (msg.what == 2) {
          Log.i(TAG, "handleMessage: fail signin");

          Toast.makeText(SignIn.this, "sign in failed", Toast.LENGTH_LONG).show();
        }
      }
    };

    findViewById(R.id.SignInButton).setOnClickListener(v -> {
      String username = ((EditText) findViewById(R.id.SignInUsernameText)).getText().toString();
      String password = ((EditText) findViewById(R.id.SignInUsernamePassword)).getText().toString();
      Log.i(TAG, "onCreate: starting signin");
      Amplify.Auth.signIn(
        username,
        password,
        r -> handler.sendEmptyMessage(1),
        r -> handler.sendEmptyMessage(2)
      );
    });
  }
}
