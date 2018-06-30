package com.amangarg.todoapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.amangarg.todoapp.R;
import com.amangarg.todoapp.model.Picture;

import java.io.IOException;

public class AddTaskActivity extends AppCompatActivity {

    private static final int SELECT_SINGLE_PICTURE = 101;

    private static final int SELECT_MULTIPLE_PICTURE = 201;

    public static final String IMAGE_TYPE = "image/*";

    private ImageView selectedImagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_new_task);
        setContentView(R.layout.activity_add_task);


        findViewById(R.id.uploadPhotoBtn).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType(IMAGE_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        getString(R.string.select_picture)), SELECT_SINGLE_PICTURE);
            }
        });

        selectedImagePreview = (ImageView) findViewById(R.id.previewIV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            Uri selectedImageUri = data.getData();
            try {
                selectedImagePreview.setImageBitmap(new Picture(selectedImageUri, getContentResolver()).getBitmap());
            } catch (IOException e) {
                Log.e(MainCategoryActivity.class.getSimpleName(), "Failed to load image", e);
            }
        }
    }

}
