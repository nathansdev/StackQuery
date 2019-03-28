package com.nathansdev.stack.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Error implements Parcelable {
    @Nullable
    @Json(name = "error_id")
    public abstract Long id();

    @Nullable
    @Json(name = "error_name")
    public abstract String name();

    @Nullable
    @Json(name = "error_message")
    public abstract String message();

    public static JsonAdapter<Error> errorJsonAdapter(Moshi moshi) {
        return new AutoValue_Error.MoshiJsonAdapter(moshi);
    }
}
