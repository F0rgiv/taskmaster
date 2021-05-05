package com.f0rgiv.taskmaster.repository;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CloudTeam;

import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
  static String TAG = "TeamRepository";

  public static void insert(CloudTeam team) {
    Amplify.API.mutate(
      ModelMutation.create(team),
      response -> Log.i(TAG, "insert: was successful!"),
      response -> Log.i(TAG, "insert: did not work :(")
    );
  }

  public static void findById(String id, TeamCallback tc) {
    Amplify.API.query(
      ModelQuery.get(CloudTeam.class, id),
      response -> {
        if (response.getData() == null) {
          Log.i(TAG, "findById: none found about to create");
          CloudTeam.builder().name("Team1").build();
          CloudTeam.builder().name("Team2").build();
          CloudTeam.builder().name("Team3").build();
        }
        else tc.teamCallback(response.getData());
      },
      response -> {
        //get from the local Db

      }
    );
  }

  public static void findByName(String name, TeamCallback tc) {
    Log.i(TAG, String.format("Checking for: %s",name));
    Amplify.API.query(
      ModelQuery.list(CloudTeam.class),
      response -> {
        for (CloudTeam team : response.getData()) {
          Log.i(TAG, String.format("checking: %s", team.getName()));
          if(team.getName().contentEquals(name)){
            Log.i(TAG, "findByName: found match");
            tc.teamCallback(team);
          }
        }
      },
      response -> {}
    );
  }

  public static void findAll(TeamsCallback tc) {
    List<CloudTeam> result = new ArrayList<>();
    Log.i(TAG, "findAll: about to start");
    Amplify.API.query(
      ModelQuery.list(CloudTeam.class),
      response -> {
        Log.i(TAG, "findById: about to check if we need to create");
        if (false) {
          Log.i(TAG, "findById: none found about to create");
          insert(CloudTeam.builder().name("Team1").build());
          insert(CloudTeam.builder().name("Team2").build());
          insert(CloudTeam.builder().name("Team3").build());
        }
        else {
          Log.i(TAG, "findById: teams already exist to create");
          for (CloudTeam team : response.getData()) {
            result.add(team);
          }
          tc.teamsCallback(result);

        }
      },
      response -> {
        //save to local
        Log.i(TAG, "findAll: failed");
      }
    );
  }

  public interface TeamsCallback {
    void teamsCallback(List<CloudTeam> teams);
  }

  public interface TeamCallback {
    void teamCallback(CloudTeam team);
  }
}
