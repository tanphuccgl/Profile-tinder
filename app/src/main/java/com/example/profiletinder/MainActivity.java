package com.example.profiletinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tạo một Intent để chuyển hướng từ MainActivity sang ProfileActivity.
        Intent intent = new Intent(this, ProfileActivity.class);

        // Gọi startActivity() để bắt đầu Activity mới (ProfileActivity).
        startActivity(intent);
    }
}
