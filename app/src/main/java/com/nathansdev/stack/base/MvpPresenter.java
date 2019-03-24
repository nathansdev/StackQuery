package com.nathansdev.stack.base;

/**
 * Every presenter in app should implement this interface or extend BasePresenter with mvp view type.
 */

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();
}
