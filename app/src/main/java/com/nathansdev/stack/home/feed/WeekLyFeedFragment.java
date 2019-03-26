package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.home.adapter.QuestionsAdapter;

import javax.inject.Inject;

public class WeekLyFeedFragment extends FeedFragment {

    @Inject
    public WeekLyFeedFragment() {

    }

    public static WeekLyFeedFragment newInstance() {
        WeekLyFeedFragment fragment = new WeekLyFeedFragment();
        return fragment;
    }

    @Override
    protected void setUpView(View view) {
        super.setUpView(view);
    }

    @Override
    protected void attachPresenter() {

    }

    @Override
    protected QuestionsAdapter getAdapter() {
        return null;
    }

    @Override
    protected void setRefreshLayout(boolean refresh) {

    }

    @Override
    public void onQuestionsLoaded() {

    }
}
