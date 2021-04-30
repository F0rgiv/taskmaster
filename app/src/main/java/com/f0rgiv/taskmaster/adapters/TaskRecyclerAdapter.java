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
  List<Task> tasks;

  public HandleOnClickAble handleOnClickAble;
  String TAG = "mainActivity";

  public TaskRecyclerAdapter(HandleOnClickAble handleOnClickAble, List<Task> tasks) {
    this.handleOnClickAble = handleOnClickAble;
    this.tasks = tasks;
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
    holder.task = tasks.get(position);
    ((Button) holder.itemView.findViewById(R.id.taskFragmentTaskButton)).setText(holder.task.title);
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
    public Task task;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
