package com.example.profiletinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import com.example.profiletinder.R;

public class GenderDialogFragment extends DialogFragment {

    private static final String[] PURPOSES = {"Nam", "Nữ"};
    public interface OnGenderSelectedListener {
        void onGenderSelected(String gender);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Chọn giới tính")
                .setItems(PURPOSES, (dialog, which) -> {
                    String selectedPurpose = PURPOSES[which];
                    GenderDialogFragment.OnGenderSelectedListener listener = (GenderDialogFragment.OnGenderSelectedListener) requireActivity();
                    listener.onGenderSelected(selectedPurpose);
                });


        return builder.create();
    }


}
