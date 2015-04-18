package library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import library.util.SimpleConstants;
import library.util.SimpleDatabaseUtil;
import library.util.SimplePreferencesHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "WeakerAccess", "CanBeFinal"})
public class SQLiteSimpleHelper extends SQLiteOpenHelper {

    private String assetsDatabaseName;
    private Context context;
    private SimplePreferencesHelper sharedPreferencesUtil;
    private String sharedPreferencesPlace;
    private static final Object writableObjectLock = new Object();
    private static final Object readableObjectLock = new Object();

    /**
     * @param context                - see {@link android.content.Context}
     * @param sharedPreferencesPlace - see {@link library.util.SimpleConstants}
     * @param databaseVersion        - see {@link android.database.sqlite.SQLiteOpenHelper}
     * @param assetsDatabaseName     - Load assets or internal (if null) sqlite database if need
     * @param isFTS                  - see {@link library.SQLiteSimpleFTS}
     */
    public SQLiteSimpleHelper(Context context, String sharedPreferencesPlace,
                              int databaseVersion, String assetsDatabaseName, boolean isFTS) {
        super(context, SimpleDatabaseUtil.getFullDatabaseName(assetsDatabaseName, context, isFTS), null, databaseVersion);
        this.assetsDatabaseName = assetsDatabaseName;
        this.context = context;
        this.sharedPreferencesPlace = sharedPreferencesPlace;
        sharedPreferencesUtil = new SimplePreferencesHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        List<String> sqlQueries = sharedPreferencesUtil.getList(
                String.format(SimplePreferencesHelper.DATABASE_QUERIES, sharedPreferencesPlace));
        if (sqlQueries != null) for (String sqlQuery : sqlQueries)
            sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        List<String> tables = sharedPreferencesUtil.getList(
                String.format(SimplePreferencesHelper.DATABASE_TABLES, SimplePreferencesHelper.LOCAL_PREFERENCES));
        if (tables != null) for (String table : tables)
            sqLiteDatabase.execSQL(String.format(SimpleConstants.FORMAT_TWINS,
                    SimpleConstants.SQL_DROP_TABLE_IF_EXISTS, table));

        onCreate(sqLiteDatabase);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        synchronized (writableObjectLock) {
            checkDatabaseFromAssets();
            if (assetsDatabaseName == null) return super.getWritableDatabase();
            else return SQLiteDatabase.openDatabase(SimpleDatabaseUtil.
                    getFullDatabasePath(context, assetsDatabaseName), null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        synchronized (readableObjectLock) {
            checkDatabaseFromAssets();
            if (assetsDatabaseName == null) return super.getReadableDatabase();
            else return SQLiteDatabase.openDatabase(SimpleDatabaseUtil.
                    getFullDatabasePath(context, assetsDatabaseName), null, SQLiteDatabase.OPEN_READONLY);
        }
    }

    private void checkDatabaseFromAssets() {
        if (assetsDatabaseName != null && !isDatabaseExist()) {
            super.getWritableDatabase(); // create empty database
            super.close();
            copyDatabaseFromAssets();
        }
    }

    private void copyDatabaseFromAssets() {
        try {
            InputStream inputStream = context.getAssets().open(assetsDatabaseName);
            OutputStream outputStream = new FileOutputStream(SimpleDatabaseUtil.getFullDatabasePath(context,
                    assetsDatabaseName));

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) outputStream.write(buffer, 0, length);

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDatabaseExist() {
        return new File(SimpleDatabaseUtil.getFullDatabasePath(context, assetsDatabaseName)).exists();
    }

}
