package com.thedeveloperworldisyours.whatdoyoudoandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;

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
    public static void shared(Activity destination, String contactName,boolean win) {
        // If sharing to a contact, put a leading space at the beginning
        if (!contactName.isEmpty()) {
            contactName = " " + contactName;
        }
        Shared.onShareEmail(destination, contactName,win);
    }

    public static void sharedWhatsapp(Activity destination, String stringShared){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, stringShared);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent != null) {
            destination.startActivity(sendIntent);
        } else {
            sendIntent.setPackage("com.google.android.talk");
            if (sendIntent != null) {
                destination.startActivity(sendIntent);
            } else {
                Toast.makeText(destination, R.string.error_no_whatsapp, Toast.LENGTH_SHORT)
                    .show();
            }
        }

    }
}
