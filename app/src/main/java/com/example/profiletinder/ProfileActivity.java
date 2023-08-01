package com.example.profiletinder;

import static java.sql.DriverManager.println;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements PurposeDialogFragment.OnPurposeSelectedListener ,GenderDialogFragment.OnGenderSelectedListener {

    private static final int REQUEST_CODE_SELECT_IMAGE = 100;

    private ImageAdapter imageAdapter;
    private GridViewAdapter adapter;
    private List<String> inputData = new ArrayList<>();

    private List<String> imagePaths = new ArrayList<>();
    private TextView tvPurpose;
    private TextView tvGender;



    public void openGenderDialog() {
        GenderDialogFragment genderDialog = new GenderDialogFragment();
        genderDialog.show(getSupportFragmentManager(), "gender_dialog");
    }

    @Override
    public void onGenderSelected(String gender) {
        tvGender.setText("Giới tính: " + gender);
    }
    private void openPurposeDialog() {
        PurposeDialogFragment purposeDialog = new PurposeDialogFragment();
        purposeDialog.show(getSupportFragmentManager(), "purpose_dialog");
    }

    @Override
    public void onPurposeSelected(String purpose) {
        tvPurpose.setText("Mục đích hẹn hò: " + purpose);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        GridView gridView = findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this,imagePaths);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectImage(position);
            }
        });
        gridView.setAdapter(imageAdapter);

        GridView gridView2 = findViewById(R.id.gridView2);
        for (int i = 0; i < 9; i++) {
            inputData.add("");
        }
        adapter = new GridViewAdapter(this, inputData);
        gridView2.setAdapter(adapter);


        tvPurpose = findViewById(R.id.tvPurpose);
        tvPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPurposeDialog();
            }
        });

        tvGender = findViewById(R.id.tvGender);
        tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGenderDialog();
            }
        });



    }


    private void selectImage(int position) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("position", position); // Đặt giá trị position vào intent

        startActivityForResult(intent, position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                try {
                    // Chuyển đổi URI thành Bitmap để hiển thị
                    String imagePath = getPathFromUri(selectedImageUri);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                    imagePaths.add(imagePath);
                    imageAdapter.setBitmapAtPosition(bitmap, requestCode);
                    imageAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }
}
