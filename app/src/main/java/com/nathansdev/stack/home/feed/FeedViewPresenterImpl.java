package com.nathansdev.stack.home.feed;

import com.nathansdev.stack.base.BasePresenter;
import com.nathansdev.stack.data.api.StackExchangeApi;
import com.nathansdev.stack.data.model.QuestionsResponse;
import com.nathansdev.stack.error.DisposableSubscriberCallbackWrapper;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FeedViewPresenterImpl<V extends FeedView> extends BasePresenter<V> implements FeedViewPresenter<V> {

    private StackExchangeApi api;
    private PublishProcessor<Long> questionsSubject = PublishProcessor.create();
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    FeedViewPresenterImpl(StackExchangeApi api) {
        this.api = api;
    }

    @Override
    public void init() {
        Disposable disposable = questionsSubject
                .onBackpressureDrop()
                .concatMap((Function<Long, Publisher<QuestionsResponse>>) page ->
                        getObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriberCallbackWrapper<QuestionsResponse>(getMvpView()) {

                    @Override
                    protected void onNextAction(QuestionsResponse alertsAdapterRows) {
                        Timber.d("alert rows %s", alertsAdapterRows);
                    }

                    @Override
                    protected void onCompleted() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void loadQuestions() {
        Timber.d("loadQuestions");
        questionsSubject.onNext(0L);
    }

    @Override
    public void loadNextPage() {
        Timber.d("loadNextPage");
    }

    @Override
    public void cleanUp() {
        disposables.clear();
    }

    private Flowable<QuestionsResponse> getObservable() {
        return api.getQuestionsFlowable("activity", "stackoverflow", "desc")
                .subscribeOn(Schedulers.io());
    }
}
