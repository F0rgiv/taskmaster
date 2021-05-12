package com.f0rgiv.taskmaster.service;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.amplifyframework.core.Amplify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AmplifyS3 {
  static String TAG = "s3stuff";

  public static void saveFileToS3(Context context, File file, String fileKey) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      Amplify.Storage.uploadFile(fileKey, file, r -> {
        Log.i(TAG, "saveFileToS3: Succeeded");
        r.getKey();
      }, r -> Log.e(TAG, "saveFileToS3: failed."));
    } catch (IOException e) {
      Log.e(TAG, "saveFileToS3:"); //e.printStackTrace()
    }
  }

  public static void loadFileFromS3(Context context, String fileKey) {
    Amplify.Storage.downloadFile(
      fileKey,
      new File(context.getFilesDir(), fileKey),
      r -> {

      },
      r -> {
      }
    );
  }
}
