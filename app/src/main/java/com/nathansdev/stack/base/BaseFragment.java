package com.nathansdev.stack.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import butterknife.Unbinder;
import dagger.android.AndroidInjection;

/**
 * Base fragment class that any fragment can extends.
 */
public abstract class BaseFragment extends Fragment implements MvpView {

    @Nullable
    private Unbinder viewUnbinder;

    private BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidInjection.inject(this);
        }
        if (activity instanceof BaseActivity) {
            this.mActivity = (BaseActivity) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndroidInjection.inject(this);
        }
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView(view);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // good to use here too.
        //viewUnbinder = ButterKnife.bind(this, getView());
    }

    @Override
    public void onDestroyView() {
        if (viewUnbinder != null) {
            viewUnbinder.unbind();
        }
        mActivity = null;
        super.onDestroyView();
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
    public void showMessage(@StringRes int resId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public boolean isSessionExpired() {
        return false;
    }

    @Override
    public void showSessionExpired() {

    }

    @Override
    public void onServerDownError(int errorCode) {
        mActivity.onServerDownError(errorCode);
    }

    protected abstract void setUpView(View view);

    public void setViewUnbinder(Unbinder unbinder) {
        viewUnbinder = unbinder;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
