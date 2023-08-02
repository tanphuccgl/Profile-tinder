package com.example.profiletinder;

// Import các thư viện và package cần thiết
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

public class ProfileActivity extends AppCompatActivity implements PurposeDialogFragment.OnPurposeSelectedListener, GenderDialogFragment.OnGenderSelectedListener {

    // Các biến và đối tượng để quản lý danh sách ảnh và các thành phần giao diện khác
    private static final int REQUEST_CODE_SELECT_IMAGE = 100;
    private ImageAdapter imageAdapter;
    private GridViewAdapter adapter;
    private List<String> inputData = new ArrayList<>();
    private List<String> imagePaths = new ArrayList<>();
    private TextView tvPurpose;
    private TextView tvGender;

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
                    imageAdapter.setBitmapAtPosition(bitmap, requestCode); // Đặt bitmap vào vị trí đã chọn trong GridView
                    imageAdapter.notifyDataSetChanged(); // Cập nhật GridView
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
