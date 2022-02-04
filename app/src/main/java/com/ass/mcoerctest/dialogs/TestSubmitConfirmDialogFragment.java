package com.ass.mcoerctest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TestSubmitConfirmDialogFragment extends DialogFragment {


    public interface ConfirmDialogListener {
        void onConfirm(String confirmation);
    }

    private ConfirmDialogListener mHost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mHost = (ConfirmDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm");
        builder.setMessage("Do you really want to submit the test?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mHost.onConfirm("Yes");

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mHost.onConfirm("No");
            }
        });

        return builder.create();
    }
}
