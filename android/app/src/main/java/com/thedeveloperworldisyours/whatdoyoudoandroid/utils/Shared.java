package com.thedeveloperworldisyours.whatdoyoudoandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;

import java.util.Random;

/**
 * Created by javiergonzalezcabezas on 19/4/15.
 */
public class Shared {

    Activity mActivity;

    public Shared(Activity c) {
        this.mActivity = c;
    }

    public static void onShareEmail(Activity activity, String contactName, boolean win) {
        final String stringShared;
        if (win){
            String arrayWin[] = activity.getResources().getStringArray(R.array.arrayWin);
            Random rand = new Random();
            int randomNum = rand.nextInt((arrayWin.length - 1) + 1) + 0;
            stringShared = arrayWin[randomNum];
        }else {
            String array[] = activity.getResources().getStringArray(R.array.arrayLose);
            Random rand = new Random();
            int randomNum = rand.nextInt((array.length - 1) + 1) + 0;
            stringShared = array[randomNum];
        }




        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide what to do with it.
        intent.putExtra(Intent.EXTRA_SUBJECT,activity.getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, String.format(stringShared, contactName, activity.getString(R.string.app_name) ));
        try {
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.title_email_dialog)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity,
                    activity.getString(R.string.title_email_dialog),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
