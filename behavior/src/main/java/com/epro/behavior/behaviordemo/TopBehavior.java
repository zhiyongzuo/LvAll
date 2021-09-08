package com.epro.behavior.behaviordemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author zzy
 * @date 2021/9/7
 */
public class TopBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    public TopBehavior() {
    }

    public TopBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        LogUtils.e("dependency.getTranslationY()=" + dependency.getTranslationY() + "  child.getHeight()=" + child.getHeight());

        float finalY = dependency.getTranslationY()==0 ? -child.getHeight() : -child.getHeight()-dependency.getTranslationY();
        if (finalY>0) {
            finalY = 0;
        }
        child.setTranslationY(finalY);
        return true;
    }
}
