package com.example.profiletinder;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

// Lớp PurposeDialogFragment là một Fragment trong Android dùng để hiển thị hộp thoại (Dialog) cho người dùng chọn mục đích hẹn hò.

public class PurposeDialogFragment extends DialogFragment {

    // Mảng PURPOSES chứa các mục đích hẹn hò để hiển thị trong danh sách lựa chọn trong hộp thoại.
    private static final String[] PURPOSES = {"Tìm bạn đời", "Tìm người yêu", "Kết bạn mới", "Hẹn hò nghiêm túc"};

    // Interface OnPurposeSelectedListener dùng để giao tiếp từ Fragment tới Activity chứa nó khi người dùng chọn mục đích hẹn hò.
    public interface OnPurposeSelectedListener {
        void onPurposeSelected(String purpose);
    }

    // Phương thức onCreateDialog(Bundle savedInstanceState) được ghi đè để tạo hộp thoại AlertDialog và trả về nó.
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Tạo một đối tượng AlertDialog.Builder để xây dựng hộp thoại.
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        // Đặt tiêu đề cho hộp thoại.
        builder.setTitle("Chọn mục đích hẹn hò")

                // Đặt danh sách các mục chọn vào hộp thoại, sử dụng mảng PURPOSES và xử lý sự kiện chọn mục bằng một lambda expression.
                .setItems(PURPOSES, (dialog, which) -> {
                    // Lấy giá trị của mục được chọn từ mảng PURPOSES.
                    String selectedPurpose = PURPOSES[which];

                    // Lấy interface OnPurposeSelectedListener từ Activity chứa Fragment và gọi phương thức onPurposeSelected() với giá trị mục đích hẹn hò đã chọn.
                    OnPurposeSelectedListener listener = (OnPurposeSelectedListener) requireActivity();
                    listener.onPurposeSelected(selectedPurpose);
                });

        // Trả về hộp thoại AlertDialog đã tạo.
        return builder.create();
    }
}
