package com.nathansdev.stack.rxevent;

import android.util.Pair;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


/**
 * Event bus for communication between activities and fragment.
 */

public class RxEventBus {

    @Inject
    public RxEventBus() {

    }

    private PublishSubject<Pair<String, Object>> eventSubject = PublishSubject.create();

    /**
     * Return event subject which has a super class as observable.
     *
     * @return Observable.
     */
    public Observable<Pair<String, Object>> toObservables() {
        return eventSubject;
    }

    /**
     * Check whether subject has observers.
     *
     * @return true or false.
     */
    public boolean hasObservers() {
        return eventSubject.hasObservers();
    }

    /**
     * Send event via subject to the class which subscribed it.
     *
     * @param event event.
     */
    public void send(Pair<String, Object> event) {
        eventSubject.onNext(event);
    }
}
