package com.nathansdev.stack.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nathansdev.stack.rxevent.RxEventBus;

public class QuestionsAdapter extends RecyclerView.Adapter {
    private RxEventBus eventBus;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setEventBus(RxEventBus eventBus) {
        this.eventBus = eventBus;
    }
}
