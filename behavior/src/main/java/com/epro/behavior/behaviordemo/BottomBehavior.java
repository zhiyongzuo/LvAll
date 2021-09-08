package com.epro.behavior.behaviordemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author zzy
 * @date 2021/9/7
 */
public class BottomBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {
    public BottomBehavior() {
    }

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RelativeLayout child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RelativeLayout child, @NonNull View dependency) {
        LogUtils.e("dependency.getHeight()-child.getHeight()=" + (dependency.getHeight()-child.getHeight()) + "    child.getTranslationY()=" + child.getTranslationY());
        //下面注释的代码会使RelativeLayout移动的很快
//        float finalY = dependency.getTranslationY()==0 ? dependency.getHeight()-child.getHeight() : child.getTranslationY()+dependency.getTranslationY();
        float finalY = dependency.getTranslationY()==0 ? dependency.getHeight()-child.getHeight() : dependency.getHeight()-child.getHeight()+dependency.getTranslationY();
        if(Math.abs(finalY)<=child.getHeight()) {
            finalY = child.getHeight();
        }
        child.setTranslationY(finalY);
        return true;
    }
}
