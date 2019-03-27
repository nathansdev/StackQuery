package com.nathansdev.stack.home.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nathansdev.stack.home.adapter.QuestionsAdapter;

import javax.inject.Inject;

public class FeaturedFeedFragment extends FeedFragment {

    @Inject
    public FeaturedFeedFragment() {

    }

    public static FeaturedFeedFragment newInstance() {
        FeaturedFeedFragment fragment = new FeaturedFeedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
        return new QuestionsAdapter();
    }
}
