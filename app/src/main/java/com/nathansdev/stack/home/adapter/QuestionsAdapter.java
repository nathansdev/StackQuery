package com.nathansdev.stack.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathansdev.stack.R;
import com.nathansdev.stack.rxevent.RxEventBus;
import com.nathansdev.stack.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsAdapterVH> {
    private static final int VIEW_TYPE_QUESTION = 0x01;
    private static final int VIEW_TYPE_LOADING = 0x02;
    private static final int VIEW_TYPE_LOADMORE = 0x03;

    private RxEventBus eventBus;
    private ArrayList<QuestionsAdapterVH> viewHolders = new ArrayList<>();
    private QuestionsAdapterRowDataSet dataSet;

    @NonNull
    @Override
    public QuestionsAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        QuestionsAdapterVH holder = null;
        switch (viewType) {
            case VIEW_TYPE_QUESTION:
                holder = QuestionsAdapterVH.ofQuestion(parent);
                break;
            case VIEW_TYPE_LOADMORE:
                holder = QuestionsAdapterVH.ofLoadMore(parent);
                break;
            default:
                break;
        }
        if (holder != null) {
            viewHolders.add(holder);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(QuestionsAdapterVH holder, int position) {
        holder.bind(dataSet.get(position), eventBus);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        QuestionsAdapterRow row = dataSet.get(position);
        if (row.isTypeQuestion()) {
            return VIEW_TYPE_QUESTION;
        } else if (row.isTypeLoadMore()) {
            return VIEW_TYPE_LOADMORE;
        }
        return RecyclerView.NO_POSITION;
    }

    /**
     * Set data to the list.
     *
     * @param adapterRowDataSet group adapter item/row.
     */
    public void setData(QuestionsAdapterRowDataSet adapterRowDataSet) {
        this.dataSet = adapterRowDataSet;
    }

    public void setEventBus(RxEventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Releasing resources.
     */
    public void handleDestroy() {
        dataSet.clearDataSet();
        for (QuestionsAdapterVH holder : viewHolders) {
            holder.destroy();
        }
        viewHolders.clear();
    }

    /**
     * abstract class for feeds related view holder.
     */
    abstract static class QuestionsAdapterVH extends RecyclerView.ViewHolder {

        /**
         * Initialize constructor with item view.
         *
         * @param itemView item view / row view.
         */
        QuestionsAdapterVH(View itemView) {
            super(itemView);
        }

        static QuestionsAdapterVH ofQuestion(ViewGroup parent) {
            return QuestionVH.create(parent);
        }

        static QuestionsAdapterVH ofLoadMore(ViewGroup parent) {
            return LoadMoreVH.create(parent);
        }

        abstract void bind(QuestionsAdapterRow row, RxEventBus eventBus);

        abstract void destroy();
    }


    /**
     * Binds groups name and avatar in the list.
     */
    public static class QuestionVH extends QuestionsAdapterVH {

        @BindView(R.id.tv_owner_name)
        TextView ownerName;
        @BindView(R.id.tv_question_posted_at)
        TextView timeStamp;
        @BindView(R.id.tv_question_title)
        TextView title;
        @BindView(R.id.iv_owner_profile)
        ImageView avatar;

        /**
         * Initialize constructor with item view.
         *
         * @param itemView item view / row view.
         */
        QuestionVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Inflate the user skillLayout to view.
         *
         * @param parent view group.
         * @return users view holder.
         */
        public static QuestionVH create(ViewGroup parent) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_question, parent, false);
            return new QuestionVH(rootView);
        }

        @Override
        void bind(final QuestionsAdapterRow row, final RxEventBus eventBus) {
            Utils.loadRoundImage(itemView.getContext(), row.imageUrl(), avatar);
            ownerName.setText(row.name());
            title.setText(row.title());
            timeStamp.setText(String.valueOf(row.timeStamp()));
        }

        @Override
        void destroy() {

        }
    }

    /**
     * Binds groups name and avatar in the list.
     */
    public static class LoadMoreVH extends QuestionsAdapterVH {

        /**
         * Initialize constructor with item view.
         *
         * @param itemView item view / row view.
         */
        LoadMoreVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Inflate the user skillLayout to view.
         *
         * @param parent view group.
         * @return users view holder.
         */
        public static LoadMoreVH create(ViewGroup parent) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_load_more, parent, false);
            return new LoadMoreVH(rootView);
        }

        @Override
        void bind(QuestionsAdapterRow row, final RxEventBus eventBus) {

        }

        @Override
        void destroy() {

        }
    }
}
