package com.thedeveloperworldisyours.whatdoyoudoandroid;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class MyApp extends Application {
    public void onCreate()
    {
        super.onCreate();
        initializeTypefaces();
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
