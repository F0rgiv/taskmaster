package com.f0rgiv.taskmaster.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.f0rgiv.taskmaster.R;
import com.f0rgiv.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {
  static List<String> tasks = new ArrayList<>();

  static {
    tasks.add("task1");
    tasks.add("task2");
    tasks.add("task3");
    tasks.add("task4");
    tasks.add("task5");
    tasks.add("task6");
    tasks.add("task7");
    tasks.add("task8");
    tasks.add("task9");
    tasks.add("task10");
    tasks.add("task11");
    tasks.add("task12");
    tasks.add("task13");
    tasks.add("task14");
    tasks.add("task15");
  }

  public HandleOnClickAble handleOnClickAble;
  String TAG = "mainActivity";

  public TaskRecyclerAdapter(HandleOnClickAble handleOnClickAble) {
    this.handleOnClickAble = handleOnClickAble;
  }

  @NonNull
  @Override
  public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View fragment = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.fragment_task, parent, false);
    return new TaskViewHolder(fragment);
  }

  @Override
  public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    String taskName = tasks.get(position);
    holder.taskName = taskName;
    ((Button) holder.itemView.findViewById(R.id.taskFragmentTaskButton)).setText(taskName);
    holder.itemView.findViewById(R.id.taskFragmentTaskButton).setOnClickListener(view -> handleOnClickAble.handleClickOnTask(holder));
  }

  @Override
  public int getItemCount() {
    return tasks.size();
  }

  public static abstract class HandleOnClickTask {
    abstract void handleClickOnTask(TaskViewHolder taskViewHolder);
    List<Task> tasks = new ArrayList<>();
  }

  public interface HandleOnClickAble {
    void handleClickOnTask(TaskViewHolder taskViewHolder);
  }


  public static class TaskViewHolder extends RecyclerView.ViewHolder {
    public String taskName;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
