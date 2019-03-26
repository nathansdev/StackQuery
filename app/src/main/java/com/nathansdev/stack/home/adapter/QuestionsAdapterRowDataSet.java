package com.nathansdev.stack.home.adapter;

import android.support.v7.util.SortedList;
import android.support.v7.widget.util.SortedListAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapterRowDataSet extends SortedListAdapterCallback<QuestionsAdapterRow> {

    private SortedList<QuestionsAdapterRow> sortedList;

    /**
     * Creates a {@link SortedList.Callback} that will forward data change events to the provided
     * Adapter.
     *
     * @param adapter The Adapter instance which should receive events from the SortedList.
     */
    public QuestionsAdapterRowDataSet(QuestionsAdapter adapter, ArrayList<QuestionsAdapterRow> data) {
        super(adapter);
        sortedList = new SortedList<>(QuestionsAdapterRow.class, this);
        if (data != null && !data.isEmpty()) {
            sortedList.addAll(data);
        }
    }

    public static QuestionsAdapterRowDataSet createWithInitialData(QuestionsAdapter adapter,
                                                                   ArrayList<QuestionsAdapterRow> data) {
        return new QuestionsAdapterRowDataSet(adapter, data);
    }

    public static QuestionsAdapterRowDataSet createWithEmptyData(QuestionsAdapter adapter) {
        return new QuestionsAdapterRowDataSet(adapter, null);
    }

    @Override
    public int compare(QuestionsAdapterRow o1, QuestionsAdapterRow o2) {
        return o1.compare(o2);
    }

    @Override
    public boolean areContentsTheSame(QuestionsAdapterRow oldItem, QuestionsAdapterRow newItem) {
        return oldItem.areContentsTheSame(newItem);
    }

    @Override
    public boolean areItemsTheSame(QuestionsAdapterRow item1, QuestionsAdapterRow item2) {
        return item1.areItemsTheSame(item2);
    }

    /**
     * Add all item to the sorted list.
     *
     * @param rows adapter row.
     */
    public void addAllRows(List<QuestionsAdapterRow> rows) {
        for (QuestionsAdapterRow row : rows) {
            sortedList.add(row);
        }
    }

    /**
     * Add single item to the sorted list.
     *
     * @param row adapter row.
     */
    public void addRow(QuestionsAdapterRow row) {
        sortedList.add(row);
    }

    /**
     * clear data for the sorted list.
     */
    public void clearDataSet() {
        sortedList.clear();
    }

    /**
     * return the size of sorted list.
     *
     * @return size.
     */
    public int size() {
        return sortedList.size();
    }

    /**
     * verify whether sorted list is empty.
     *
     * @return boolean.
     */
    public boolean isEmpty() {
        return sortedList.size() == 0;
    }

    /**
     * provide the particular item from the list based on pos.
     *
     * @param pos position in the list.
     * @return item.
     */
    public QuestionsAdapterRow get(int pos) {
        return sortedList.get(pos);
    }

    /**
     * Check for load more row type available in sorted list.
     *
     * @return true if present else false.
     */
    public boolean containsLoadMore() {
        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(0).isTypeLoadMore()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove load more row from sorted list.
     */
    public void removeLoadMore() {
        int index = -1;
        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).isTypeLoadMore()) {
                index = i;
            }
        }
        if (index > -1) {
            sortedList.removeItemAt(index);
        }
    }

    /**
     * Remove load more row from sorted list.
     */
    public void removeLoading() {
        int index = -1;
        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).isTypeLoading()) {
                index = i;
            }
        }
        if (index > -1) {
            sortedList.removeItemAt(index);
        }
    }

    /**
     * Remove load more row from sorted list.
     */
    public void removeError() {
        int index = -1;
        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).isTypeError()) {
                index = i;
            }
        }
        if (index > -1) {
            sortedList.removeItemAt(index);
        }
    }
}
