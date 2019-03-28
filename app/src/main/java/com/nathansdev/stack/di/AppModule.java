package com.nathansdev.stack.di;

import android.app.Application;
import android.content.Context;

import com.nathansdev.stack.AppConfig;
import com.nathansdev.stack.AppPreferences;
import com.nathansdev.stack.R;
import com.nathansdev.stack.rxevent.RxEventBus;
import com.nathansdev.stack.utils.ErrorUtils;
import com.nathansdev.stack.utils.Utils;

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

    @Provides
    @Singleton
    static AppConfig provideAppConfig(Application application) {
        return AppConfig.builder()
                .clientId(application.getString(R.string.client_id))
                .accesskey(application.getString(R.string.access_key))
                .clientSecretId(application.getString(R.string.client_secret_id))
                .redirectUri(application.getString(R.string.redirect_uri))
                .build();
    }

    @Provides
    @Singleton
    static Utils provideUtils() {
        return new Utils();
    }


    @Provides
    @Singleton
    static ErrorUtils provideErrorUtils() {
        return new ErrorUtils();
    }
}
