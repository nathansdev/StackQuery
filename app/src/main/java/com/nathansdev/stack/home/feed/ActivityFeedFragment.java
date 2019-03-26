package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.home.adapter.QuestionsAdapter;

import javax.inject.Inject;

public class ActivityFeedFragment extends FeedFragment {

    @Inject
    public ActivityFeedFragment() {

    }

    public static ActivityFeedFragment newInstance() {
        ActivityFeedFragment fragment = new ActivityFeedFragment();
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
}
