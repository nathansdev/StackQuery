package com.nathansdev.stack.home;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.R;
import com.nathansdev.stack.base.BaseActivity;
import com.nathansdev.stack.home.feed.ActivityFeedFragment;
import com.nathansdev.stack.home.feed.FeaturedFeedFragment;
import com.nathansdev.stack.home.feed.HotFeedFragment;
import com.nathansdev.stack.home.feed.MonthLyFeedFragment;
import com.nathansdev.stack.home.feed.SelfFragment;
import com.nathansdev.stack.home.feed.WeekLyFeedFragment;
import com.nathansdev.stack.rxevent.AppEvents;
import com.nathansdev.stack.rxevent.RxEventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    // injection
    @Inject
    RxEventBus eventBus;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tableLayout;
    @Inject
    FeaturedFeedFragment featuredFeedFragment;
    @Inject
    HotFeedFragment hotFeedFragment;
    @Inject
    ActivityFeedFragment activityFeedFragment;
    @Inject
    MonthLyFeedFragment monthLyFeedFragment;
    @Inject
    WeekLyFeedFragment weekLyFeedFragment;
    @Inject
    SelfFragment selfFragment;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private HomePagerAdapter homePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setUpSubscription();
        setUpViews();
        setUpViewPager();
        addFragmentsToContainer();
    }

    private void setUpSubscription() {
        disposables.add(eventBus.toObservables()
                .onErrorReturn(throwable -> {
                    Timber.tag(TAG).e(throwable);
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    Timber.v("Event received %s", event.first);
                    handleEventData(event);
                })
        );
    }

    private void handleEventData(Pair<String, Object> event) {
        if (event.first.equalsIgnoreCase(AppEvents.PROFILE_MENU_CLICKED)) {
            handleProfileMenuClicked();
        } else if (event.first.equalsIgnoreCase(AppEvents.QUESTION_TAG_CLICKED)) {
            handleQuestionsTagClicked((String) event.second);
        }
    }

    /**
     * add all fragments to activity.
     */
    private void addFragmentsToContainer() {

    }

    /**
     * Initializing view pager.
     */
    private void setUpViewPager() {
        activityFeedFragment.setArguments(getFilterArgBundle(AppConstants.ACTIVITY));
        featuredFeedFragment.setArguments(getFilterArgBundle(AppConstants.VOTES));
        hotFeedFragment.setArguments(getFilterArgBundle(AppConstants.HOT));
        monthLyFeedFragment.setArguments(getFilterArgBundle(AppConstants.MONTH));
        weekLyFeedFragment.setArguments(getFilterArgBundle(AppConstants.WEEK));
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), activityFeedFragment,
                featuredFeedFragment, hotFeedFragment, monthLyFeedFragment, weekLyFeedFragment,
                selfFragment, getResources().getStringArray(R.array.home_tabs));
        viewPager.setAdapter(homePagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        });
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setCurrentItem(0);
    }

    private void setUpViews() {
        toolbar.inflateMenu(R.menu.menu_profile);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.action_profile) {
                eventBus.send(new Pair<>(AppEvents.PROFILE_MENU_CLICKED, null));
            }
            return false;
        });
    }

    private void handleProfileMenuClicked() {

    }

    private void handleQuestionsTagClicked(String tag) {
        Toast toast = Toast.makeText(this, tag, Toast.LENGTH_SHORT);

        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be edited
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.white));
        toast.show();
    }

    private Bundle getFilterArgBundle(String filterType) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ARG_FILTER_TYPE, filterType);
        return bundle;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
