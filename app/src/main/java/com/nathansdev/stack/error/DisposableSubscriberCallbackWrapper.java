package com.nathansdev.stack.error;

import android.util.Pair;

import com.nathansdev.stack.base.MvpView;
import com.nathansdev.stack.utils.ErrorUtils;

import java.lang.ref.WeakReference;

import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

public abstract class DisposableSubscriberCallbackWrapper<T> extends DisposableSubscriber<T> {

    //MvpView is just a reference of a View in MVP
    private WeakReference<MvpView> weakReference;

    public DisposableSubscriberCallbackWrapper(MvpView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onNextAction(T t);

    protected abstract void onCompleted();

    @Override
    public void onNext(T t) {
        onNextAction(t);
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e);
        MvpView view = weakReference.get();
        Pair<Integer, String> valuePair = ErrorUtils.errorMessage(e);
        view.onError(valuePair.second);
    }

    @Override
    public void onComplete() {
        onCompleted();
    }
}