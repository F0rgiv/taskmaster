package com.f0rgiv.taskmaster.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AmplifyS3 {
  static String TAG = "s3stuff";

  public static void saveFileToS3(File file, String fileKey) {
    Amplify.Storage.uploadFile(
      fileKey, file,
      r -> Log.i(TAG, "saveFileToS3: Succeeded"),
      r -> Log.e(TAG, "saveFileToS3: failed."));
  }

  public static void loadFileFromS3(Context context, String fileKey, FileLoadCallback cb) {
    Log.i(TAG, "loadFileFromS3: about to load file");
    File file = new File(context.getFilesDir(), fileKey);
    Amplify.Storage.downloadFile(
      fileKey,
      file,
      r -> cb.fileLoadCallback(BitmapFactory.decodeFile(r.getFile().getPath())),
      r -> Log.i(TAG, r.toString())
    );
  }

  public interface FileLoadCallback {
    void fileLoadCallback(Bitmap bitmap);
  }
}
