package com.nathansdev.stack.error;

import com.nathansdev.stack.base.MvpView;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

/**
 * DisposableObserver wrapper for handling errors on a single place
 */
public abstract class DisposableObserverCallbackWrapper<T> extends DisposableObserver<T> {

    //MvpView is just a reference of a View in MVP
    private WeakReference<MvpView> weakReference;

    public DisposableObserverCallbackWrapper(MvpView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T t);

    protected abstract void onNextAction(T t);

    protected abstract void onCompleted();

    @Override
    public void onNext(T t) {
        onNextAction(t);
    }

    @Override
    public void onError(Throwable e) {
        MvpView view = weakReference.get();
    }

    @Override
    public void onComplete() {
        onCompleted();
    }
}