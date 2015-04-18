package library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import garin.artemiy.sqlitesimple.library.model.FTSModel;
import garin.artemiy.sqlitesimple.library.util.SimpleConstants;
import garin.artemiy.sqlitesimple.library.util.SimpleDatabaseUtil;
import garin.artemiy.sqlitesimple.library.util.SimplePreferencesHelper;

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
public class SQLiteSimpleFTS {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TABLE_CATEGORY = "tableCategory";
    private static final String COLUMN_DATA = "data";
    private static final String MATCH_FORMAT = "%s:%s*";

    private static SQLiteDatabase database = null;
    private boolean useTablesCategory;
    private String tableName;
    private SimplePreferencesHelper preferencesUtil;

    @SuppressWarnings("unused")
    public SQLiteSimpleFTS(Context context, boolean useTablesCategory) {
        this.useTablesCategory = useTablesCategory;

        SQLiteSimpleHelper simpleHelper = new SQLiteSimpleHelper(context, SimplePreferencesHelper.LOCAL_PREFERENCES,
                new SimplePreferencesHelper(context).getDatabaseVersion(SimplePreferencesHelper.LOCAL_PREFERENCES), null, true);

        if (database == null || !database.isOpen()) database = simpleHelper.getWritableDatabase();
        tableName = SimpleDatabaseUtil.getFTSTableName(context);

        createTableIfNotExist(context);
    }

    @SuppressWarnings("unused")
    public void dropTable() {
        database.execSQL(String.format(SimpleConstants.FTS_DROP_VIRTUAL_TABLE, tableName));
        preferencesUtil.setVirtualTableDropped();
        preferencesUtil.commit();
    }

    public void createTableIfNotExist(Context context) {
        preferencesUtil = new SimplePreferencesHelper(context);
        if (!preferencesUtil.isVirtualTableCreated()) {

            String createVirtualFTSTable;
            if (useTablesCategory)
                createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE_WITH_CATEGORY,
                        tableName, COLUMN_ID, COLUMN_TABLE_CATEGORY, COLUMN_DATA);
            else createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE,
                    tableName, COLUMN_ID, COLUMN_DATA);

            database.execSQL(createVirtualFTSTable);

            preferencesUtil.setVirtualTableCreated();
            preferencesUtil.commit();
        }
    }

    @SuppressWarnings("unused")
    public void create(FTSModel ftsModel) {
        if (!TextUtils.isEmpty(ftsModel.getData())) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, ftsModel.getId());
            contentValues.put(COLUMN_DATA, ftsModel.getData().toLowerCase());

            if (useTablesCategory) contentValues.put(COLUMN_TABLE_CATEGORY, ftsModel.getTableCategory());

            database.insert(tableName, null, contentValues);
        }
    }

    @SuppressWarnings("unused")
    public void createAll(List<FTSModel> ftsModels) {
        for (FTSModel ftsModel : ftsModels) create(ftsModel);
    }

    @SuppressWarnings("unused")
    public List<FTSModel> search(String incomingQuery, boolean resultDesc) {
        List<FTSModel> ftsModels = new ArrayList<FTSModel>();

        if (incomingQuery.length() >= SimpleConstants.FTS_QUERY_MINIMUM_LENGTH) {
            String order;
            if (resultDesc) order = SimpleConstants.DESC;
            else order = SimpleConstants.ASC;

            String format = String.format(SimpleConstants.FTS_SQL_FORMAT, tableName, tableName, COLUMN_ID, order);
            Cursor cursor = database.rawQuery(format, new String[]{String.format(MATCH_FORMAT, COLUMN_DATA, incomingQuery)});
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                FTSModel ftsModel;
                if (useTablesCategory)
                    ftsModel = new FTSModel(
                            cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_TABLE_CATEGORY)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));
                else ftsModel = new FTSModel(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));

                cursor.moveToNext();
                ftsModels.add(ftsModel);
            }

            cursor.close();
        }

        return ftsModels;
    }

}
