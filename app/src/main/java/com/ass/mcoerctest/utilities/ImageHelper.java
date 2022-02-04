package com.ass.mcoerctest.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageHelper {

    public static final int IMAGE_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;

    public static void loadCircularImageWithGlide(Context context, RequestOptions requestOptions, Drawable image, ImageView imageView) {
        GlideApp.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new CircleCrop())
                .into(imageView);
    }

    public static void loadCircularImageWithGlide(Context context, RequestOptions requestOptions, String imageUrl, ImageView imageView) {
        GlideApp.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new CircleCrop())
                .into(imageView);
    }

    public static void loadCircularImageWithGlide(Context context, RequestOptions requestOptions, File file, ImageView imageView) {
        GlideApp.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(file)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new CircleCrop())
                .into(imageView);
    }

    public static void showPictureDialog(Context context) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // choosePhotoFromGallary();
                                break;
                            case 1:
                                //  takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public static void captureImage(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = getImageFile(activity);
            } catch (IOException e) {

            }
            if (imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(activity, "com.ass.pudhari", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                activity.startActivityForResult(cameraIntent, IMAGE_REQUEST_CODE);
            }
        }
    }

    public static File getImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageName, ".jpg", storageDir);

        return imageFile;
    }

    public static void pickFromGallery(Activity activity) {
        //Create an Intent with action as ACTION_GET_CONTENT
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};

        activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public static Drawable setTint(Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;

    }

    public static void setTint(ImageView imageView, int color) {
        imageView.setColorFilter(color);
    }

    public static Bitmap getImageFromAssetsFile(Context mContext, String fileName) {
        Bitmap image = null;
        AssetManager am = mContext.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
