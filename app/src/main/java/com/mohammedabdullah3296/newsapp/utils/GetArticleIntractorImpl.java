package com.mohammedabdullah3296.newsapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.mohammedabdullah3296.newsapp.interfaces.GetNewsDataService;
import com.mohammedabdullah3296.newsapp.model.Article;
import com.mohammedabdullah3296.newsapp.model.ArticlesResponse;
import com.mohammedabdullah3296.newsapp.network.RetrofitInstance;
import com.mohammedabdullah3296.newsapp.presenter.MainContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetArticleIntractorImpl implements MainContract.GetNoticeIntractor {

    Context context;

    public GetArticleIntractorImpl(Context context1) {
        this.context = context1;
    }

    @Override
    public void getNoticeArrayList(final OnFinishedListener onFinishedListener) {
        GetNewsDataService service = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<ArticlesResponse> call = service.getNewsDataData("usa-today", "d80df8fef29b4abb87565d639d7b1d79");
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                onFinishedListener.onFinished((ArrayList<Article>) response.body().getArticles());

            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                //    onFinishedListener.onFailure(t);
                onFinishedListener.onFinished(getArticles(loadJSONFromAsset(context)));
                //Log.e("articles" , getArticles(loadJSONFromAsset(context)).size() + "");
            }
        });

    }

    /**
     * I did it because API does not work
     *
     */

    public String loadJSONFromAsset(Context myContext) {
        String json = null;
        try {
            AssetManager mngr = myContext.getAssets();
            InputStream is = mngr.open("json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.e("JSON", json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<Article> getArticles(String response) {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String title = jsonObject1.getString("title");
                String url = jsonObject1.getString("url");
                String urlToImage = jsonObject1.getString("urlToImage");
                String publishedAt = jsonObject1.getString("publishedAt");
                articles.add(new Article("", title, "", url, urlToImage, "", publishedAt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
