package com.nathansdev.stack;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class ProfileScrollBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {

    // calculated from given layout
    private int startXPositionImage;
    private int startYPositionImage;
    private int startHeight;
    private int startToolbarHeight;

    private boolean initialised = false;

    private float amountOfToolbarToMove;
    private float amountOfImageToReduce;
    private float amountToMoveXPosition;
    private float amountToMoveYPosition;

    // user configured params
    private float finalToolbarHeight, finalXPosition, finalYPosition, finalHeight;

    public ProfileScrollBehavior(
            final Context context,
            final AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProfileScrollBehavior);
            finalXPosition = a.getDimension(R.styleable.ProfileScrollBehavior_finalXPosition, 24);
            finalYPosition = a.getDimension(R.styleable.ProfileScrollBehavior_finalYPosition, 24);
            finalHeight = a.getDimension(R.styleable.ProfileScrollBehavior_finalHeight, 0);
            finalToolbarHeight = a.getDimension(R.styleable.ProfileScrollBehavior_finalToolbarHeight, 0);
            a.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(
            final CoordinatorLayout parent,
            final RelativeLayout child,
            final View dependency) {

        return dependency instanceof AppBarLayout; // change if you want another sibling to depend on
    }

    @Override
    public boolean onDependentViewChanged(
            final CoordinatorLayout parent,
            final RelativeLayout child,
            final View dependency) {

        // make child (avatar) change in relation to dependency (toolbar) in both size and position, init with properties from layout
        initProperties(child, dependency);

        // calculate progress of movement of dependency
        float currentToolbarHeight = startToolbarHeight + dependency.getY(); // current expanded height of toolbar
        // don't go below configured min height for calculations (it does go passed the toolbar)
        currentToolbarHeight = currentToolbarHeight < finalToolbarHeight ? finalToolbarHeight : currentToolbarHeight;
        final float amountAlreadyMoved = startToolbarHeight - currentToolbarHeight;
        final float progress = 100 * amountAlreadyMoved / amountOfToolbarToMove; // how much % of expand we reached

        // update image size
        final float heightToSubtract = progress * amountOfImageToReduce / 100;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.width = (int) (startHeight - heightToSubtract);
        lp.height = (int) (startHeight - heightToSubtract);
        child.setLayoutParams(lp);

        // update image position
        final float distanceXToSubtract = progress * amountToMoveXPosition / 100;
        final float distanceYToSubtract = progress * amountToMoveYPosition / 100;
        float newXPosition = startXPositionImage - distanceXToSubtract;
        //newXPosition = newXPosition < endXPosition ? endXPosition : newXPosition; // don't go passed end position
        child.setX(newXPosition);
        child.setY(startYPositionImage - distanceYToSubtract);
        return true;
    }

    private void initProperties(
            final RelativeLayout child,
            final View dependency) {

        if (!initialised) {
            // form initial layout
            startHeight = child.getHeight();
            startXPositionImage = (int) child.getX();
            startYPositionImage = (int) child.getY();
            startToolbarHeight = dependency.getHeight();
            // some calculated fields
            amountOfToolbarToMove = startToolbarHeight - finalToolbarHeight;
            amountOfImageToReduce = startHeight - finalHeight;
            amountToMoveXPosition = startXPositionImage - finalXPosition;
            amountToMoveYPosition = startYPositionImage - finalYPosition;
            initialised = true;
        }
    }

}