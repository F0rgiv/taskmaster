package com.f0rgiv.taskmaster.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.CloudTask;
import com.f0rgiv.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {
  List<CloudTask> cloudTasks;

  public HandleOnClickAble handleOnClickAble;
  String TAG = "mainActivity";

  public TaskRecyclerAdapter(HandleOnClickAble handleOnClickAble, List<CloudTask> cloudTasks) {
    this.handleOnClickAble = handleOnClickAble;
    this.cloudTasks = cloudTasks;
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
    holder.cloudTask = cloudTasks.get(position);
    ((Button) holder.itemView.findViewById(R.id.taskFragmentTaskButton)).setText(holder.cloudTask.getName());
    holder.itemView.findViewById(R.id.taskFragmentTaskButton).setOnClickListener(view -> handleOnClickAble.handleClickOnTask(holder));
  }

  @Override
  public int getItemCount() {
    return cloudTasks.size();
  }

  public static abstract class HandleOnClickTask {
    abstract void handleClickOnTask(TaskViewHolder taskViewHolder);
    List<CloudTask> cloudTasks = new ArrayList<>();
  }

  public interface HandleOnClickAble {
    void handleClickOnTask(TaskViewHolder taskViewHolder);
  }


  public static class TaskViewHolder extends RecyclerView.ViewHolder {
    public CloudTask cloudTask;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
