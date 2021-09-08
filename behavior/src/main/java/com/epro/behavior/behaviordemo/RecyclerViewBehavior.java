package com.epro.behavior.behaviordemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zzy
 * @date 2021/9/6
 */

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        LogUtils.e("dependency.getHeight()=" + dependency.getHeight() + "   dependency.getTranslationY()=" + dependency.getTranslationY());
        float height = dependency.getHeight() + dependency.getTranslationY();
        child.setTranslationY((int)height);
        return true;
    }
}
