package com.f0rgiv.taskmaster.models;

public class Task {
  String title;
  String description;
  State state;

  public enum State {NEW, ASSIGNED, INPROGRESS, COMPLETE}

  public Task(String title, String description, State state) {
    this.title = title;
    this.description = description;
    this.state = state;
  }
}
