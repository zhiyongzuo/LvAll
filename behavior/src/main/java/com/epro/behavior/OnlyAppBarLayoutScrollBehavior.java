package com.epro.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * @author zzy
 * @date 2021/9/6
 */
public class OnlyAppBarLayoutScrollBehavior extends AppBarLayout.Behavior {

    public OnlyAppBarLayoutScrollBehavior() {
    }

    public OnlyAppBarLayoutScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull View directTargetChild, View target, int nestedScrollAxes, int type) {
        return nestedScrollAxes==ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        LogUtils.e("Math.abs(child.getTranslationY())=" + Math.abs(child.getTranslationY())
                + "   child.getTotalScrollRange()- ScreenUtils.getScreenHeight()=" + (child.getTotalScrollRange()-ScreenUtils.getScreenHeight()));
        if(Math.abs(child.getTranslationY())<=child.getTotalScrollRange()- ScreenUtils.getScreenHeight()) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        } else {
            consumed[1]=dy;
        }
    }
}
