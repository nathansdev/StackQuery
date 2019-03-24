package com.nathansdev.stack.di;

import android.app.Application;
import android.content.Context;

import com.nathansdev.stack.rxevent.RxEventBus;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Contains all singleton and provides methods needed for app.
 */
@Module
public abstract class AppModule {

    @Binds
    abstract Context provideContext(Application application);

    @Provides
    @Singleton
    static RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }
}
