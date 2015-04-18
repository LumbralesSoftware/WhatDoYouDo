package library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Artemiy Garin
 * Copyright (C) 2013 SQLite Simple Project
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * *
 * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@SuppressWarnings("CanBeFinal")
public class SimplePreferencesHelper {

    private SharedPreferences.Editor preferencesEditor;
    private SharedPreferences sharedPreferences;

    public static final String PREFERENCES_APPLICATION = "SQLiteSimpleDatabaseApplication";
    public static final String IS_FIRST_APPLICATION_START = "SHARED_IS_FIRST_APPLICATION_START";
    public static final String LOCAL_PREFERENCES = "LOCAL";
    public static final String DATABASE_TABLES = "SQLiteSimpleDatabaseTables_%s";
    public static final String DATABASE_QUERIES = "SQLiteSimpleDatabaseQueries_%s";

    private static final String PREFERENCES_DATABASE = "SQLiteSimpleDatabaseHelper";
    private static final String DATABASE_VERSION = "SQLiteSimpleDatabaseVersion_%s";
    private static final String DATABASE_VIRTUAL_TABLE_CREATED = "SQLiteSimpleDatabaseVirtualTableCreated";
    private static final String PREFERENCES_LIST = "List_%s_%s";
    private static final String PREFERENCES_INDEX = "%s_Index";

    @SuppressLint("CommitPrefEdits")
    public SimplePreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_DATABASE,
                Context.MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();
    }

    public void clearAllPreferences(String place, int databaseVersion) {
        boolean isFTSTableCreated = isVirtualTableCreated();

        preferencesEditor.remove(String.format(DATABASE_QUERIES, place));
        preferencesEditor.remove(String.format(PREFERENCES_INDEX, String.format(DATABASE_QUERIES, place)));
        preferencesEditor.remove(String.format(DATABASE_TABLES, place));
        preferencesEditor.remove(String.format(PREFERENCES_INDEX, String.format(DATABASE_TABLES, place)));

        putDatabaseVersion(databaseVersion, place);

        if (isFTSTableCreated) setVirtualTableCreated();

        preferencesEditor.commit();
    }


    private int getNextIndex(String place) {
        return getCurrentIndex(place) + 1;
    }

    private int getCurrentIndex(String place) {
        return sharedPreferences.getInt(String.format(PREFERENCES_INDEX, place), 1);
    }

    public void putList(String place, List<String> entityList) {
        int index = getNextIndex(place);
        for (String entity : entityList) {
            preferencesEditor.putString(String.format(PREFERENCES_LIST, place, index), entity);
            index++;
        }
        preferencesEditor.putInt(String.format(PREFERENCES_INDEX, place), index);
        preferencesEditor.commit();
    }

    public List<String> getList(String place) {
        List<String> resultList = new ArrayList<String>();

        for (int i = 1; i <= getCurrentIndex(place); i++) {
            String savedString = sharedPreferences.getString(String.format(PREFERENCES_LIST, place, i), null);
            if (savedString != null) resultList.add(savedString);
        }

        return resultList;
    }

    public void commit() {
        preferencesEditor.commit();
    }

    public void putDatabaseVersion(int databaseVersion, String sharedPreferencesPlace) {
        preferencesEditor.putInt(String.format(DATABASE_VERSION,
                sharedPreferencesPlace), databaseVersion);
    }

    public int getDatabaseVersion(String sharedPreferencesPlace) {
        return sharedPreferences.getInt(String.format(DATABASE_VERSION,
                sharedPreferencesPlace), SimpleConstants.FIRST_DATABASE_VERSION);
    }

    public boolean isVirtualTableCreated() {
        return sharedPreferences.getBoolean(DATABASE_VIRTUAL_TABLE_CREATED, false);
    }

    public void setVirtualTableCreated() {
        preferencesEditor.putBoolean(DATABASE_VIRTUAL_TABLE_CREATED, true);
    }

    public void setVirtualTableDropped() {
        preferencesEditor.putBoolean(DATABASE_VIRTUAL_TABLE_CREATED, false);
    }

}
