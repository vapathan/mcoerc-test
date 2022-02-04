package com.ass.mcoerctest.utilities;

import android.content.Context;
import android.util.TypedValue;

public class DimensionHelper {

    public static int getDIP(Context context, int value) {
        int dip = 0;
        dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
        return dip;
    }
}
