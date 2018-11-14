package com.mohammedabdullah3296.newsapp.interfaces;

import com.mohammedabdullah3296.newsapp.model.ArticlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetNewsDataService {

    @GET("v2/everything")
    Call<ArticlesResponse> getNewsDataData(@Query("sources") String sources , @Query("apiKey") String apiKey );

}