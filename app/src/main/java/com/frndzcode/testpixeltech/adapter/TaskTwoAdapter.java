package com.frndzcode.testpixeltech.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frndzcode.testpixeltech.activities.TaskOneActivity;
import com.frndzcode.testpixeltech.activities.TaskTwoActivity;
import com.frndzcode.testpixeltech.databinding.ItemTakTwoBinding;
import com.frndzcode.testpixeltech.databinding.ItemTaskOneSingleBinding;
import com.frndzcode.testpixeltech.model.TaskModel;
import com.frndzcode.testpixeltech.model.TaskTwoModel;

import java.util.ArrayList;

public class TaskTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TaskTwoAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    private  Context context;
    private ArrayList<Uri> taskList;

    public TaskTwoAdapter(ArrayList<Uri> taskList, TaskTwoActivity activity) {
        this.taskList = taskList;
        this.context = activity;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskTwoAdapter.TaskViewHolder(ItemTakTwoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //TaskTwoModel model = taskList.get(position);
        Log.e(TAG, "onBindViewHolder: list size => "+taskList.size() );
        setupTask(/*model, */(TaskTwoAdapter.TaskViewHolder) holder,position);
    }

    private void setupTask(/*TaskTwoModel model,*/ TaskViewHolder holder, int position) {
        //show upload photo
        if (taskList.size()>0){
            holder.binding.image.setImageURI(taskList.get(position));
           /* for (int i=0;i<taskList.size();i++) {
                holder.binding.image.setImageURI(taskList.get(i));
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private ItemTakTwoBinding binding;

        public TaskViewHolder(ItemTakTwoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
