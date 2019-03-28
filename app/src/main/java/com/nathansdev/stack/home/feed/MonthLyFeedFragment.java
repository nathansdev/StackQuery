package com.nathansdev.stack.home.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.home.adapter.QuestionsAdapter;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRow;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRowDataSet;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;
/**
 * Feeds Fragment with filtertype "month".
 */
public class MonthLyFeedFragment extends FeedFragment implements FeedView {

    @Inject
    public MonthLyFeedFragment() {

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
    protected void attachPresenter() {
        presenter.onAttach(this);
    }

    @Override
    public void onQuestionsLoaded(List<QuestionsAdapterRow> rows) {
        Timber.d("onQuestionsLoaded");
        adapter.notifyDataSetChanged();
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
    protected void setUpView(View view) {
        super.setUpView(view);
        Timber.d("setUpView");
        presenter.init(dataset, filterType);
        loadFeeds();
    }

    @Override
    protected QuestionsAdapterRowDataSet getAdapterDataSet(QuestionsAdapter adapter) {
        return QuestionsAdapterRowDataSet.createWithEmptyData(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cleanUp();
        presenter.onDetach();
    }

    @Override
    protected QuestionsAdapter getAdapter() {
        return new QuestionsAdapter();
    }
}
