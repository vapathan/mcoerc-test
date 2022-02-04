package com.ass.mcoerctest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ass.mcoerctest.models.Test;

import static com.ass.mcoerctest.constants.Constants.CANCEL_START_TEST;
import static com.ass.mcoerctest.constants.Constants.START_TEST;
import static com.ass.mcoerctest.constants.Constants.TEST_KEY;

public class TestStartConfirmDialogFragment extends DialogFragment {

    private Test mTest;

    public interface TestStartConfirmDialogListener {
        void onConfirm(String confirmation, Test test, Context context);
    }

    private TestStartConfirmDialogListener mHost;

    private TestStartConfirmDialogFragment() {

    }

    public static TestStartConfirmDialogFragment getInstance(Test test) {
        TestStartConfirmDialogFragment testStartConfirmDialogFragment = new TestStartConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(TEST_KEY, test);
        testStartConfirmDialogFragment.setArguments(args);
        return testStartConfirmDialogFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mHost = (TestStartConfirmDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTest = getArguments().getParcelable(TEST_KEY);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm");
        builder.setMessage("Select Yes to start this test");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mHost.onConfirm(START_TEST, mTest, getActivity());

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mHost.onConfirm(CANCEL_START_TEST, mTest, getActivity());
            }
        });

        return builder.create();
    }
}
