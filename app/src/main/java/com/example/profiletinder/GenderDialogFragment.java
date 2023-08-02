package com.example.profiletinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import com.example.profiletinder.R;

// Lớp GenderDialogFragment là một Fragment trong Android dùng để hiển thị hộp thoại (Dialog) cho người dùng chọn giới tính (Nam hoặc Nữ).

public class GenderDialogFragment extends DialogFragment {

    // Mảng PURPOSES chứa các giá trị để hiển thị trong danh sách lựa chọn giới tính trong hộp thoại.
    private static final String[] PURPOSES = {"Nam", "Nữ"};

    // Interface OnGenderSelectedListener dùng để giao tiếp từ Fragment tới Activity chứa nó khi người dùng chọn giới tính.
    public interface OnGenderSelectedListener {
        void onGenderSelected(String gender);
    }

    // Phương thức onCreateDialog(Bundle savedInstanceState) được ghi đè để tạo hộp thoại AlertDialog và trả về nó.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Tạo một đối tượng AlertDialog.Builder để xây dựng hộp thoại.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Đặt tiêu đề cho hộp thoại.
        builder.setTitle("Chọn giới tính")

                // Đặt danh sách các mục chọn vào hộp thoại, sử dụng mảng PURPOSES và xử lý sự kiện chọn mục bằng một lambda expression.
                .setItems(PURPOSES, (dialog, which) -> {
                    // Lấy giá trị của mục được chọn từ mảng PURPOSES.
                    String selectedPurpose = PURPOSES[which];

                    // Lấy interface OnGenderSelectedListener từ Activity chứa Fragment và gọi phương thức onGenderSelected() với giá trị giới tính đã chọn.
                    GenderDialogFragment.OnGenderSelectedListener listener = (GenderDialogFragment.OnGenderSelectedListener) requireActivity();
                    listener.onGenderSelected(selectedPurpose);
                });

        // Trả về hộp thoại AlertDialog đã tạo.
        return builder.create();
    }
}
