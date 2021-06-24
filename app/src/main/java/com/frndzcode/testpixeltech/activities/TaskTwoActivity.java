package com.frndzcode.testpixeltech.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.frndzcode.testpixeltech.R;
import com.frndzcode.testpixeltech.adapter.TaskTwoAdapter;
import com.frndzcode.testpixeltech.databinding.ActivityTaskTwoBinding;
import com.frndzcode.testpixeltech.helpers.CaptureImage;
import com.frndzcode.testpixeltech.interfaces.ImageCapturedListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class TaskTwoActivity extends AppCompatActivity implements ImageCapturedListener {
    private static final String TAG = TaskTwoActivity.class.getSimpleName();
    private static final int PICK_IMAGE_REQUEST = 0x02;
    private TaskTwoActivity activity;
    private ActivityTaskTwoBinding binding;

    private TaskTwoAdapter adapter;
    private ArrayList<Uri> imageList = new ArrayList<Uri>();

    private CaptureImage captureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        bindActivity();
    }

    private void bindActivity() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Task Two");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.recycler.setLayoutManager(new GridLayoutManager(this,4));
//        adapter = new TaskTwoAdapter(imageList, activity);
//        binding.recycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upload_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.upload:
                uploadPhoto();
                break;
        }
        return true;
    }

    private void uploadPhoto() {
        captureImage = new CaptureImage(this, String.valueOf(System.currentTimeMillis()), 512, true);
        captureImage.beginCapture();
    }

    @Override
    public void imageCaptured(Uri fileUri) {
        File f = new File(getCacheDir(), System.currentTimeMillis() + ".jpg");
        try {
            InputStream is = getContentResolver().openInputStream(fileUri);
            OutputStream output = new FileOutputStream(f);
            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;
            while ((read = is.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            output.flush();
            output.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageList.add(Uri.fromFile(f));
    }

    @Override
    public void imageProcessed(String base64Image) {
        //when parse/call bese64 value
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: data : " + data);
            switch (requestCode) {
                case PICK_IMAGE_REQUEST:
                    if (data != null && data.getClipData() != null) {

                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            imageList.add(uri);
                            adapter = new TaskTwoAdapter(imageList, activity);
                            binding.recycler.setAdapter(adapter);
                        }
                    } else
                        captureImage.resolveImage(null);
                    break;
            }
        }
    }


}