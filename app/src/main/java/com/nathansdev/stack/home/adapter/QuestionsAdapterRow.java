package com.nathansdev.stack.home.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.nathansdev.stack.data.model.Question;

/**
 * A Builder class for recyclerview items  in QuestionsAdapter.
 */
@AutoValue
public abstract class QuestionsAdapterRow implements Parcelable {
    private static final int TYPE_QUESTION = 0x01;
    private static final int TYPE_LOAD_MORE = 0x02;
    private static final int TYPE_LOADING = 0x03;
    private static final int TYPE_ERROR = 0x04;

    /**
     * Builds along with already built row.
     *
     * @return update row/model.
     */
    public abstract Builder toBuilder();

    /**
     * Returns to builder.
     *
     * @return builder class.
     */
    public Builder withBuild() {
        return toBuilder();
    }

    public boolean isTypeQuestion() {
        return type() == TYPE_QUESTION;
    }

    public boolean isTypeLoadMore() {
        return type() == TYPE_LOAD_MORE;
    }

    public boolean isTypeLoading() {
        return type() == TYPE_LOADING;
    }

    public boolean isTypeError() {
        return type() == TYPE_ERROR;
    }

    public static Builder builder() {
        return new AutoValue_QuestionsAdapterRow.Builder();
    }

    public static QuestionsAdapterRow ofQuestion(Question question) {
        return builder().type(TYPE_QUESTION).imageUrl(question.owner().image()).question(question)
                .name(question.owner().name()).timeStamp(question.updatedAt())
                .title(question.title()).votes(question.score()).build();
    }

    public static QuestionsAdapterRow ofLoading() {
        return builder().type(TYPE_LOADING).build();
    }

    public static QuestionsAdapterRow ofLoadMore() {
        return builder().type(TYPE_LOAD_MORE).build();
    }

    public static QuestionsAdapterRow ofError() {
        return builder().type(TYPE_ERROR).build();
    }

    /**
     * Compare the new item with existing item.
     *
     * @param r2 row.
     * @return -1 if new item should be top of existing item, 0 if items are same and
     * 1 if new item should below the existing item.
     */
    public int compare(QuestionsAdapterRow r2) {
        int comparedValue = -1;
        if (this.isTypeQuestion() && r2.isTypeLoadMore()) {
            return -1;
        } else if (this.isTypeLoadMore() && r2.isTypeQuestion()) {
            return 1;
        } else if (this.isTypeQuestion() && r2.isTypeLoading()) {
            return -1;
        } else if (this.isTypeLoading() && r2.isTypeQuestion()) {
            return 1;
        } else if (this.isTypeLoadMore() && r2.isTypeLoadMore()) {
            return 0;
        } else if (this.isTypeLoading() && r2.isTypeLoading()) {
            return 0;
        } else if (this.isTypeError() && r2.isTypeError()) {
            return 0;
        }
        return comparedValue;
    }

    /**
     * Compare two item with hashcode.
     *
     * @param newItem new item.
     * @return true if items are same else false.
     */
    public boolean areContentsTheSame(QuestionsAdapterRow newItem) {
        if (this.isTypeQuestion() && newItem.isTypeQuestion()) {
            return this.question().equals(newItem.question());
        } else if (isTypeLoadMore() && newItem.isTypeLoadMore()) {
            return this.equals(newItem);
        } else if (isTypeLoading() && newItem.isTypeLoading()) {
            return this.equals(newItem);
        } else if (isTypeError() && newItem.isTypeError()) {
            return this.equals(newItem);
        }
        return false;
    }

    /**
     * Compare two items for the same item with particular attribute value.
     *
     * @param newItem new item.
     * @return true if items are same else false.
     */
    public boolean areItemsTheSame(QuestionsAdapterRow newItem) {
        if (isTypeQuestion() && newItem.isTypeQuestion()) {
            return this.question().id().longValue() == newItem.question().id().longValue();
        } else if (isTypeLoadMore() && newItem.isTypeLoadMore()) {
            return true;
        } else if (isTypeLoading() && newItem.isTypeLoading()) {
            return true;
        } else return isTypeError() && newItem.isTypeError();
    }

    @Nullable
    public abstract Question question();

    @Nullable
    public abstract String title();

    @Nullable
    public abstract Long timeStamp();

    @Nullable
    public abstract String name();

    @Nullable
    public abstract String imageUrl();

    @Nullable
    public abstract Integer votes();

    public abstract int type();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract QuestionsAdapterRow build();

        public abstract Builder type(int t);

        public abstract Builder question(Question t);

        public abstract Builder title(String t);

        public abstract Builder timeStamp(Long t);

        public abstract Builder name(String t);

        public abstract Builder imageUrl(String t);

        public abstract Builder votes(Integer t);
    }
}
