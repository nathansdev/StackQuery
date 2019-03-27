package com.nathansdev.stack.home.profile;

import android.view.View;

import com.nathansdev.stack.home.adapter.QuestionsAdapter;
import com.nathansdev.stack.home.feed.FeedFragment;

import javax.inject.Inject;

public class MyFeedFragment extends FeedFragment {

    @Inject
    public MyFeedFragment() {

    }

    public static MyFeedFragment newInstance() {
        MyFeedFragment fragment = new MyFeedFragment();
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
