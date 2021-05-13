package com.f0rgiv.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.f0rgiv.taskmaster.R;

public class SignUp extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    String TAG = "signup";

    findViewById(R.id.SignUpButton).setOnClickListener(v -> {
      Log.i(TAG, "onCreate: about to signup");
      String username = ((EditText) findViewById(R.id.signUpEditTextUsername)).getText().toString();
      String password = ((EditText) findViewById(R.id.signUpEditTextPassword)).getText().toString();
      AuthSignUpOptions options = AuthSignUpOptions.builder()
        .userAttribute(AuthUserAttributeKey.email(), username)
        .build();
      Amplify.Auth.signUp(
        username,
        password,
        options,
        r -> {
          Log.i(TAG, "onCreate: sign up success");
          Intent intent = new Intent(SignUp.this, SignUpConfirmation.class);
          intent.putExtra("username", username);
          startActivity(intent);
        },
        r -> Log.i(TAG, "signup failure: " + r.toString())
      );
    });
  }
}
