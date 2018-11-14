package com.mohammedabdullah3296.newsapp.model.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohammed Abdullah on 9/30/2017.
 */

public class ArticleContact {

    private ArticleContact() {
    }

    public static final String CONTENT_AUTHORITY = "com.mohammedabdullah3296.newsapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";
    public static final class FavoriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAVORITES);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;
        public final static String TABLE_NAME = "favorites";
        public final static String _ID = BaseColumns._ID;
        public final static String TITLE = "title";
        public final static String IMAGE = "image";
        public final static String TIME = "time";
        public final static String URL = "url";


    }

}
