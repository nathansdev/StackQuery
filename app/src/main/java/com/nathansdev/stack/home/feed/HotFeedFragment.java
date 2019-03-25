package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.base.BaseFragment;

import javax.inject.Inject;

public class HotFeedFragment extends BaseFragment {

    @Inject
    public HotFeedFragment() {

    }

    public static HotFeedFragment newInstance() {
        HotFeedFragment fragment = new HotFeedFragment();
        return fragment;
    }

    @Override
    protected void setUpView(View view) {

    }
}
