package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.home.adapter.QuestionsAdapter;

import javax.inject.Inject;

public class InterestingFeedFragment extends FeedFragment {

    @Inject
    public InterestingFeedFragment() {

    }

    public static InterestingFeedFragment newInstance() {
        InterestingFeedFragment fragment = new InterestingFeedFragment();
        return fragment;
    }

    @Override
    protected void setUpView(View view) {

    }

    @Override
    protected void attachPresenter() {

    }

    @Override
    protected QuestionsAdapter getAdapter() {
        return new QuestionsAdapter();
    }

    @Override
    protected void setRefreshLayout(boolean refresh) {

    }

    @Override
    public void onQuestionsLoaded() {

    }
}
