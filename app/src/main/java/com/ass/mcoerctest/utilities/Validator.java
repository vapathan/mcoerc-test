package com.ass.mcoerctest.utilities;

import android.text.TextUtils;

public class Validator {

    public static boolean isValidText(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return true;
    }

    public static boolean isValidInteger(String text) {
        if (TextUtils.isEmpty(text) || !TextUtils.isDigitsOnly(text)) {
            return false;
        }
        return true;
    }
}
