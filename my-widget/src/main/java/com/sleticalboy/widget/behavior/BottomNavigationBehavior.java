package com.sleticalboy.widget.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * Created by Android Studio.
 * Date: 1/3/18.
 *
 * @author sleticalboy
 */
public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<View> {

    private ObjectAnimator mOut;
    private ObjectAnimator mIn;

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       View child,
                                       View directTargetChild,
                                       View target,
                                       int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  View child,
                                  View target,
                                  int dx,
                                  int dy,
                                  int[] consumed) {
        // 上滑隐藏，下滑显示
        if (dy > 0) {
            if (mOut == null) {
                mOut = ObjectAnimator.ofFloat(child, "translationY", 0, child.getHeight());
                mOut.setDuration(200);
            }
            if (!mOut.isRunning() && child.getTranslationY() <= 0) {
                mOut.start();
            }
        } else if (dy < 0) {
            if (mIn == null) {
                mIn = ObjectAnimator.ofFloat(child, "translationY", child.getHeight(), 0);
                mIn.setDuration(200);
            }
            if (!mIn.isRunning() && child.getTranslationY() >= child.getHeight()) {
                mIn.start();
            }
        }
    }
}
