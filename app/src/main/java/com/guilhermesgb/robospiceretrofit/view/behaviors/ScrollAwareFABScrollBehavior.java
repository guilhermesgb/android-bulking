package com.guilhermesgb.robospiceretrofit.view.behaviors;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

@SuppressWarnings("unused")
public class ScrollAwareFABScrollBehavior extends FloatingActionButton.Behavior {

    boolean fabShouldScroll = true;

    public ScrollAwareFABScrollBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        fabShouldScroll = false;
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(parent, child, directTargetChild,
                target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout parent, FloatingActionButton child,
                        View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(parent, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        int lowerThreshold = 0;
        int upperThreshold = child.getHeight() * 2;
        if (!fabShouldScroll && child.getTranslationY() == lowerThreshold) {
            fabShouldScroll = true;
        }
        if (fabShouldScroll) {
            int translationY = Math.max(lowerThreshold, (int) child.getTranslationY() + dyConsumed);
            if (translationY > upperThreshold) {
                translationY = upperThreshold;
            }
            child.setTranslationY(translationY);
            if (child.getTranslationY() > target.getHeight() && child.getVisibility() == View.VISIBLE) {
                child.hide();
            } else if (child.getTranslationY() <= target.getHeight() && child.getVisibility() != View.VISIBLE) {
                child.show();
            }
        }
    }

}
