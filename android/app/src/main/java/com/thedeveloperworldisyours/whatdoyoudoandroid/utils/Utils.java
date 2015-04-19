package com.thedeveloperworldisyours.whatdoyoudoandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;

/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class Utils {
    public static String checkLanguaje() {
        if (Locale.getDefault().getLanguage().equals("es")) {
            return "es";
        } else {
            return "en";
        }
    }

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * @param destination
     * @param contactName
     */
    public static void shared(Activity destination, String contactName) {
        // If sharing to a contact, put a leading space at the beginning
        if (!contactName.isEmpty()) {
            contactName = " " + contactName;
        }
        Shared.onShareEmail(destination, contactName);
    }
}
