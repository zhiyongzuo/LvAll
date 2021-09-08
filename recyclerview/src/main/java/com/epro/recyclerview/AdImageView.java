package com.epro.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

/**
 * @author zzy
 * @date 2019/12/22
 */
public class AdImageView extends AppCompatImageView {
    private Bitmap drawBitmap;
    private int mDy;

    private RectF mBitmapRectF;


    public AdImageView(Context context) {
        super(context);
    }

    public AdImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mMinDy;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mMinDy = h;
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        drawBitmap = drawableToBitmap(drawable);
        mBitmapRectF = new RectF(0, 0,
                w,
                drawBitmap.getHeight() * w / drawBitmap.getWidth());

    }

//    public void setDy(int dy) {
//        this.dy = dy;
//
//        if(dy <=0) {
//            this.dy=0;
//        }
//
//        invalidate();
//    }

    public void setDy(int dy) {

        if (getDrawable() == null) {
            return;
        }
        mDy = dy - mMinDy;
        if (mDy <= 0) {
            mDy = 0;
        }
        if (mDy > mBitmapRectF.height() - mMinDy) {
            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(drawBitmap==null) {
            drawBitmap = drawableToBitmap(getDrawable());
            return;
        }
        canvas.translate(0, -mDy);
//        canvas.drawBitmap(drawBitmap, null, new RectF(0, 0, drawBitmap.getWidth(), drawBitmap.getHeight()), null);
        canvas.drawBitmap(drawBitmap, null, mBitmapRectF, null);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ?
                                    Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }
}
