package com.example.profiletinder;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class PurposeDialogFragment extends DialogFragment {

    private static final String[] PURPOSES = {"Tìm bạn đời", "Tìm người yêu", "Kết bạn mới", "Hẹn hò nghiêm túc"};

    public interface OnPurposeSelectedListener {
        void onPurposeSelected(String purpose);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Chọn mục đích hẹn hò")
                .setItems(PURPOSES, (dialog, which) -> {
                    String selectedPurpose = PURPOSES[which];
                    OnPurposeSelectedListener listener = (OnPurposeSelectedListener) requireActivity();
                    listener.onPurposeSelected(selectedPurpose);
                });

        return builder.create();
    }
}
