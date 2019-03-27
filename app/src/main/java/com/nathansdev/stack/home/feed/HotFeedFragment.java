package com.nathansdev.stack.home.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.home.adapter.QuestionsAdapter;

import javax.inject.Inject;

public class HotFeedFragment extends FeedFragment implements FeedView {

    @Inject
    public HotFeedFragment() {

    }

    @Inject
    FeedViewPresenter<FeedView> presenter;

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
        loadFeeds();
    }

    @Override
    protected void loadNextPage() {
        presenter.loadNextPage();
    }

    @Override
    protected void loadFeeds() {
        presenter.loadQuestions();
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
    public void onQuestionsLoaded() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cleanUp();
        presenter.onDetach();
    }
}
