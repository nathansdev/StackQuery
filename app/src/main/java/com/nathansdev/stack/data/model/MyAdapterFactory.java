package com.nathansdev.stack.data.model;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

/**
 * MyAdapterFactory common adapter factory for all models.
 */
@MoshiAdapterFactory
public abstract class MyAdapterFactory implements JsonAdapter.Factory {

    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_MyAdapterFactory();
    }
}
