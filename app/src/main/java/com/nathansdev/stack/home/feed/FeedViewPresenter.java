package com.nathansdev.stack.home.feed;

import com.nathansdev.stack.base.MvpPresenter;
import com.nathansdev.stack.base.MvpView;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRowDataSet;

public interface FeedViewPresenter<V extends MvpView> extends MvpPresenter<V> {
    void init(QuestionsAdapterRowDataSet dataset);

    void loadQuestions();

    void loadNextPage();

    void cleanUp();
}
