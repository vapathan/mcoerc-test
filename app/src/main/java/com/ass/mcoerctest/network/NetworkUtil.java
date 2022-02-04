package com.ass.mcoerctest.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {


    public static boolean hasNetworkAccess(Context context) {

        //check network connectivity
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static int checkNetworkType(Context context) {

        //Check Connection type
        //0 - Not connected to the network;
        //1 - connected to the mobile device network;
        //2 - connected to the wifi network

        try {
            if (hasNetworkAccess(context)) {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
                NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo.isConnected()) {
                    return 1;
                } else if (wifiInfo.isConnected()) {
                    return 2;
                } else {
                    return 0;
                }

            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


}
