package com.thedeveloperworldisyours.whatdoyoudoandroid.dao;

import android.content.Context;

import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Node;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;

import library.SQLiteSimpleDAO;

/**
 * Created by javiergonzalezcabezas on 19/4/15.
 */
public class NodeDAO extends SQLiteSimpleDAO<Node> {

    public NodeDAO(Context context) {
        super(Node.class, context, Constants.DATABASE_NAME);
    }
}
