package com.nathansdev.stack.error;

import com.nathansdev.stack.base.MvpView;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableSingleObserver;

public abstract class SingleObserverCallbackWrapper<T> extends DisposableSingleObserver<T> {

    //MvpView is just a reference of a View in MVP
    private WeakReference<MvpView> weakReference;

    public SingleObserverCallbackWrapper(MvpView view) {
        this.weakReference = new WeakReference<>(view);
    }

    @Override
    public void onSuccess(T t) {
        duringSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        MvpView view = weakReference.get();
    }

    protected abstract void duringSuccess(T t);

    protected abstract void duringFailure(String message);
}