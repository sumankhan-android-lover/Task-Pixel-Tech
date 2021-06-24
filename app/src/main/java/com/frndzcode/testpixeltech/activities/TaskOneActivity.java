package com.frndzcode.testpixeltech.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.frndzcode.testpixeltech.adapter.TaskOneAdapter;
import com.frndzcode.testpixeltech.databinding.ActivityTaskOneBinding;
import com.frndzcode.testpixeltech.model.TaskModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Response;

public class TaskOneActivity extends AppCompatActivity {
    private static final String TAG = TaskOneActivity.class.getSimpleName();
    private TaskOneActivity activity;
    private ActivityTaskOneBinding binding;
    private TaskOneAdapter adapter;
    private ArrayList<TaskModel> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskOneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        bindActivity();
        getActionApi();
    }

    private void bindActivity() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Task One");

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskOneAdapter(taskList, activity);
        binding.recycler.setAdapter(adapter);
    }

    private void getActionApi() {
        String url = "https://www.mlm.pixelsoftwares.com/vynkpay_store/api/dashboard";
        AndroidNetworking.get(url)
                .addHeaders("Token","96a02d9459756c7fb552363c0b43ab8e")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponseAndJSONObject(new OkHttpResponseAndJSONObjectRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        parseResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onErrorResponse: " + anError.getErrorDetail());
                    }
                });
    }

    private void parseResponse(JSONObject response) {
        try {
            if (response.getInt("status")==200){
                JSONObject product = response.getJSONObject("products");
                JSONArray dealsOfTheDays = product.getJSONArray("dealsOfTheDay");
                for (int i = 0;i<dealsOfTheDays.length();i++){
                    JSONObject object = dealsOfTheDays.getJSONObject(i);
                    TaskModel model = new TaskModel();
                    model.setId(object.getString("id"));
                    model.setQty(object.getString("qty"));
                    model.setMain_price(object.getString("main_price"));
                    model.setProduct_name(object.getString("product_name"));
                    model.setName(object.getString("name"));
                    taskList.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}