package com.ass.mcoerctest.utilities.ui;

import android.view.View;
import android.widget.ProgressBar;

public class UIHelper {
    public static void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }
}
