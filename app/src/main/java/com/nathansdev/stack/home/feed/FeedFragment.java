package com.nathansdev.stack.home.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nathansdev.stack.R;
import com.nathansdev.stack.base.BaseFragment;
import com.nathansdev.stack.home.adapter.QuestionsAdapter;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRow;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRowDataSet;
import com.nathansdev.stack.rxevent.RxEventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class FeedFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.feeds_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.feeds_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Inject
    RxEventBus eventBus;

    private LinearLayoutManager layoutManager;
    protected QuestionsAdapter adapter;
    protected QuestionsAdapterRowDataSet dataset;

    private RecyclerView.OnScrollListener onScrollListener =
            new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItem > -1) {
                        QuestionsAdapterRow row = dataset.get(lastVisibleItem);
                        if (row.isTypeLoadMore()) {
                                loadNextPage();
                        }
                    }
                }
            };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        attachPresenter();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void setUpView(View view) {
        recyclerView.removeOnScrollListener(onScrollListener);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (adapter != null) {
            adapter.handleDestroy();
        }
        adapter = getAdapter();
        if (dataset != null) {
            dataset.handleDestroy();
        }
        if (adapter != null) {
            if (dataset == null) {
                Timber.d("creating new data set");
                dataset = getAdapterDataSet(adapter);
            }
            adapter.setData(dataset);
            adapter.setEventBus(eventBus);
        }
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(onScrollListener);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnabled(false);
    }

    @Override
    public void showLoading() {
        setRefreshLayout(false);
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onDestroyView() {
        if (adapter != null) {
            adapter.handleDestroy();
            adapter = null;
        }
        if (recyclerView != null) {
            recyclerView.removeOnScrollListener(onScrollListener);
        }
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(null);
        }
        super.onDestroyView();
    }

    private void setRefreshLayout(boolean refresh) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(refresh);
        }
    }

    protected abstract void attachPresenter();

    protected abstract void loadNextPage();

    protected abstract void loadFeeds();

    protected abstract QuestionsAdapter getAdapter();

    protected abstract QuestionsAdapterRowDataSet getAdapterDataSet(QuestionsAdapter adapter);
}
