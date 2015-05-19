package com.thedeveloperworldisyours.whatdoyoudoandroid;

import android.app.Application;
import android.graphics.Typeface;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Mission;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Node;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;

import java.util.AbstractMap;
import java.util.HashMap;

import library.SQLiteSimple;
import library.util.SimpleDatabaseUtil;

/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class MyApp extends Application {

    public static GoogleAnalytics analytics;
    private static final String PROPERTY_ID = "UA-55573153-2";

    //Logging TAG
    private static final String TAG = "MyApp";

    public static int GENERAL_TRACKER = 0;
    public HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }



    public void onCreate() {
        super.onCreate();
        initializeTypefaces();
        if (SimpleDatabaseUtil.isFirstApplicationStart(this)) {
            SQLiteSimple databaseSimple = new SQLiteSimple(this, Constants.DATABASE_NAME);
            //databaseSimple.create(Node.class);
            //databaseSimple.create(Mission.class);
            //databaseSimple.rawQuery("CREATE TABLE 'Mission' ('_id' VARCHAR(64) PRIMARY KEY  NOT NULL  UNIQUE , 'name' VARCHAR(200) NOT NULL , 'text' TEXT NOT NULL , 'beginning' VARCHAR(64) NOT NULL )");
            //databaseSimple.rawQuery("CREATE TABLE 'Node' ('_id' VARCHAR(64) PRIMARY KEY  NOT NULL  UNIQUE , 'name' VARCHAR(200) NOT NULL , 'text' TEXT NOT NULL , 'answer_1' VARCHAR(200), 'answer_2' VARCHAR(200), 'status' INTEGER, 'node_1' VARCHAR(64), 'node_2' VARCHAR(64), 'mission' VARCHAR(64) NOT NULL )");
        }
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);


    }


    public static class Fonts {
        public static Typeface PENCIL;
    }

    private void initializeTypefaces() {

        Fonts.PENCIL = Typeface.createFromAsset(getAssets(), "fonts/pencil.ttf");
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.global_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

}
