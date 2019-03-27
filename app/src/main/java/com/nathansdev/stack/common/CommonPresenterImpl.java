package com.nathansdev.stack.common;


import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.base.BasePresenter;
import com.nathansdev.stack.data.api.StackExchangeApi;
import com.nathansdev.stack.data.model.CommonResponseWrapper;
import com.nathansdev.stack.data.model.UsersResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CommonPresenterImpl<V extends CommonView> extends BasePresenter<V> implements CommonPresenter<V> {

    private StackExchangeApi api;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    CommonPresenterImpl(StackExchangeApi api) {
        this.api = api;
    }

    @Override
    public void loadUser() {
        Disposable disposable = getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, UsersResponse>() {
                    @Override
                    public UsersResponse apply(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                        return null;
                    }
                })
                .subscribe(new Consumer<UsersResponse>() {
                    @Override
                    public void accept(UsersResponse response) throws Exception {
                        handleUserProfileReceived(response);
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void invalidateAccessToken(String token) {
        Disposable disposable = getObservable(token)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, CommonResponseWrapper>() {
                    @Override
                    public CommonResponseWrapper apply(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                        getMvpView().onLoggedOut();
                        return null;
                    }
                })
                .subscribe(new Consumer<CommonResponseWrapper>() {
                    @Override
                    public void accept(CommonResponseWrapper response) throws Exception {
                        Timber.d("logout response %s", response);
                        getMvpView().onLoggedOut();
                    }
                });
        disposables.add(disposable);
    }

    private void handleUserProfileReceived(UsersResponse response) {
        Timber.d("handleUserProfileReceived %s", response);
        if (!response.users().isEmpty()) {
            getMvpView().showUser(response.users().get(0));
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void cleanUp() {
        disposables.clear();
    }

    private Observable<UsersResponse> getObservable() {
        return api.getUserRx(AppConstants.REPUTATION, AppConstants.SITE, AppConstants.DESC)
                .subscribeOn(Schedulers.io());
    }

    private Observable<CommonResponseWrapper> getObservable(String accessToken) {
        return api.invalidateRx(accessToken)
                .subscribeOn(Schedulers.io());
    }
}
