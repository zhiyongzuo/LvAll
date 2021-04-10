package com.epro.lvall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2021/2/23
 */
public class AutoChangeLineLinearLayout5 extends LinearLayout {

    public AutoChangeLineLinearLayout5(Context context) {
        super(context);
    }

    public AutoChangeLineLinearLayout5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoChangeLineLinearLayout5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoChangeLineLinearLayout5(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int currentWidthSize = 0;
        int currentHeightSize = 0;

        for(int i=0; i<getChildCount(); i++) {
            View view = getChildAt(i);
            //view.measure(MeasureSpec.makeMeasureSpec(parentWidthSize, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(parentHeightSize, MeasureSpec.UNSPECIFIED));
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            if(currentWidthSize + view.getMeasuredWidth() > ScreenUtils.getScreenWidth()) {
                currentWidthSize = view.getMeasuredWidth();
                currentHeightSize += view.getMeasuredHeight();
            } else {
                currentWidthSize += view.getMeasuredWidth();
                if(currentHeightSize==0) {
                    currentHeightSize += view.getMeasuredHeight();
                }
            }

            //LogUtils.e(i + "    currentHeightSize=" + currentHeightSize);
        }
        setMeasuredDimension(ScreenUtils.getScreenWidth(), currentHeightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentLeft = l;
        int currentTop = t;

        for(int i=0; i<getChildCount(); i++) {
            View view = getChildAt(i);
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            LogUtils.e(i + "    viewWidth=" + viewWidth  + "   currentLeft=" + currentLeft + "   ScreenUtils.getScreenWidth()=" + ScreenUtils.getScreenWidth());
            if(viewWidth + currentLeft > ScreenUtils.getScreenWidth()) {
                currentLeft = l;
                currentTop += viewHeight;
            } else {
                currentLeft += viewWidth;
            }
            LogUtils.e(i + "    currentTop=" + currentTop);
            view.layout(currentLeft, currentTop, currentLeft+viewWidth, currentTop+viewHeight);
        }
    }
}
