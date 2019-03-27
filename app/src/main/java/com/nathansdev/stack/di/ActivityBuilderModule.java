package com.nathansdev.stack.di;

import com.nathansdev.stack.auth.LoginActivity;
import com.nathansdev.stack.home.HomeActivity;
import com.nathansdev.stack.home.HomeActivityModule;
import com.nathansdev.stack.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Activity builder class that maps all activities in graph using dagger.
 */
@Module
abstract class ActivityBuilderModule {
    @PerActivity
    @ContributesAndroidInjector(modules = {HomeActivityModule.class})
    abstract HomeActivity bindHomeActivity();

    @PerActivity
    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @PerActivity
    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();
}
