package com.mohammedabdullah3296.newsapp.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mohammedabdullah3296.newsapp.R;
import com.mohammedabdullah3296.newsapp.adapter.ArticleAdapter;
import com.mohammedabdullah3296.newsapp.interfaces.ArticleClick;
import com.mohammedabdullah3296.newsapp.model.Article;
import com.mohammedabdullah3296.newsapp.model.database.ArticleContact;
import com.mohammedabdullah3296.newsapp.presenter.MainContract;
import com.mohammedabdullah3296.newsapp.presenter.MainPresenterImpl;
import com.mohammedabdullah3296.newsapp.utils.GetArticleIntractorImpl;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements MainContract.MainView,
        ArticleClick {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MainContract.presenter presenter;
    private ArrayList<Article> articles;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            initializeToolbarAndRecyclerView();
            initProgressBar();

            presenter = new MainPresenterImpl(this, new GetArticleIntractorImpl(this));
            presenter.requestDataFromServer();
        }
        else {
            findViewById(R.id.date).setVisibility(View.GONE);
            Toast.makeText(this, "Error: No Internet Connection ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeToolbarAndRecyclerView() {
        recyclerView = findViewById(R.id.main_news);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initProgressBar() {
        progressBar = findViewById(R.id.loading_indicator);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setDataToRecyclerView(ArrayList<Article> articleArrayList) {

        ArticleAdapter adapter = new ArticleAdapter(this);
        adapter.setArticleData(articleArrayList, this);
        recyclerView.setAdapter(adapter);
        articles = articleArrayList;
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(NewsActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   presenter.onDestroy();
    }

    @Override
    public void onListItemClick(Article clickedItemIndex) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedItemIndex.getUrl()));
        startActivity(intent);

    }

    @Override
    public void onArticleFavorate(View v, int position) {
        Article article = articles.get(position);
        if (Exists(article.getTitle())) {
            deleteFavorite(article.getTitle());
        } else {
            saveFavorite(article.getUrlToImage(), article.getPublishedAt(), article.getUrl(), article.getTitle());
        }

    }

    private void saveFavorite(String image, String time, String url, String title) {
        ContentValues values = new ContentValues();
        values.put(ArticleContact.FavoriteEntry.TITLE, title);
        values.put(ArticleContact.FavoriteEntry.TIME, time);
        values.put(ArticleContact.FavoriteEntry.URL, url);
        values.put(ArticleContact.FavoriteEntry.IMAGE, image);
        Uri newUri = getContentResolver().insert(ArticleContact.FavoriteEntry.CONTENT_URI, values);
        if (newUri == null) {
            Toast.makeText(this, "Adding failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Adding Successful",
                    Toast.LENGTH_SHORT).show();
        }

    }


    public boolean Exists(String article_title) {
        String selection = ArticleContact.FavoriteEntry.TITLE + " = ?";
        String[] selectionArgs = {article_title};
        Cursor count = getContentResolver().query(ArticleContact.FavoriteEntry.CONTENT_URI,
                null, selection, selectionArgs, null);
        if (count == null || count.getCount() < 1) {
            return false;
        } else {
            return true;
        }
    }

    private void deleteFavorite(String title) {
        String selection = ArticleContact.FavoriteEntry.TITLE + " = ?";
        String[] selectionArgs = {title};
        int rowsDeleted = getContentResolver().delete(ArticleContact.FavoriteEntry.CONTENT_URI, selection, selectionArgs);

        // Show a toast message depending on whether or not the delete was successful.
        if (rowsDeleted == 0) {
            Toast.makeText(this, "Deleting Failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Deleting successful",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
       startActivity(new Intent(this , FavoritesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}


































































































