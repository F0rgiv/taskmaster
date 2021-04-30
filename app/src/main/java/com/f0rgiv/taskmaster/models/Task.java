package com.f0rgiv.taskmaster.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
  @PrimaryKey(autoGenerate = true)
  public long id;

  public String title;
  public String description;
  public String state;

  public Task(String title, String description, String state) {
    this.title = title;
    this.description = description;
    this.state = state;
  }
}
