package com.ass.mcoerctest.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ScreenShotHelper {

    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        //Find the screen dimensions to create bitmap in the same size.
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    public static String saveBitmap(Bitmap b, String strFileName) {
        FileOutputStream fos = null;

        File sd = Environment.getExternalStorageDirectory();
        File directory = new File(sd.getAbsolutePath() + "/OnlineExamPic/");
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }


        File file = new File(directory, strFileName);
        try {
            fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 90, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return file.getAbsolutePath();
    }


    public static void captureAndSaveScreenshot(Activity activity) {
        String imageFileName = UUID.randomUUID().toString() + ".jpg";
        String filePath = ScreenShotHelper.saveBitmap(ScreenShotHelper.takeScreenShot(activity), imageFileName);
        shareImage(activity, filePath);
    }

    private static void shareImage(Context context, String imagePath) {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");

        share.setType("image/*");

        // Make sure you put example png image named myImage.png in your
        // directory

        File imageFileToShare = new File(imagePath);

        // Uri uri = Uri.fromFile(imageFileToShare);
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(share, "Share Image!"));
    }


}
