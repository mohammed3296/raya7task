package com.mohammedabdullah3296.newsapp.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ArticleDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = ArticleDbHelper.class.getSimpleName();


    private static final String DATABASE_NAME = "favoritesDB.db";

    private static final int DATABASE_VERSION = 1;

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + ArticleContact.FavoriteEntry.TABLE_NAME + " ("
                + ArticleContact.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ArticleContact.FavoriteEntry.TITLE + " TEXT NOT NULL  , "
                + ArticleContact.FavoriteEntry.IMAGE + " TEXT, "
                + ArticleContact.FavoriteEntry.TIME + " TEXT , "
                + ArticleContact.FavoriteEntry.URL + " TEXT );";

        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
