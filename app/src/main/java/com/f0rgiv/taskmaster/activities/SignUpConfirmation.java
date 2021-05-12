package com.f0rgiv.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.f0rgiv.taskmaster.R;

public class SignUpConfirmation extends AppCompatActivity {
String TAG = "signup";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_confirm_sign_up);

    String username = getIntent().getStringExtra("username");

    findViewById(R.id.SignUpConfirmationButton).setOnClickListener(v -> {
        Amplify.Auth.confirmSignUp(
          username,
          ((EditText) findViewById(R.id.SignUpConfirmationCode)).getText().toString(),
          r -> {
            Log.i(TAG, "onCreate: confirmed");
            startActivity(new Intent(SignUpConfirmation.this, SignIn.class));
          },
          r -> {
            Toast.makeText(SignUpConfirmation.this, "confirmation code failed", Toast.LENGTH_LONG);
          }
        );
      }
    );
  }
}
