package com.nathansdev.stack.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import timber.log.Timber;

/**
 * Base activity class for other activities.
 */

public abstract class BaseActivity extends AppCompatActivity implements HasFragmentInjector, MvpView {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(@StringRes int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    /**
     * Hide soft keyboard.
     */
    public void hideKeyboard() {

    }

    @Override
    public void showSessionExpired() {

    }

    @Override
    public boolean isSessionExpired() {
        return false;
    }

    @Override
    public void onServerDownError(int errorCode) {
        Timber.d("server down error");
    }
}

