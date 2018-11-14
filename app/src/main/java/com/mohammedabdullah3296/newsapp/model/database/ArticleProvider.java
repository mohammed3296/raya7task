package com.mohammedabdullah3296.newsapp.model.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


public class ArticleProvider extends ContentProvider {
    public static final String LOG_TAG = ArticleProvider.class.getSimpleName();
    private static final int FAVORITES = 100;
    private static final int FAVORITE_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ArticleContact.CONTENT_AUTHORITY, ArticleContact.PATH_FAVORITES, FAVORITES);
        sUriMatcher.addURI(ArticleContact.CONTENT_AUTHORITY, ArticleContact.PATH_FAVORITES + "/#", FAVORITE_ID);
    }

    private ArticleDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ArticleDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                cursor = database.query(ArticleContact.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case FAVORITE_ID:
                selection = ArticleContact.FavoriteEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ArticleContact.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                return ArticleContact.FavoriteEntry.CONTENT_LIST_TYPE;
            case FAVORITE_ID:
                return ArticleContact.FavoriteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                return insertFavorite(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }


    private Uri insertFavorite(Uri uri, ContentValues values) {
        try {
            SQLiteDatabase database = mDbHelper.getWritableDatabase();

            long id = database.insert(ArticleContact.FavoriteEntry.TABLE_NAME, null, values);
            if (id == -1) {
                Log.e(LOG_TAG, "Failed to insert row for " + uri);
                return null;
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);

        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {


        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                rowsDeleted = database.delete(ArticleContact.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVORITE_ID:
                selection = ArticleContact.FavoriteEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ArticleContact.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                return updateFavorite(uri, contentValues, selection, selectionArgs);
            case FAVORITE_ID:
                selection = ArticleContact.FavoriteEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateFavorite(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateFavorite(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        try {

            if (values.size() == 0) {
                return 0;
            }

            SQLiteDatabase database = mDbHelper.getWritableDatabase();

            int rowsUpdated = database.update(ArticleContact.FavoriteEntry.TABLE_NAME, values, selection, selectionArgs);
            if (rowsUpdated != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;

        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

}
