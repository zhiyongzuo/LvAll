package com.epro.image;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;

/**
 * @author zzy----------------------从别的地方copy过来，没看懂做什么用。。。
 * @date 2020/9/22
 */
public class BigImageView extends View {
    private static final String TAG = "BigImageView";

    private Drawable d;
    private BitmapRegionDecoder mRegionDecoder;
    private int mImageWidth, mImageHeight;
    private Rect mRect = new Rect();

    private Rect mImageRect = new Rect();
    private Bitmap[][] mBitmaps;

    private static BitmapFactory.Options sOptions = new BitmapFactory.Options();
    {
        sOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

//        final TypedArray a = context.obtainStyledAttributes(
//                attrs, R.styleable.BigImageView, defStyleAttr, defStyleRes);
//
//        d = a.getDrawable(R.styleable.BigImageView_img);
//        Bitmap bitmap = ConvertUtils.drawable2Bitmap(d);
//        byte[] bytes = ConvertUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG);
//        InputStream inputStream = ConvertUtils.bytes2InputStream(bytes);
//        setInputStream(inputStream);
    }

    public void setInputStream(InputStream inputStream) {
        try {
            mRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            //下面注释的代码options.outHeight、options.outWidth
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = false;
//            BitmapFactory.decodeStream(inputStream, null, options);
//            mImageHeight = options.outHeight;
//            mImageWidth = options.outWidth;

            mImageHeight = mRegionDecoder.getWidth();
            mImageWidth = mRegionDecoder.getHeight();
            mImageRect = new Rect(0, 0, mImageWidth, mImageHeight);
//            mBitmaps =

            requestLayout();
            invalidate();
        } catch (IOException e) {
            LogUtils.e(e.getMessage());
            e.printStackTrace();
        }
    }

    int downX = 0;
    int downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int curX = (int) event.getX();
                int curY = (int) event.getY();

                int moveX = curX - downX;
                int moveY = curY - downY;

                if (Math.abs(moveX)>1 || Math.abs(moveY)>1) {
                    onMove(moveX, moveY);

                    System.out.println(TAG + " moveX = " + moveX + " curX = " + curX + " downX = " + downX);
                }

                downX = curX;
                downY = curY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private void onMove(int moveX, int moveY) {
        LogUtils.e("mImageHeight=" + mImageHeight + "  mImageWidth=" +mImageWidth + "   getWidth()=" + getWidth() + "   getHeight()=" + getHeight());
        if (mImageWidth > getWidth()) {
            mRect.offset(-moveX, 0);
            checkWidth();
            invalidate();
        }

        if (mImageHeight > getHeight()) {
            mRect.offset(0, -moveY);
            checkHeight();
            invalidate();
        }
    }

    private void checkWidth() {
        Rect rect = mRect;
        if (rect.right > mImageWidth) {
            rect.right = mImageWidth;
            rect.left = mImageWidth - getWidth();
        }

        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    private void checkHeight() {
        Rect rect = mRect;
        if (rect.bottom > mImageHeight) {
            rect.bottom = mImageHeight;
            rect.top = mImageHeight - getHeight();
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getWidth();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtils.e(mRect.toShortString());
        Bitmap bitmap = mRegionDecoder.decodeRegion(mRect, sOptions);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}
