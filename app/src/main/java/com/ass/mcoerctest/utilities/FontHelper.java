package com.ass.mcoerctest.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontHelper {

    public static Typeface getTypeFaceKarma(Context context){
        AssetManager assetManager = context.getApplicationContext().getAssets();
        Typeface typeface=Typeface.createFromAsset(assetManager,String.format("fonts/%s","Karma-Regular.ttf"));
        return typeface;

    }
    public static Typeface getOpenSans(Context context){
        AssetManager assetManager = context.getApplicationContext().getAssets();
        Typeface typeface=Typeface.createFromAsset(assetManager,String.format("fonts/%s","OpenSans-Regular.ttf"));
        return typeface;
    }

}
