package com.epro.dispatch.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/8/3
 */
public class DispatchTextView extends TextView {

    public DispatchTextView(Context context) {
        super(context);
    }

    public DispatchTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.e("DispatchView-dispatchTouchEvent-" + event.toString());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("DispatchView-onTouchEvent-" + event.toString());
        return super.onTouchEvent(event);
    }
}
