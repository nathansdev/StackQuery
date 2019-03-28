package com.nathansdev.stack.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class UsersResponse implements Parcelable {

    @Nullable
    @Json(name = "items")
    public abstract List<Owner> users();

    @Nullable
    @Json(name = "has_more")
    public abstract Boolean hasMore();

    @Nullable
    @Json(name = "quota_max")
    public abstract Long max();

    @Nullable
    @Json(name = "quota_remaining")
    public abstract Long remaining();

    public static JsonAdapter<UsersResponse> usersResponseJsonAdapter(Moshi moshi) {
        return new AutoValue_UsersResponse.MoshiJsonAdapter(moshi);
    }
}
