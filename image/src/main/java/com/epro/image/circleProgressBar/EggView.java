package com.epro.image.circleProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author zzy
 * @date 2021/7/12
 */
public class EggView extends View {
    private int left, top, right, bottom;

    public EggView(Context context) {
        this(context, null);
    }

    public EggView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EggView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams();
    }

    public EggView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initParams() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.e(left, top, right, bottom);

        Rect rect = new Rect();
        getDrawingRect(rect);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(18);
//        RectF rectF = new RectF(left, top, right, (bottom - top) / 2);
//        canvas.drawArc(rect.left, rect.top-100, rect.right, rect.bottom+100, 180, 180, false, paint);

        int radius = 100;

        canvas.drawArc((right-left)/2-radius, (bottom-top)/2-radius - radius, (right-left)/2+radius, (bottom-top)/2+radius + radius, 180, 180, false, paint);
        canvas.drawArc((right-left)/2-radius, (bottom-top)/2-radius, (right-left)/2+radius, (bottom-top)/2+radius, 0, 180, false, paint);
        canvas.drawCircle((right-left)/2, (bottom-top)/2, 10, paint);
    }
}
