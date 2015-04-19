package com.thedeveloperworldisyours.whatdoyoudoandroid;

import android.app.Application;
import android.graphics.Typeface;

import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Mission;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Node;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;

import library.SQLiteSimple;
import library.util.SimpleDatabaseUtil;

/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class MyApp extends Application {
    public void onCreate()
    {
        super.onCreate();
        initializeTypefaces();
        if (SimpleDatabaseUtil.isFirstApplicationStart(this)) {
            SQLiteSimple databaseSimple = new SQLiteSimple(this, Constants.DATABASE_NAME);
            databaseSimple.create(Node.class);
            databaseSimple.create(Mission.class);
        }
    }

    public static class Fonts
    {
        public static Typeface PENCIL;
    }

    private void initializeTypefaces()
    {

        Fonts.PENCIL = Typeface.createFromAsset(getAssets(), "fonts/pencil.ttf");
    }
}
