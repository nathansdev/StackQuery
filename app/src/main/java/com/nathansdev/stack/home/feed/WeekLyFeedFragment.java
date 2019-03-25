package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.base.BaseFragment;

import javax.inject.Inject;

public class WeekLyFeedFragment extends BaseFragment {

    @Inject
    public WeekLyFeedFragment() {

    }

    public static WeekLyFeedFragment newInstance() {
        WeekLyFeedFragment fragment = new WeekLyFeedFragment();
        return fragment;
    }

    @Override
    protected void setUpView(View view) {

    }
}
