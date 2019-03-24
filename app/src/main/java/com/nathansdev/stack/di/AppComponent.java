package com.nathansdev.stack.di;

import android.app.Application;

import com.nathansdev.stack.StackAndroidApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * App component interface.
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilderModule.class})
public interface AppComponent {

    /**
     * Binding application class instance to app component.
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(StackAndroidApp app);
}
