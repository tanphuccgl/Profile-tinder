package com.example.profiletinder;

// Import các thư viện và package cần thiết

import static java.sql.DriverManager.println;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements PurposeDialogFragment.OnPurposeSelectedListener, GenderDialogFragment.OnGenderSelectedListener {

    // Các biến và đối tượng để quản lý danh sách ảnh và các thành phần giao diện khác
    private static final int REQUEST_CODE_SELECT_IMAGE = 100;
    private ImageAdapter imageAdapter;
    private GridViewAdapter adapter;
    private List<String> inputData = new ArrayList<>();
    private List<String> imagePaths = new ArrayList<>();

    private List<ImageModel> list = new ArrayList<>();

    private TextView tvPurpose;
    private TextView tvGender;
    private Button btnSave;
    private EditText editText3;
    private EditText editText;
    private EditText editText1;
    private EditText editText2;


    // Phương thức để mở hộp thoại chọn giới tính
    public void openGenderDialog() {
        GenderDialogFragment genderDialog = new GenderDialogFragment();
        genderDialog.show(getSupportFragmentManager(), "gender_dialog");
    }

    // Ghi đè phương thức onGenderSelected từ interface GenderDialogFragment.OnGenderSelectedListener
    // Được gọi khi người dùng chọn giới tính trong hộp thoại
    @Override
    public void onGenderSelected(String gender) {
        tvGender.setText("Giới tính: " + gender);
    }

    // Phương thức để mở hộp thoại chọn mục đích hẹn hò
    private void openPurposeDialog() {
        PurposeDialogFragment purposeDialog = new PurposeDialogFragment();
        purposeDialog.show(getSupportFragmentManager(), "purpose_dialog");
    }

    // Ghi đè phương thức onPurposeSelected từ interface PurposeDialogFragment.OnPurposeSelectedListener
    // Được gọi khi người dùng chọn mục đích hẹn hò trong hộp thoại
    @Override
    public void onPurposeSelected(String purpose) {
        tvPurpose.setText("Mục đích hẹn hò: " + purpose);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(ProfileActivity.this, "Đã lưu thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        // Tạo các đối tượng để quản lý GridView chứa ảnh
        GridView gridView = findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this, imagePaths);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectImage(position);
            }
        });
        gridView.setAdapter(imageAdapter);

        // Tạo GridView để hiển thị các EditText để nhập thông tin
        GridView gridView2 = findViewById(R.id.gridView2);
        for (int i = 0; i < 9; i++) {
            inputData.add("");
        }
        adapter = new GridViewAdapter(this, inputData);
        gridView2.setAdapter(adapter);


        // Thiết lập sự kiện click cho TextView "Mục đích hẹn hò" và "Giới tính" để mở hộp thoại tương ứng
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

        loadData();
        loadEditTextData();
        list = loadImageList();
        try {

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getBitmap().toString() != "") {
                    imagePaths.add(list.get(i).getKey());

                    byte[] byteArray = Base64.decode(list.get(i).getBitmap(), Base64.DEFAULT);

// Chuyển đổi mảng byte thành Bitmap
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageAdapter.setBitmapAtPosition(decodedBitmap, list.get(i).getRequestCode());
                    imageAdapter.notifyDataSetChanged();
                }

            }
        } catch (NumberFormatException a) {
            Log.d("levi1234", a.toString());
            Toast.makeText(ProfileActivity.this, a.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    private void saveImageList() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String imageListJson = gson.toJson(list);

        editor.putString("image_list1", imageListJson);
        editor.apply();
    }


    private void saveEditTextData() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < 9; i++) {
            String editTextData = inputData.get(i);

            editor.putString("editText" + i, editTextData.toString());
        }

        editor.apply();
    }

    private List<ImageModel> loadImageList() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        String imageListJson = sharedPreferences.getString("image_list1", "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ImageModel>>() {
        }.getType();
        List<ImageModel> imageModelList = gson.fromJson(imageListJson, type);

        if (imageModelList == null) {
            imageModelList = new ArrayList<>();
        }

        return imageModelList;
    }


    private void loadEditTextData() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        for (int i = 0; i < 9; i++) {
            String editTextData = sharedPreferences.getString("editText" + i, "");
            inputData.set(i, editTextData);
        }

        adapter.notifyDataSetChanged();
    }


    private void saveData() {

        String gioithieu = editText.getText().toString();
        String mucdich = tvPurpose.getText().toString();
        String nghenghiep = editText1.getText().toString();
        String truonghoc = editText2.getText().toString();
        String thanhpho = editText3.getText().toString();
        String gender = tvGender.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("gioithieu", gioithieu);
        editor.putString("mucdich", mucdich);
        editor.putString("nghenghiep", nghenghiep);
        editor.putString("truonghoc", truonghoc);
        editor.putString("thanhpho", thanhpho);
        editor.putString("gioitinh", gender);

        editor.apply();
        adapter.updateData(inputData);
        saveEditTextData();
        saveImageList();


    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        String gioithieu = sharedPreferences.getString("gioithieu", "");
        String mucdich = sharedPreferences.getString("mucdich", "");
        String nghenghiep = sharedPreferences.getString("nghenghiep", "");
        String truonghoc = sharedPreferences.getString("truonghoc", "");
        String thanhpho = sharedPreferences.getString("thanhpho", "");
        String gender = sharedPreferences.getString("gioitinh", "");

        // Hiển thị dữ liệu đã lưu lên giao diện
        editText.setText(gioithieu);

        if (mucdich == "") {
            tvPurpose.setText("Mục đích hẹn hò: Chọn");

        } else {
            tvPurpose.setText(mucdich);
        }

        editText1.setText(nghenghiep);
        editText2.setText(truonghoc);
        editText3.setText(thanhpho);
        if (gender == "") {
            tvGender.setText("Giới tính: Chọn");

        } else {
            tvGender.setText(gender);
        }

    }


    // Phương thức để chọn ảnh từ thư viện ảnh của điện thoại
    private void selectImage(int position) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("position", position); // Đặt giá trị position vào intent
        startActivityForResult(intent, position);
    }

    // Ghi đè phương thức onActivityResult để xử lý kết quả trả về khi người dùng chọn ảnh từ thư viện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    // Chuyển đổi URI thành Bitmap để hiển thị
                    String imagePath = getPathFromUri(selectedImageUri);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                    imagePaths.add(imagePath); // Thêm đường dẫn ảnh vào danh sách imagePaths
                    imageAdapter.setBitmapAtPosition(bitmap, requestCode);
                    Log.d("levi123", "??" + bitmap.toString());

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();

// Chuyển đổi mảng byte thành chuỗi Base64 để lưu
                    String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    list.add(new ImageModel(encodedImage, requestCode, imagePath));
                    // Đặt bitmap vào vị trí đã chọn trong GridView
                    imageAdapter.notifyDataSetChanged();
                    // Cập nhật GridView


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Phương thức để lấy đường dẫn thực tế của ảnh từ URI
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

