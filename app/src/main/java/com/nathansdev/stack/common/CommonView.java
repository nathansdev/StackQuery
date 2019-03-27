package com.nathansdev.stack.common;


import com.nathansdev.stack.base.MvpView;
import com.nathansdev.stack.data.model.Owner;

public interface CommonView extends MvpView {
    void showUser(Owner owner);

    void onLoggedOut();
}
