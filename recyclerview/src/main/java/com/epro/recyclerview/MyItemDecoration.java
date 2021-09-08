package com.epro.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zzy
 * @date 2019/10/20
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Xfermode xfermode;
    private LinearGradient linearGradient;

    public MyItemDecoration() {
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 100.0f, new int[]{0, Color.GREEN}, null, Shader.TileMode.CLAMP);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        LogUtils.e("onDraw");
        super.onDraw(c, parent, state);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        for (int i=0; i<parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            c.drawText("onDraw", 50, child.getTop() + child.getHeight() / 2, paint);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        LogUtils.e("onDrawOver");
        super.onDrawOver(c, parent, state);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        for (int i=0; i<parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            c.drawText("onDraw i", 100, child.getTop() + child.getHeight() / 2, paint);
        }

        Paint mPaint = new Paint();
        mPaint.setXfermode(xfermode);
        mPaint.setShader(linearGradient);
        c.drawRect(0.0f, 0.0f, parent.getRight(), 200.0f, mPaint);
        mPaint.setXfermode(null);
//        c.restoreToCount(layerId);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        LogUtils.e("getItemOffsets");
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(100, 0, 0, 4);
    }
}
