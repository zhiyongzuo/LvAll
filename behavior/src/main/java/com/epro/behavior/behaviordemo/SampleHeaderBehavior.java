package com.epro.behavior.behaviordemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zzy
 * @date 2021/9/6
 */
public class SampleHeaderBehavior extends CoordinatorLayout.Behavior<TextView> {

    //界面整体向上滑动，达到列表可滑动的临界点
    private boolean upReach;
    //列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private boolean downReach;
    //列表上一个全部可见的item位置
    private int lastPosition = -1;

    public SampleHeaderBehavior() { }

    public SampleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, TextView child, MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downReach = false;
                upReach = false;
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }
//
//
//    @Override
//    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        if (target instanceof RecyclerView) {
//            RecyclerView list = (RecyclerView) target;
//            //列表第一个全部可见Item的位置
//            int pos = ((LinearLayoutManager) list.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
//            if (pos == 0 && pos < lastPosition) {
//                downReach = true;
//            }
//
//            if (canScroll(child, dy) && pos == 0) {
//                float finalY = child.getTranslationY() - dy;
//                if (finalY < -child.getHeight()) {
//                    finalY = -child.getHeight();
//                    upReach = true;
//                } else if (finalY > 0) {
//                    finalY = 0;
//                }
//                child.setTranslationY(finalY);
//                consumed[1] = dy;
//            }
//            lastPosition = pos;
//        }
//    }
//
    private boolean canScroll(View child, float scrollY) {
        if (scrollY > 0 && child.getTranslationY() == -child.getHeight() && !upReach) {
            return false;
        }

        if (downReach) {
            return false;
        }

        return true;
    }



    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //recyclerview向上滑动，dy>0  --- child.getTranslationY()负值越来越大
        //recyclerview向下滑动，dy<0  --- child.getTranslationY()负值越来越小
        RecyclerView list = (RecyclerView) target;
        //列表第一个全部可见Item的位置
        int pos = ((LinearLayoutManager) list.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

        LogUtils.e("child.getHeight()=" + child.getHeight() + "   child.getTranslationY()=" + child.getTranslationY() +"  dy=" + dy
                + "   child.getTranslationY()-dy=" + (child.getTranslationY()-dy));
        float finalY = child.getTranslationY()-dy;
        if(finalY<=0 && -finalY<child.getHeight()-300 && pos==0) {
            child.setTranslationY(finalY);
            //没有下面代码，recyclerview只能滑一个item
            //pos==0时TextView滑动,RecyclerView不滑动，而是依赖TextView移动.
            // 让CoordinatorLayout消费滑动事件
            consumed[1]=dy;
        }
    }
}
