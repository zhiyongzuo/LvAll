package com.epro.lvall;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;

import java.math.BigDecimal;
import java.util.logging.Logger;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2019/12/21
 */
public class AutoChangeLineLinearLayout extends LinearLayout {
    public AutoChangeLineLinearLayout(Context context) {
        super(context);
    }

    public AutoChangeLineLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoChangeLineLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量过程-----
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int allHeight = 0;
        int lastLineWidth = 0;


        for(int i=0; i<getChildCount(); i++) {
            View currentView = getChildAt(i);
            currentView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

            int currentWidth = currentView.getMeasuredWidth();
            int currentHeight = currentView.getMeasuredHeight();
            if(lastLineWidth + currentWidth > MeasureSpec.getSize(widthMeasureSpec)) {
                lastLineWidth = currentWidth;
                allHeight += currentHeight;
            } else {
                lastLineWidth += currentWidth;
                allHeight = Math.max(currentHeight, allHeight);
            }
        }

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), allHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.e("getMeasuredHeight() : " + getMeasuredHeight());//getMeasuredHeight() : 76
        int viewWidth = r - l;
        int leftOffset = getPaddingLeft();
        int topOffset = getPaddingTop();
        Log.e("11", "leftOffset-" + leftOffset + "   topOffset-" + topOffset);
        Log.e("viewWidth", "viewWidth-" + viewWidth);

        //上一个的宽度
        int lastWidth = 0;

        for(int i=0; i<getChildCount(); i++) {
            View childView = getChildAt(i);

            int currentWidth = childView.getMeasuredWidth();
            int currentHeight = childView.getMeasuredHeight();

            if (i!=0) {
                //非第一个
                if(leftOffset + lastWidth + currentWidth > viewWidth) {
                    leftOffset = 0;
                    topOffset += currentHeight;
                } else {
                    leftOffset += lastWidth;
                }
            }
            lastWidth = currentWidth;

            int left = leftOffset;
            int top = topOffset;
            int right = leftOffset + childView.getMeasuredWidth();
            int bottom =  topOffset + childView.getMeasuredHeight();
            Log.e("22", "leftOffset-" + leftOffset + "   topOffset-" + topOffset + " right-" + right + " bottom-" + bottom);
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/11: leftOffset-0   topOffset-0
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/viewWidth: viewWidth-1080
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/22: leftOffset-0   topOffset-0 right-192 bottom-38
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/22: leftOffset-192   topOffset-0 right-528 bottom-38
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/22: leftOffset-528   topOffset-0 right-976 bottom-38
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/22: leftOffset-0   topOffset-38 right-464 bottom-76
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/22: leftOffset-464   topOffset-38 right-720 bottom-76
            //09-05 18:45:53.459 12749-12749/com.epro.lvall E/22: leftOffset-720   topOffset-38 right-776 bottom-76
            childView.layout(left, top, right, bottom);
        }
    }
}
