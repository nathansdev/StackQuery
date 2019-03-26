package com.nathansdev.stack.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;


/**
 * A custom layout that takes care of overflowing items and positions them on the next line.
 */
public class FlowLayoutTags extends ViewGroup {
    private int mLineHeight;
    private int mVerticalSpacing;
    private int mHorizontalSpacing;

    /**
     * Constructor with no attributes.
     *
     * @param context Context
     */
    public FlowLayoutTags(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * Constructor with attribute set.
     *
     * @param context Context
     * @param attrs   attribute set
     */
    public FlowLayoutTags(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mVerticalSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        mHorizontalSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        final int count = getChildCount();
        int lineHeight = 0;

        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();

        int childHeightMeasureSpec;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        } else {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec);
                final int childMeasuredWidth = child.getMeasuredWidth() + child.getPaddingLeft() + child.getPaddingRight();
                lineHeight = Math.max(lineHeight, child.getMeasuredHeight() + mVerticalSpacing);

                if (xpos + childMeasuredWidth > width) {
                    xpos = getPaddingLeft();
                    ypos += lineHeight;
                }

                xpos += childMeasuredWidth + mHorizontalSpacing;
            }
        }
        this.mLineHeight = lineHeight;

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = ypos + lineHeight;

        } else if ((MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) && (ypos + lineHeight < height)) {
            height = ypos + lineHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        final int width = r - l;
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int childMeasuredWidth = child.getMeasuredWidth();
                final int childMeasuredHeight = child.getMeasuredHeight();
                if (xpos + childMeasuredWidth > width) {
                    xpos = getPaddingLeft();
                    ypos += mLineHeight;
                }
                child.layout(xpos, ypos, xpos + childMeasuredWidth, ypos + childMeasuredHeight);
                xpos += childMeasuredWidth + mHorizontalSpacing;
            }
        }
    }
}
