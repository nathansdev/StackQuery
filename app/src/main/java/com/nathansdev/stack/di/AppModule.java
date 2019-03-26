package com.nathansdev.stack.di;

import android.app.Application;
import android.content.Context;

import com.nathansdev.stack.AppPreferences;
import com.nathansdev.stack.data.model.MyAdapterFactory;
import com.nathansdev.stack.rxevent.RxEventBus;
import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Contains all singleton and provides methods needed for app.
 */
@Module
abstract class AppModule {

    @Binds
    abstract Context provideContext(Application application);

    @Provides
    @Singleton
    static RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }

    @Provides
    @Singleton
    static AppPreferences provideAppPreferences(Application application) {
        return new AppPreferences(application);
    }
}
