package com.epro.textviewsize;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class DrawTextView extends View {
    private Paint mPaint ;
    private String text = "英勇青铜5+abcdefg";
    public DrawTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 初始化画笔设置
     */
    private void initPaint() {
        Context c = getContext();
        Resources r;

        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#FF4081"));
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, r.getDisplayMetrics()));
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(text, 0 ,getHeight()/2 ,mPaint);

    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST){
//            setMeasuredDimension(300,300);
//        }else  if (wSpecMode == MeasureSpec.AT_MOST){
//            setMeasuredDimension(300,hSpecSize);
//        }else if (hSpecMode ==  MeasureSpec.AT_MOST) {
//            setMeasuredDimension(wSpecSize,300);
//        }
    }
}