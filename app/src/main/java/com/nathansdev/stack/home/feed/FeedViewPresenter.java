package com.nathansdev.stack.home.feed;

import com.nathansdev.stack.base.MvpPresenter;
import com.nathansdev.stack.base.MvpView;

public interface FeedViewPresenter<V extends MvpView> extends MvpPresenter<V> {
    void loadQuestions();
}
