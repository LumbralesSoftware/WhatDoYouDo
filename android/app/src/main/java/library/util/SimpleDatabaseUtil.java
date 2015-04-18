package library.util;

import android.content.Context;
import android.content.SharedPreferences;
import garin.artemiy.sqlitesimple.library.annotations.Column;
import garin.artemiy.sqlitesimple.library.annotations.Table;

import java.lang.reflect.Field;
import java.util.Date;

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
@SuppressWarnings("AndroidLintSdCardPath")
public class SimpleDatabaseUtil {

    /**
     * Settings
     */
    private static final String DB_FORMAT = ".db";
    private static final String DATABASE_PATH = "/data/data/%s/databases/%s";

    /**
     * Use only by static reference
     */
    private SimpleDatabaseUtil() {
    }

    /**
     * @param field - Java field
     * @return - return column name {@link garin.artemiy.sqlitesimple.library.annotations.Column}, or field name if null
     */
    public static String getColumnName(Field field) {
        Column annotationColumn = field.getAnnotation(Column.class);
        String column = null;
        if (annotationColumn != null)
            if (annotationColumn.name().equals(SimpleConstants.EMPTY)) column = field.getName();
            else column = annotationColumn.name();

        return column;
    }

    /**
     * @param tClass - Java class
     * @return - return table name {@link garin.artemiy.sqlitesimple.library.annotations.Table}, or class name if null
     */
    public static String getTableName(Class<?> tClass) {
        Table annotationTable = tClass.getAnnotation(Table.class);
        String table = tClass.getSimpleName();
        if (annotationTable != null && !annotationTable.name().equals(SimpleConstants.EMPTY))
            table = annotationTable.name();

        return table;
    }

    /**
     * @param field            - Java field
     * @param columnAnnotation - {@link garin.artemiy.sqlitesimple.library.annotations.Column}
     * @return - return correct SQL type by parsing Java field
     */
    public static String getSQLType(Field field, Column columnAnnotation) {
        String type;
        if (columnAnnotation.type() != null && !columnAnnotation.type().equals(SimpleConstants.AUTO_ASSIGN)
                && !columnAnnotation.type().equals(SimpleConstants.EMPTY))
            type = columnAnnotation.type();
        else {
            Class<?> fieldType = field.getType();

            if (fieldType.isAssignableFrom(Long.class) || fieldType.isAssignableFrom(long.class))
                type = ColumnType.INTEGER;
            else if (fieldType.isAssignableFrom(String.class))
                type = ColumnType.TEXT;
            else if ((fieldType.isAssignableFrom(Integer.class) || fieldType.isAssignableFrom(int.class)))
                type = ColumnType.INTEGER;
            else if ((fieldType.isAssignableFrom(Byte[].class) || fieldType.isAssignableFrom(byte[].class)))
                type = ColumnType.BLOB;
            else if ((fieldType.isAssignableFrom(Double.class) || fieldType.isAssignableFrom(double.class)))
                type = ColumnType.REAL;
            else if ((fieldType.isAssignableFrom(Float.class) || fieldType.isAssignableFrom(float.class)))
                type = ColumnType.REAL;
            else if ((fieldType.isAssignableFrom(Short.class) || fieldType.isAssignableFrom(short.class)))
                type = ColumnType.INTEGER;
            else if (fieldType.isAssignableFrom(Byte.class) || fieldType.isAssignableFrom(byte.class))
                type = ColumnType.INTEGER;
            else if (fieldType.isAssignableFrom(Boolean.class) || fieldType.isAssignableFrom(boolean.class))
                type = ColumnType.NUMERIC;
            else if (fieldType.isAssignableFrom(Date.class))
                type = ColumnType.INTEGER;
            else throw new RuntimeException("Unknown variable type:" + fieldType);
        }

        return type;
    }

    /**
     * @param context      - see {@link android.content.Context}
     * @param databaseName - any database name
     * @return - full database path
     */
    public static String getFullDatabasePath(Context context, String databaseName) {
        return String.format(DATABASE_PATH, context.getPackageName(), databaseName);
    }

    public static String getFullDatabaseName(String databaseName, Context context, boolean isFTS) {
        if (databaseName == null) {
            if (isFTS)
                return String.format(SimpleConstants.FORMAT_GLUED_FTS, context.getPackageName(), DB_FORMAT).
                        replace(SimpleConstants.DOT, SimpleConstants.UNDERSCORE).toUpperCase();
            else return String.format(SimpleConstants.FORMAT_GLUED, context.getPackageName(), DB_FORMAT).
                    replace(SimpleConstants.DOT, SimpleConstants.UNDERSCORE).toUpperCase();
        } else return databaseName;
    }

    /**
     * @param context - see {@link android.content.Context}
     * @return - Parse package and return generated FTS table name
     */
    public static String getFTSTableName(Context context) {
        return String.format(SimpleConstants.FTS_SQL_TABLE_NAME, context.getPackageName()).
                replace(SimpleConstants.DOT, SimpleConstants.UNDERSCORE).toUpperCase();
    }

    @SuppressWarnings("unused")
    public static boolean isFirstApplicationStart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (SimplePreferencesHelper.PREFERENCES_APPLICATION, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SimplePreferencesHelper.IS_FIRST_APPLICATION_START, true)) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(SimplePreferencesHelper.IS_FIRST_APPLICATION_START, false);
            sharedPreferencesEditor.commit();
            return true;
        } else return false;
    }

    @SuppressWarnings("unused")
    public static boolean isFirstStartOnAppVersion(Context context, int appVersionCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (SimplePreferencesHelper.PREFERENCES_APPLICATION, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(
                String.format(SimpleConstants.FORMAT_SHARED_IS_FIRST_APPLICATION_START, appVersionCode), true)) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(
                    String.format(SimpleConstants.FORMAT_SHARED_IS_FIRST_APPLICATION_START, appVersionCode), false);
            sharedPreferencesEditor.commit();
            return true;
        } else return false;
    }

}
