package com.thedeveloperworldisyours.whatdoyoudoandroid.dao;

import android.content.Context;

import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Mission;

import library.SQLiteSimpleDAO;

/**
 * Created by javiergonzalezcabezas on 19/4/15.
 */
public class MissionDAO extends SQLiteSimpleDAO<Mission> {

    public MissionDAO(Context context) {
        super(Mission.class, context);
    }
}
