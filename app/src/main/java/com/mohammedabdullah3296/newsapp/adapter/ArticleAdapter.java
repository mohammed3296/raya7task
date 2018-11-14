package com.mohammedabdullah3296.newsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.mohammedabdullah3296.newsapp.interfaces.ArticleClick;
import com.mohammedabdullah3296.newsapp.model.Article;
import com.mohammedabdullah3296.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    public List<Article> data = Collections.emptyList();
    Article current;
    int currentPos = 0;
    private ArticleClick lOnClickListener;
    public ArticleAdapter(ArticleClick listener) {
        lOnClickListener = listener;
    }
    public void setArticleData(List<Article> recipesIn, Context context) {
        data = recipesIn;
        mContext = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.article_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MyHolder viewHolder = new MyHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.title.setText(current.getTitle());
        Picasso.get().load(current.getUrlToImage()).placeholder(R.drawable.placeholder).into(myHolder.image);
        myHolder.time.setText(current.getPublishedAt());
        myHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lOnClickListener.onArticleFavorate(v, position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView title, time;
        ImageView image;
        ImageButton  imageButton ;

        public MyHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.media_image);
            imageButton = (ImageButton) itemView.findViewById(R.id.action_button_1);
            title = (TextView) itemView.findViewById(R.id.primary_text);
            time = (TextView) itemView.findViewById(R.id.sub_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(data.get(clickedPosition));
        }
    }
    public void clear() {
        final int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }
}
