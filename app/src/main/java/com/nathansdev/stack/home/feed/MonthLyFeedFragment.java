package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.base.BaseFragment;

import javax.inject.Inject;

public class MonthLyFeedFragment extends BaseFragment {

    @Inject
    public MonthLyFeedFragment() {

    }

    public static MonthLyFeedFragment newInstance() {
        MonthLyFeedFragment fragment = new MonthLyFeedFragment();
        return fragment;
    }

    @Override
    protected void setUpView(View view) {

    }
}
