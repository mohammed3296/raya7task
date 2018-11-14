package com.mohammedabdullah3296.newsapp.view;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.mohammedabdullah3296.newsapp.R;
import com.mohammedabdullah3296.newsapp.model.database.ArticleContact;
import com.mohammedabdullah3296.newsapp.adapter.ArticleCursorAdapter;

public class FavoritesActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int FAVORITE_LOADER = 7874556;
    ArticleCursorAdapter mCursorAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ListView favoriteListView = (ListView) findViewById(R.id.main_favorites);
        mCursorAdapter = new ArticleCursorAdapter(this, null);
        favoriteListView.setAdapter(mCursorAdapter);


        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
        getLoaderManager().initLoader(FAVORITE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                ArticleContact.FavoriteEntry._ID,
                ArticleContact.FavoriteEntry.TITLE,
                ArticleContact.FavoriteEntry.TIME,
                ArticleContact.FavoriteEntry.IMAGE,
                ArticleContact.FavoriteEntry.URL};
        return new CursorLoader(this,
                ArticleContact.FavoriteEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
