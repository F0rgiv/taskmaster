//package com.f0rgiv.taskmaster.daos;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//import com.f0rgiv.taskmaster.models.CloudTask;
//
//import java.util.List;
//
//@Dao
//public interface TaskDao {
//
//  @Insert
//  void insert(CloudTask cloudTask);
//
//  @Query("SELECT * FROM CloudTask")
//  List<CloudTask> findAll();
//
//  @Query("SELECT * FROM CloudTask WHERE id = :id")
//  CloudTask findById(Long id);
//
//  @Query("SELECT COUNT(*) FROM CloudTask")
//  int getCount();
//}
