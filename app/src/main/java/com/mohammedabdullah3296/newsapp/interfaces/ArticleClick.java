package com.mohammedabdullah3296.newsapp.interfaces;

import android.view.View;

import com.mohammedabdullah3296.newsapp.model.Article;

public interface ArticleClick {
    void onListItemClick(Article clickedItemIndex);
    void onArticleFavorate(View v, int position);
}
