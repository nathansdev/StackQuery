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
import com.nathansdev.stack.home.adapter.QuestionsAdapterRowDataSet;
import com.nathansdev.stack.rxevent.RxEventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class FeedFragment extends BaseFragment implements FeedView {

    @BindView(R.id.feeds_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.feeds_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Inject
    RxEventBus eventBus;
    @Inject
    FeedViewPresenter<FeedView> presenter;

    private LinearLayoutManager layoutManager;
    private QuestionsAdapter adapter;
    private QuestionsAdapterRowDataSet dataset;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        presenter.onAttach(this);
        attachPresenter();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void setUpView(View view) {
        adapter = new QuestionsAdapter();
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        adapter.setEventBus(eventBus);
        if (dataset == null) {
            Timber.d("creating new data set");
            dataset = QuestionsAdapterRowDataSet.createWithEmptyData(adapter);
        }
        adapter.setData(dataset);
        recyclerView.setAdapter(adapter);
        presenter.init(dataset);
        presenter.loadQuestions();
    }

    protected abstract void attachPresenter();

    protected abstract QuestionsAdapter getAdapter();

    protected abstract void setRefreshLayout(boolean refresh);
}
