package com.epro.lvall.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/2/5
 */
public class AutoChangeLineLinearLayout2 extends LinearLayout {
    public AutoChangeLineLinearLayout2(Context context) {
        super(context);
    }

    public AutoChangeLineLinearLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoChangeLineLinearLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoChangeLineLinearLayout2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int totalHeight = 0;
        int currentLineWidth = 0;
        for(int i=0; i<getChildCount(); i++) {
            View currentView = getChildAt(i);
            if(currentLineWidth + currentView.getMeasuredWidth() >= widthSize) {
                currentLineWidth = currentView.getMeasuredWidth();
                totalHeight += currentView.getMeasuredHeight();
            } else {
                currentLineWidth += currentView.getMeasuredWidth();
            }

        }

        setMeasuredDimension(widthSize, heightMode==MeasureSpec.EXACTLY ? heightSize : totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.e("getMeasuredHeight() : " + getMeasuredHeight());
//        super.onLayout(changed, l, t, r, b);

        int totalHeight = 0;
        int currentLineWidth = 0;
        for(int i=0; i<getChildCount(); i++) {
            View currentView = getChildAt(i);
            if(currentLineWidth + currentView.getMeasuredWidth() >= getWidth()) {
                currentLineWidth = currentView.getMeasuredWidth();
                totalHeight += currentView.getMeasuredHeight();
            } else {
                currentLineWidth += currentView.getMeasuredWidth();
            }

        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
