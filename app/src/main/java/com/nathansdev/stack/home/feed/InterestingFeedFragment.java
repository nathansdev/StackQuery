package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.base.BaseFragment;

import javax.inject.Inject;

public class InterestingFeedFragment extends BaseFragment {

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
}
