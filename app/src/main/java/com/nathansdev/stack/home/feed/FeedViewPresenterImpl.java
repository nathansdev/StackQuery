package com.nathansdev.stack.home.feed;

import com.nathansdev.stack.base.BasePresenter;

import javax.inject.Inject;

public class FeedViewPresenterImpl<V extends FeedView> extends BasePresenter<V> implements FeedViewPresenter<V> {
    @Inject
    FeedViewPresenterImpl() {

    }

    @Override
    public void loadQuestions() {

    }
}
