package com.mohammedabdullah3296.newsapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammedabdullah3296.newsapp.R;
import com.mohammedabdullah3296.newsapp.model.database.ArticleContact;
import com.squareup.picasso.Picasso;


public class ArticleCursorAdapter extends CursorAdapter {

    public ArticleCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView image = (ImageView) view.findViewById(R.id.media_image);
        TextView  title = (TextView) view.findViewById(R.id.primary_text);
        TextView  time = (TextView) view.findViewById(R.id.sub_text);

        int favorite_titleColumnIndex = cursor.getColumnIndex(ArticleContact.FavoriteEntry.TITLE);
        int favorite_posterColumnIndex = cursor.getColumnIndex(ArticleContact.FavoriteEntry.IMAGE);
        int tititi = cursor.getColumnIndex(ArticleContact.FavoriteEntry.TIME);
        int uuurl = cursor.getColumnIndex(ArticleContact.FavoriteEntry.URL);

        String titleString = cursor.getString(favorite_titleColumnIndex);
        String timeString = cursor.getString(tititi);
        String imageString = cursor.getString(favorite_posterColumnIndex);
        String urlString = cursor.getString(uuurl);

        title.setText(titleString);
        time.setText(timeString);

        Picasso.get().load(imageString).placeholder(R.drawable.placeholder).into(image);


    }
}