package com.f0rgiv.taskmaster.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.f0rgiv.taskmaster.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

  @Insert
  void insert(Task task);

  @Query("SELECT * FROM Task")
  List<Task> findAll();

  @Query("SELECT * FROM Task WHERE id = :id")
  Task findById(Long id);

  @Query("SELECT COUNT(*) FROM Task")
  int getCount();
}
