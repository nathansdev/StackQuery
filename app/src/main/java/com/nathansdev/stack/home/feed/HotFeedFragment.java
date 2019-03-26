package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.home.adapter.QuestionsAdapter;

import javax.inject.Inject;

public class HotFeedFragment extends FeedFragment {

    @Inject
    public HotFeedFragment() {

    }

    public static HotFeedFragment newInstance() {
        HotFeedFragment fragment = new HotFeedFragment();
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
