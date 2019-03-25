package com.nathansdev.stack.home.feed;

import android.view.View;

import com.nathansdev.stack.base.BaseFragment;

import javax.inject.Inject;

public class SelfFragment extends BaseFragment {

    @Inject
    public SelfFragment() {

    }

    public static SelfFragment newInstance() {
        SelfFragment fragment = new SelfFragment();
        return fragment;
    }

    @Override
    protected void setUpView(View view) {

    }
}
