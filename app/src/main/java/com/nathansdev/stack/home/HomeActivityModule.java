package com.nathansdev.stack.home;


import com.nathansdev.stack.di.PerActivity;
import com.nathansdev.stack.di.PerFragment;
import com.nathansdev.stack.home.feed.ActivityFeedFragment;
import com.nathansdev.stack.home.feed.FeaturedFeedFragment;
import com.nathansdev.stack.home.feed.FeedView;
import com.nathansdev.stack.home.feed.FeedViewPresenter;
import com.nathansdev.stack.home.feed.FeedViewPresenterImpl;
import com.nathansdev.stack.home.feed.HotFeedFragment;
import com.nathansdev.stack.home.feed.MonthLyFeedFragment;
import com.nathansdev.stack.home.feed.SelfFragment;
import com.nathansdev.stack.home.feed.WeekLyFeedFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Activity module for home activity.
 */
@Module
public abstract class HomeActivityModule {
    @PerFragment
    @ContributesAndroidInjector
    abstract ActivityFeedFragment provideInterestingFeedFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector
    abstract FeaturedFeedFragment provideFeaturedFeedFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector
    abstract HotFeedFragment provideHotFeedFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector
    abstract MonthLyFeedFragment provideMonthLyFeedFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector
    abstract WeekLyFeedFragment provideWeekLyFeedFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector()
    abstract SelfFragment providePSelfFragmentFactory();

    @PerActivity
    @Binds
    abstract FeedViewPresenter<FeedView> provideFeedViewPresenter(FeedViewPresenterImpl<FeedView>
                                                                          feedViewPresenterImpl);
}
