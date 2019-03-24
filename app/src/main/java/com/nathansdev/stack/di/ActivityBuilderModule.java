package com.nathansdev.stack.di;

import com.nathansdev.stack.home.HomeActivity;
import com.nathansdev.stack.home.HomeActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Activity builder class that maps all activities in graph using dagger.
 */
@Module
public abstract class ActivityBuilderModule {
    @PerActivity
    @ContributesAndroidInjector(modules = {HomeActivityModule.class})
    abstract HomeActivity bindHomeActivity();
}
