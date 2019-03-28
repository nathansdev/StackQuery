package com.nathansdev.stack.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathansdev.stack.R;
import com.nathansdev.stack.rxevent.AppEvents;
import com.nathansdev.stack.rxevent.RxEventBus;
import com.nathansdev.stack.utils.Utils;
import com.nathansdev.stack.view.TagView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A RecyclerView adapter with different view type to display questions and loading .
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsAdapterVH> {
    private static final int VIEW_TYPE_QUESTION = 0x01;
    private static final int VIEW_TYPE_LOADMORE = 0x02;
    private static final int VIEW_TYPE_LOADING = 0x03;
    private static final int VIEW_TYPE_ERROR = 0x04;

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
            case VIEW_TYPE_LOADING:
                holder = QuestionsAdapterVH.ofLoading(parent);
                break;
            case VIEW_TYPE_ERROR:
                holder = QuestionsAdapterVH.ofError(parent);
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
        } else if (row.isTypeLoading()) {
            return VIEW_TYPE_LOADING;
        } else if (row.isTypeError()) {
            return VIEW_TYPE_ERROR;
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

        static QuestionsAdapterVH ofError(ViewGroup parent) {
            return ErrorVH.create(parent);
        }

        static QuestionsAdapterVH ofLoading(ViewGroup parent) {
            return LoadingVH.create(parent);
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
        @BindView(R.id.flow_layout_tags)
        ViewGroup tagsLayout;
        @BindView(R.id.tv_view_count)
        TextView viewCount;
        @BindView(R.id.tv_answer_count)
        TextView answerCount;
        @BindView(R.id.tv_vote_count)
        TextView votesCount;

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
            if (tagsLayout != null && tagsLayout.getChildCount() != 0) {
                tagsLayout.removeAllViews();
            }
            Utils.loadRoundImage(itemView.getContext(), row.imageUrl(), avatar);
            ownerName.setText(row.name());
            title.setText(row.title());
            timeStamp.setText(Utils.timeStampRelativeToCurrentTime(row.timeStamp() * 1000));
            if (row.question().tags() != null && !row.question().tags().isEmpty()) {
                for (String tag : row.question().tags()) {
                    TagView tagView = TagView.formView(tagsLayout, tag);
                    tagView.setOnClickListener(v -> eventBus.send(new Pair<>(AppEvents.QUESTION_TAG_CLICKED, tag)));
                    tagsLayout.addView(tagView);
                }
            }
            viewCount.setText(String.valueOf(row.question().viewCount()));
            answerCount.setText(String.valueOf(row.question().answerCount()));
            votesCount.setText(String.valueOf(row.question().score()));
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


    /**
     * Binds groups name and avatar in the list.
     */
    public static class LoadingVH extends QuestionsAdapterVH {

        /**
         * Initialize constructor with item view.
         *
         * @param itemView item view / row view.
         */
        LoadingVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Inflate the user skillLayout to view.
         *
         * @param parent view group.
         * @return users view holder.
         */
        public static LoadingVH create(ViewGroup parent) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_loading, parent, false);
            return new LoadingVH(rootView);
        }

        @Override
        void bind(QuestionsAdapterRow row, final RxEventBus eventBus) {

        }

        @Override
        void destroy() {

        }
    }


    /**
     * Binds groups name and avatar in the list.
     */
    public static class ErrorVH extends QuestionsAdapterVH {

        /**
         * Initialize constructor with item view.
         *
         * @param itemView item view / row view.
         */
        ErrorVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Inflate the user skillLayout to view.
         *
         * @param parent view group.
         * @return users view holder.
         */
        public static ErrorVH create(ViewGroup parent) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_error, parent, false);
            return new ErrorVH(rootView);
        }

        @Override
        void bind(QuestionsAdapterRow row, final RxEventBus eventBus) {

        }

        @Override
        void destroy() {

        }
    }
}
