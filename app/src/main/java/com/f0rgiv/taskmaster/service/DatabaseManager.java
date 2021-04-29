package com.f0rgiv.taskmaster.service;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.f0rgiv.taskmaster.daos.TaskDao;
import com.f0rgiv.taskmaster.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class DatabaseManager  extends RoomDatabase {
  public abstract TaskDao taskDao();
}
