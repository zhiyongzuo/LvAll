package com.epro.lvall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/6/1
 */
public class CustomMoveView extends TextView {
    private int r, l, t, b;

    public CustomMoveView(Context context) {
        super(context);
    }

    public CustomMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                l = (int) (x + getTranslationX() - getWidth() / 2 + getLeft());
                t = (int) (y + getTranslationY() - getHeight() / 2 + getTop());
                r = l + getWidth();
                b = t + getHeight();

                layout(l, t, r, b);
                break;
            case MotionEvent.ACTION_UP:
                t = (int) (y + getTranslationY() - getHeight() / 2 + getTop());
                r = ScreenUtils.getScreenWidth() - getWidth() / 2;
                l = r - getWidth();
                b = t + getHeight();

                layout(l, t, r, b);
                break;
            default:
                break;
        }
        return true;
    }
}
