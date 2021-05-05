package com.f0rgiv.taskmaster.repository;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CloudTask;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
  static String TAG = "TaskRepository";

  public static void insert(com.amplifyframework.datastore.generated.model.CloudTask cloudTask) {
    Amplify.API.mutate(
      ModelMutation.create(cloudTask),
      response -> Log.i(TAG, "insert: was successful!"),
      response -> Log.i(TAG, "insert: did not work :(")
    );
  }

  public static void findById(String id, TaskCallback tc) {
    Amplify.API.query(
      ModelQuery.get(com.amplifyframework.datastore.generated.model.CloudTask.class, id),
      response -> {
        if (response.getData() == null) return;
        com.amplifyframework.datastore.generated.model.CloudTask cloudTask = response.getData();
        tc.taskCallback(response.getData());
      },
      response -> {
        //get from the local Db
      }
    );
  }

  public static void findAll(TasksCallback tc) {
    List<CloudTask> result = new ArrayList<>();
    Log.i(TAG, "findAll: about to start");
    Amplify.API.query(
      ModelQuery.list(com.amplifyframework.datastore.generated.model.CloudTask.class),
      response -> {
        Log.i(TAG, response.toString());
        if (response.getData() != null) {
          for (CloudTask cloudTask : response.getData()) {
            result.add(cloudTask);
          }
        }
        tc.tasksCallback(result);
      },
      response -> {
        //save to local
        Log.i(TAG, "findAll: failed");
      }
    );
  }

  public interface TasksCallback {
    void tasksCallback(List<CloudTask> cloudTasks);
  }

  public interface TaskCallback {
    void taskCallback(CloudTask cloudTask);
  }
}
