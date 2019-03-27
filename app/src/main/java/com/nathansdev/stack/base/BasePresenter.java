package com.nathansdev.stack.base;

import timber.log.Timber;

/**
 * Base class than implements presenter interface and provides base implementation for
 * any view being attached.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;

    @Override
    public void onAttach(V mvpView) {
        Timber.v("loadFeedWithDelay view");
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        Timber.d("detached %s", "detached");
        mMvpView = null;
    }

    private boolean isViewAttached() {
        return mMvpView != null;
    }

    protected V getMvpView() {
        return mMvpView;
    }

    /**
     * Checks whether mvp view is attached.
     */
    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    /**
     * Exception class which throw run time exception.
     */
    public static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before"
                    + " requesting data to the Presenter");
        }
    }
}
