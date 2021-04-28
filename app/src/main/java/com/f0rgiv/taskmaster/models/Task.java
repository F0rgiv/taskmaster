package com.f0rgiv.taskmaster.models;

public class Task {
  String title;
  String description;
  String state;

  public Task(String title, String description, String completed) {
    this.title = title;
    this.description = description;
    this.state = completed;
  }
}
