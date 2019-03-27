package com.nathansdev.stack.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.nathansdev.stack.GlideApp;
import com.nathansdev.stack.R;

import timber.log.Timber;

public class Utils {
    /**
     * Load round image into imageview.
     *
     * @param context ui context.
     * @param url     image url.
     * @param iv      image view.
     */
    public static final void loadRoundImage(final Context context, String url, final ImageView iv) {
        GlideApp.with(context).asBitmap()
                .load(url)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .error(R.drawable.ic_account_circle_black_24dp)
                .centerCrop()
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        iv.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * Return time stamp for epoch time.
     *
     * @param epoch unix time stamp.
     * @return time stamp in string with particular format eg: Dec 29, 2107
     */
    public static String timeStampRelativeToCurrentTime(long epoch) {
        Timber.d("timeStampRelativeToCurrentTime %s", TimeAgo.using(epoch));
        return TimeAgo.using(epoch );
    }

}
