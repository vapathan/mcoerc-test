package com.ass.mcoerctest.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.net.URLEncoder;

public class CommunicationHelper {

    public static void textMessage(Context context, String phoneNumber) {
        //open  Text Message
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        context.startActivity(smsIntent);
    }


    public static void dailNumber(Context context, String phoneNumber) {
        //open  Dailer
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        context.startActivity(intent);
    }

    public static void whatsApp(Context context, String phoneNumber) {
        //open Whatsapp

        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {

            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode("", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
