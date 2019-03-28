package com.nathansdev.stack.home.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;

import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.AppPreferences;
import com.nathansdev.stack.R;
import com.nathansdev.stack.base.BaseFragment;
import com.nathansdev.stack.common.CommonPresenter;
import com.nathansdev.stack.common.CommonView;
import com.nathansdev.stack.data.model.Owner;
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
import timber.log.Timber;

/**
 * profile fragment to display loggedin user.
 */
public class ProfileFragment extends BaseFragment implements HasSupportFragmentInjector, CommonView {

    private static final String FRAG_TAG_MY_FEED = "myFeedFragment";

    @Inject
    public ProfileFragment() {

    }

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;
    @Inject
    RxEventBus eventBus;
    @Inject
    AppPreferences preferences;
    @Inject
    CommonPresenter<CommonView> presenter;
    @Inject
    MyFeedFragment myFeedFragment;

    @BindView(R.id.my_feeds_container)
    View myFeedsContainer;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.screen_mask_with_loader)
    View mask;
    @BindView(R.id.login_empty_state_panel)
    View loginPanel;
    @BindView(R.id.button_login)
    View buttonLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        presenter.onAttach(this);
        return rootView;
    }

    @Override
    protected void setUpView(View view) {
        toolbar.setNavigationIcon(Utils.getTintedVectorAsset(getActivity(), R.drawable.ic_close_black_24dp, R.color.black));
        toolbar.setNavigationOnClickListener(v -> eventBus.send(new Pair<>(AppEvents.BACK_ARROW_CLICKED, " ")));
        addChildFragment();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.send(new Pair<>(AppEvents.LOGIN_CLICKED, null));
            }
        });
    }

    private void addChildFragment() {
        MyFeedFragment myFeedFrag = getMyFeedFrag();
        if (myFeedFrag == null) {
            myFeedFrag = myFeedFragment;
            myFeedFrag.setArguments(getFilterArgBundle());
            getChildFragmentManager().beginTransaction()
                    .add(myFeedsContainer.getId(), myFeedFrag, FRAG_TAG_MY_FEED).commit();
        }
    }

    /**
     * Return Feedback filter fragment by tag.
     *
     * @return Feedback filter fragment.
     */
    private MyFeedFragment getMyFeedFrag() {
        return (MyFeedFragment) getChildFragmentManager().findFragmentByTag(FRAG_TAG_MY_FEED);
    }

    private Bundle getFilterArgBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ARG_FILTER_TYPE, AppConstants.MY_FEED);
        return bundle;
    }

    private void showOptionsMenu(View view, PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
        PopupMenu popup = new PopupMenu(getActivity(), view, Gravity.END);
        popup.getMenuInflater().inflate(R.menu.action_menu_logout,
                popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    private PopupMenu.OnMenuItemClickListener menuItemClickListener(RxEventBus eventBus) {
        return item -> {
            switch (item.getItemId()) {
                case R.id.action_logout:
                    eventBus.send(new Pair<>(AppEvents.LOGOUT_CLICKED, null));
                    break;
                default:
                    break;
            }
            return true;
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cleanUp();
        presenter.onDetach();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    public void handleProfileClicked() {
        if (preferences.isLoggedIn()) {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_logout);
            toolbar.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.action_logout_menu) {
                    showOptionsMenu(toolbar, menuItemClickListener(eventBus));
                }
                return false;
            });
            presenter.loadUser();
        } else {
            loginPanel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showUser(Owner owner) {
        Timber.d("showUser %s", owner);
        preferences.setUserId(owner.id());
        toolbar.setTitle(owner.name());
        myFeedFragment.loadQuestions();
    }

    @Override
    public void onLoggedOut() {
        preferences.setIsLoggedIn(false);
        preferences.delete();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        eventBus.send(new Pair<>(AppEvents.LOGOUT_COMPLETED, null));
    }

    public void logOutUser() {
        mask.setVisibility(View.VISIBLE);
        presenter.invalidateAccessToken(preferences.getAccessToken());
    }
}
