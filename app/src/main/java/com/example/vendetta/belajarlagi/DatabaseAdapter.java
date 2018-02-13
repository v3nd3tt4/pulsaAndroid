package com.example.vendetta.belajarlagi;

/**
 * Created by vendetta on 13/02/18.
 */

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAdapter extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "db_pulsa.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}