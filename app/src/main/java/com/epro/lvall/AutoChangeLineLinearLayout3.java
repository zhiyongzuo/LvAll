package com.epro.lvall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/9/5
 */
class AutoChangeLineLinearLayout3 extends LinearLayout {

    public AutoChangeLineLinearLayout3(Context context) {
        super(context);
    }

    public AutoChangeLineLinearLayout3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoChangeLineLinearLayout3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        int totalHeight = 0;
        int currentWidth = 0;
        for(int i=0; i<getChildCount(); i++) {
            //getChildAt(i).getWidth()能获取到吗
            View childView = getChildAt(i);
            //下面的measure 参数不同，结果不一样
//            childView.measure(widthMeasureSpec, heightMeasureSpec);
            //下面两个是一样的
            //20201221  原因可以看textview的onMeasure()源码，  MeasureSpec.UNSPECIFIED和MeasureSpec.AT_MOST效果一致，MeasureSpec.EXACTLY使用的是父类的大小和MeasureSpecMode
//            childView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            if (i==0) {
                totalHeight = childView.getMeasuredHeight();
            }
            currentWidth += childView.getMeasuredWidth();
            if(currentWidth > parentWidth) {
                totalHeight += childView.getMeasuredHeight();
                currentWidth = childView.getMeasuredWidth();
            }
        }

        setMeasuredDimension(parentWidth, totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.e("getMeasuredHeight() : " + getMeasuredHeight());//getMeasuredHeight() : 38

        int parentWidth = r - l;
        int currentLeft = l;
        int currentTop = t;
        View lastChildView = null;

        for(int i=0; i<getChildCount(); i++) {
            View currentChildView = getChildAt(i);
            //第i次总布局的宽度
            int currentLayoutViewWidth = currentChildView.getMeasuredWidth() + (lastChildView==null ? l : lastChildView.getMeasuredWidth() + lastChildView.getLeft());
            if(currentLayoutViewWidth > parentWidth) {
                //换行
                currentLeft = l;
                currentTop = lastChildView==null ? t : lastChildView.getMeasuredHeight() + lastChildView.getTop();
            }
//            Log.e(i + "--layout", "leftOffset-" + currentLeft + "   topOffset-" + currentTop + " right-" + currentLeft + currentChildView.getMeasuredWidth() + " bottom-" + currentTop + currentChildView.getMeasuredHeight());
            currentChildView.layout(currentLeft, currentTop, currentLeft + currentChildView.getMeasuredWidth(), currentTop + currentChildView.getMeasuredHeight());
            currentLeft = currentLayoutViewWidth > parentWidth ? l+currentChildView.getMeasuredWidth() : currentLayoutViewWidth;

            lastChildView = currentChildView;
        }
    }
}
