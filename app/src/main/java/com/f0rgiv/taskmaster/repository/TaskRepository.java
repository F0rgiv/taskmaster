package com.f0rgiv.taskmaster.repository;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CloudTask;
import com.f0rgiv.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
  static String TAG = "TaskRepository";

  public static void insert(Task task) {
    CloudTask cloudTask = CloudTask.builder()
      .name(task.title)
      .state(task.state)
      .description(task.description)
      .build();
    Amplify.API.mutate(
      ModelMutation.create(cloudTask),
      response -> Log.i(TAG, "insert: was successful!"),
      response -> Log.i(TAG, "insert: did not work :(")
    );
  }

  public static void findById(String id, TaskCallback tc) {
    Amplify.API.query(
      ModelQuery.get(CloudTask.class, id),
      response -> {
        if (response.getData() == null) return;
        CloudTask task = response.getData();
        tc.taskCallback(new Task(task.getId(), task.getName(), task.getDescription(), task.getState()));
      },
      response -> {
        //get from the local Db

      }
    );
  }

  public static void findAll(TasksCallback tc) {
    List<Task> result = new ArrayList<>();
    final boolean[] ready = {false};
    Log.i(TAG, "findAll: about to start");
    Amplify.API.query(
      ModelQuery.list(CloudTask.class),
      response -> {
        Log.i(TAG, response.toString());
        if (response.getData() != null) {
          for (CloudTask task : response.getData()) {
            result.add(new Task(task.getId(), task.getName(), task.getDescription(), task.getState()));
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
    void tasksCallback(List<Task> tasks);
  }

  public interface TaskCallback {
    void taskCallback(Task task);
  }
}
