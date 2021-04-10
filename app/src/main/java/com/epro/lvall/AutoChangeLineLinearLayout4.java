package com.epro.lvall;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/12/18
 *
 * 这次重写犯的错误
 * 1.在onMeasured()方法中获取childView的宽度和高度，参数widthMeasuredSpec、heightMeasuredSpec用错了
 * 2.没有赋予默认的高度
 * 3.在onLayout()方法中获取宽度错误，用了getWidth()...
 */
public class AutoChangeLineLinearLayout4 extends LinearLayout {

    private int screenWidth;

    public AutoChangeLineLinearLayout4(Context context) {
        this(context, null);
    }

    public AutoChangeLineLinearLayout4(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoChangeLineLinearLayout4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.e("AutoChangeLineLinearLayout4 onMeasure");
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        int totalMeasuredWidth = 0;
        int totalMeasuredHeight = 0;
        screenWidth = ScreenUtils.getScreenWidth();

        for(int i=0; i<getChildCount(); i++) {
            View childView = getChildAt(i);
            //重写这个版本时下面搞错了
            childView.measure(widthMeasureSpec, heightMeasureSpec);
            //childView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

            int childMeasuredWidth = childView.getMeasuredWidth();
            int childMeasuredHeight = childView.getMeasuredHeight();
            //重写这个版本时忘了这个
            if(i==0) {
                totalMeasuredHeight = childMeasuredHeight;
                //childView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED); 情况下   childMeasuredWidth=144   childMeasuredHeight=33
                //childView.measure(widthMeasureSpec, heightMeasureSpec);              情况下   childMeasuredWidth=1080   childMeasuredHeight=33
                LogUtils.e("childMeasuredWidth=" + childMeasuredWidth + "   childMeasuredHeight=" + childMeasuredHeight);
            }
            if(totalMeasuredWidth+childMeasuredWidth>screenWidth) {
                totalMeasuredWidth = childMeasuredWidth;
                totalMeasuredHeight += childMeasuredHeight;
            } else {
                totalMeasuredWidth += childMeasuredWidth;
            }
        }
        setMeasuredDimension(parentWidth, totalMeasuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.e("AutoChangeLineLinearLayout4 onLayout");
        int left = 0;
        int top = 0;

        for(int i=0; i<getChildCount(); i++) {
            View childView = getChildAt(i);
            int childMeasuredWidth = childView.getMeasuredWidth();
            int childMeasuredHeight = childView.getMeasuredHeight();

            if(left + childMeasuredWidth > screenWidth) {
                //换行
                top += childMeasuredHeight;
                childView.layout(l, top, l+childMeasuredWidth, top+childMeasuredHeight);
                left = childMeasuredWidth;
            } else {
                childView.layout(left, top, left+childMeasuredWidth, top + childMeasuredHeight);
                left += childMeasuredWidth;
            }
            //重写这个版本时用了childView.getWidth()，一直是0....
            LogUtils.e("childView.getWidth();:" + childView.getWidth());//打印是0
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.e("AutoChangeLineLinearLayout4 onDraw");
    }
}
