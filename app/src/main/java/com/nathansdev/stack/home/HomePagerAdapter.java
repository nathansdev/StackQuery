package com.nathansdev.stack.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.nathansdev.stack.TaggedFragmentStatePagerAdapter;
import com.nathansdev.stack.home.feed.ActivityFeedFragment;
import com.nathansdev.stack.home.feed.FeaturedFeedFragment;
import com.nathansdev.stack.home.feed.HotFeedFragment;
import com.nathansdev.stack.home.feed.MonthLyFeedFragment;
import com.nathansdev.stack.home.feed.WeekLyFeedFragment;

/**
 * Pager adapter for home.
 */
public class HomePagerAdapter extends TaggedFragmentStatePagerAdapter {

    private static final String TAG = HomePagerAdapter.class.getSimpleName();
    private final FeaturedFeedFragment featuredFeedFragment;
    private final HotFeedFragment hotFeedFragment;
    private final ActivityFeedFragment interestingFeedFragment;
    private final MonthLyFeedFragment monthLyFeedFragment;
    private final WeekLyFeedFragment weekLyFeedFragment;
    private final String[] names;

    /**
     * Constructor for home pager adapter.
     *
     * @param fm                      fragment manager instance.
     * @param interestingFeedFragment feed type interesting instance.
     * @param featuredFeedFragment    feed type featured instance.
     * @param hotFeedFragment         alerts fragment instance.
     * @param monthLyFeedFragment     feed type monthly instance.
     * @param weekLyFeedFragment      feed type weekly instance.
     */
    HomePagerAdapter(FragmentManager fm, ActivityFeedFragment interestingFeedFragment,
                     FeaturedFeedFragment featuredFeedFragment, HotFeedFragment hotFeedFragment,
                     MonthLyFeedFragment monthLyFeedFragment, WeekLyFeedFragment weekLyFeedFragment,
                     String[] names) {
        super(fm);
        this.interestingFeedFragment = interestingFeedFragment;
        this.featuredFeedFragment = featuredFeedFragment;
        this.hotFeedFragment = hotFeedFragment;
        this.weekLyFeedFragment = weekLyFeedFragment;
        this.monthLyFeedFragment = monthLyFeedFragment;
        this.names = names;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return interestingFeedFragment;
        } else if (position == 1) {
            return featuredFeedFragment;
        } else if (position == 2) {
            return hotFeedFragment;
        } else if (position == 3) {
            return monthLyFeedFragment;
        } else if (position == 4) {
            return weekLyFeedFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "tabs";
        if (position == 0) {
            title = names[0];
        } else if (position == 1) {
            title = names[1];
        } else if (position == 2) {
            title = names[2];
        } else if (position == 3) {
            title = names[3];
        } else if (position == 4) {
            title = names[4];
        }
        return title;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public String getTag(int position) {
        return TAG + "." + names[position];
    }
}
