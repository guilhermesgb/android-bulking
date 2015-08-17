package com.guilhermesgb.robospiceretrofit.view.behaviors;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings("unused")
public class ScrollAwareFABFadeBehavior extends FloatingActionButton.Behavior {

    public ScrollAwareFABFadeBehavior(Context context, AttributeSet attrs) {
        super();
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
        super.onNestedScroll(parent, child, target, dxConsumed,
                dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }

}
