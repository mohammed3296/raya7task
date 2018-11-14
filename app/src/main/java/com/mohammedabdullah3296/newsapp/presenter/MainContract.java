package com.mohammedabdullah3296.newsapp.presenter;

import com.mohammedabdullah3296.newsapp.model.Article;

import java.util.ArrayList;



public interface MainContract {


    interface presenter{

        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();

    }


    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<Article> noticeArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface GetNoticeIntractor {

        interface OnFinishedListener {
            void onFinished(ArrayList<Article> noticeArrayList);
            void onFailure(Throwable t);
        }

        void getNoticeArrayList(OnFinishedListener onFinishedListener);
    }
}
