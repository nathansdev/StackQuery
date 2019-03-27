package com.nathansdev.stack.home.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathansdev.stack.R;
import com.nathansdev.stack.base.BaseFragment;
import com.nathansdev.stack.home.profile.MyFeedFragment;
import com.nathansdev.stack.rxevent.AppEvents;
import com.nathansdev.stack.rxevent.RxEventBus;
import com.nathansdev.stack.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ProfileFragment extends BaseFragment implements HasSupportFragmentInjector, AppBarLayout.OnOffsetChangedListener {

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_AT_TOOLBAR = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    @Inject
    public ProfileFragment() {

    }

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;
    @Inject
    RxEventBus eventBus;
    @Inject
    MyFeedFragment myFeedFragment;

    @BindView(R.id.my_feeds_container)
    View myFeedsContainer;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_avatar)
    ImageView profileAvatar;
    @BindView(R.id.profile_layout)
    View profileLayout;
    @BindView(R.id.profile_collapse_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @Override
    protected void setUpView(View view) {
        toolbar.setNavigationIcon(Utils.getTintedVectorAsset(getActivity(), R.drawable.ic_close_black_24dp, R.color.black));
        toolbar.inflateMenu(R.menu.menu_logout);
        toolbar.setOverflowIcon(Utils.getTintedVectorAsset(getActivity(), R.drawable.ic_more_vert_black_24dp, R.color.black));
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                eventBus.send(new Pair<>(AppEvents.LOGOUT_CLICKED, null));
            }
            return true;
        });
        toolbar.setNavigationOnClickListener(v -> eventBus.send(new Pair<>(AppEvents.BACK_ARROW_CLICKED, " ")));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(toolbar, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(toolbar, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_AT_TOOLBAR) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(profileLayout, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(profileLayout, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    private void startAlphaAnimation(View v, int visibility) {
        if (v instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) v;
            toolbar.setTitle(visibility == View.VISIBLE ? "Sabarinathan" : "");
        } else {
            AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                    ? new AlphaAnimation(0f, 1f)
                    : new AlphaAnimation(1f, 0f);

            alphaAnimation.setDuration((long) ProfileFragment.ALPHA_ANIMATIONS_DURATION);
            alphaAnimation.setFillAfter(true);
            v.startAnimation(alphaAnimation);
        }
    }
}
