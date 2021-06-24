package com.frndzcode.testpixeltech.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frndzcode.testpixeltech.activities.TaskOneActivity;
import com.frndzcode.testpixeltech.databinding.ItemTaskOneSingleBinding;
import com.frndzcode.testpixeltech.model.TaskModel;

import java.util.ArrayList;

public class TaskOneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TaskOneAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    private  Context context;
    private ArrayList<TaskModel> taskList;

    public TaskOneAdapter(ArrayList<TaskModel> taskList, TaskOneActivity activity) {
        this.taskList = taskList;
        this.context = activity;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskOneAdapter.TaskViewHolder(ItemTaskOneSingleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TaskModel model = taskList.get(position);
        Log.e(TAG, "onBindViewHolder: list size => "+taskList.size() );
        setupTask(model, (TaskOneAdapter.TaskViewHolder) holder);
    }

    private void setupTask(TaskModel model, TaskViewHolder holder) {
        holder.binding.name.setText(model.getName());
        holder.binding.productName.setText(model.getProduct_name());
        holder.binding.mainPrice.setText("Rs. "+model.getMain_price());
        holder.binding.quantity.setText("Quantity : "+model.getQty());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private ItemTaskOneSingleBinding binding;

        public TaskViewHolder(ItemTaskOneSingleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
