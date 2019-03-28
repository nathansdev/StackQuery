package com.nathansdev.stack.common;


import com.nathansdev.stack.base.MvpPresenter;
import com.nathansdev.stack.base.MvpView;

public interface CommonPresenter<V extends MvpView> extends MvpPresenter<V> {
    void loadUser();

    void invalidateAccessToken(String token);

    void init();

    void cleanUp();
}
