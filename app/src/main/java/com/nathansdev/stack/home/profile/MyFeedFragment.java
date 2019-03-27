package com.nathansdev.stack.home.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.home.adapter.QuestionsAdapter;
import com.nathansdev.stack.home.feed.FeedFragment;
import com.nathansdev.stack.home.feed.FeedView;
import com.nathansdev.stack.home.feed.FeedViewPresenter;

import javax.inject.Inject;

public class MyFeedFragment extends FeedFragment implements FeedView {

    @Inject
    public MyFeedFragment() {

    }

    @Inject
    FeedViewPresenter<FeedView> presenter;

    public static MyFeedFragment newInstance() {
        MyFeedFragment fragment = new MyFeedFragment();
        return fragment;
    }

    public void loadQuestions() {
        presenter.loadQuestions();
    }

    private String filterType = "activity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterType = getArguments().getString(AppConstants.ARG_FILTER_TYPE);
        }
    }

    @Override
    protected void setUpView(View view) {
        super.setUpView(view);
        presenter.init(dataset, filterType);
    }

    @Override
    protected void loadNextPage() {
        presenter.loadNextPage();
    }

    @Override
    protected void attachPresenter() {
        presenter.onAttach(this);
    }

    @Override
    protected QuestionsAdapter getAdapter() {
        return new QuestionsAdapter();
    }

    @Override
    protected void loadFeeds() {
        presenter.loadQuestions();
    }

    @Override
    public void onQuestionsLoaded() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cleanUp();
        presenter.onDetach();
    }
}
