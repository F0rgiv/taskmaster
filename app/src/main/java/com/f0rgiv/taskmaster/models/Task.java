package com.f0rgiv.taskmaster.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Task {
  @PrimaryKey()
  @NonNull
  public String id;

  public String title;
  public String description;
  public String state;

  @Ignore
  public Task(String title, String description, String state) {
    this.title = title;
    this.description = description;
    this.state = state;
  }


  public Task(String id, String title, String description, String state) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.state = state;
  }

  @NonNull
  @Override
  public String toString() {
    return this.title;
  }
}
