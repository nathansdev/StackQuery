package com.nathansdev.stack.home.feed;

import android.view.View;

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
}
