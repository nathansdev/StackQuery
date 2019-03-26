package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.home.adapter.QuestionsAdapter;

import javax.inject.Inject;

public class MonthLyFeedFragment extends FeedFragment {

    @Inject
    public MonthLyFeedFragment() {

    }

    public static MonthLyFeedFragment newInstance() {
        MonthLyFeedFragment fragment = new MonthLyFeedFragment();
        return fragment;
    }

    @Override
    protected void setUpView(View view) {
//        super.setUpView(view);
    }

    @Override
    protected void attachPresenter() {

    }

    @Override
    protected QuestionsAdapter getAdapter() {
        return null;
    }
}
