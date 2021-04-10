package com.epro.lvall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/8/2
 */
public class DispatchView extends TextView {
    public DispatchView(Context context) {
        super(context);
    }

    public DispatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("DispatchView-event.getAction()" + event.getAction());
        return false;
    }
}
