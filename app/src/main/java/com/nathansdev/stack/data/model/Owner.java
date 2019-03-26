package com.nathansdev.stack.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Owner implements Parcelable {
    @Nullable
    @Json(name = "reputation")
    public abstract Long reputation();

    @Nullable
    @Json(name = "user_id")
    public abstract Long id();

    @Nullable
    @Json(name = "user_type")
    public abstract String type();

    @Nullable
    @Json(name = "accept_rate")
    public abstract Long rate();

    @Nullable
    @Json(name = "profile_image")
    public abstract String image();

    @Nullable
    @Json(name = "display_name")
    public abstract String name();

    @Nullable
    @Json(name = "link")
    public abstract String link();

    public static JsonAdapter<Owner> ownerJsonAdapter(Moshi moshi) {
        return new AutoValue_Owner.MoshiJsonAdapter(moshi);
    }
}
