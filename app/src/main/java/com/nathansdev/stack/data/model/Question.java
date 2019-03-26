package com.nathansdev.stack.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class Question implements Parcelable {
    @Nullable
    @Json(name = "question_id")
    public abstract Long id();

    @Nullable
    @Json(name = "title")
    public abstract String title();

    @Nullable
    @Json(name = "Owner")
    public abstract Owner owner();

    @Nullable
    @Json(name = "is_answered")
    public abstract Boolean isAnswered();

    @Nullable
    @Json(name = "view_count")
    public abstract Integer viewCount();

    @Nullable
    @Json(name = "answer_count")
    public abstract Integer answerCount();

    @Nullable
    @Json(name = "score")
    public abstract Integer score();

    @Nullable
    @Json(name = "display_name")
    public abstract String name();

    @Nullable
    @Json(name = "link")
    public abstract String link();

    @Nullable
    @Json(name = "last_activity_date")
    public abstract String updatedAt();

    @Nullable
    @Json(name = "creation_date")
    public abstract String createdAt();

    @Nullable
    @Json(name = "last_edit_date")
    public abstract String editedAt();

    @Nullable
    @Json(name = "tags")
    public abstract List<String> tags();

    public static JsonAdapter<Question> questionJsonAdapter(Moshi moshi) {
        return new AutoValue_Question.MoshiJsonAdapter(moshi);
    }
}
