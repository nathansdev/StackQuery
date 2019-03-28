package com.nathansdev.stack.home.feed;

import com.nathansdev.stack.base.MvpView;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRow;

import java.util.List;

public interface FeedView extends MvpView {
    void onQuestionsLoaded(List<QuestionsAdapterRow> rows);
}
