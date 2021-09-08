package com.epro.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author zzy----------------------从别的地方copy过来，没看懂做什么用。。。
 * @date 2020/9/22
 */
public class RectTestImageView extends View {
    private static final String TAG = "BigImageView";

    private Drawable d;
    private BitmapRegionDecoder mRegionDecoder;
    private int mImageWidth, mImageHeight;
    private Rect mRect = new Rect();

    private Rect mImageRect = new Rect();
    private Bitmap[][] mBitmaps;
    private Paint mPaint;
    int basicRectWidth = 300;
    int basicRectHeight = 300;
    private Rect mTestRect = new Rect(0, 0, basicRectWidth, basicRectHeight);


    private static BitmapFactory.Options sOptions = new BitmapFactory.Options();
    {
        sOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }

    public RectTestImageView(Context context) {
        this(context, null);
    }

    public RectTestImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectTestImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RectTestImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

//        final TypedArray a = context.obtainStyledAttributes(
//                attrs, R.styleable.BigImageView, defStyleAttr, defStyleRes);
//
//        d = a.getDrawable(R.styleable.BigImageView_img);
//        Bitmap bitmap = ConvertUtils.drawable2Bitmap(d);
//        byte[] bytes = ConvertUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG);
//        InputStream inputStream = ConvertUtils.bytes2InputStream(bytes);
//        setInputStream(inputStream);

        mPaint = new Paint();
//        mPaint.setColor(Color.GREEN);
        mPaint.setColor(Color.argb(100, 1,2,3));
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

            mImageWidth = mRegionDecoder.getWidth();
            mImageHeight = mRegionDecoder.getHeight();
            mImageRect = new Rect(0, 0, mImageWidth, mImageHeight);
            int m = mImageHeight/basicRectHeight + (mImageHeight%basicRectHeight==0 ? 0 : 1);//行数
            int n = mImageWidth/basicRectWidth + (mImageWidth%basicRectWidth==0 ? 0 : 1);//列数
            mBitmaps = new Bitmap[m][n];
            for(int i=0; i<m; i++) {
                for(int j=0; j<n; j++) {
//                    LogUtils.e("行数i=" + i + "  列数j=" + j);
                    Rect temp = new Rect( basicRectWidth*j, basicRectHeight*i, Math.min(basicRectWidth*(j+1), mImageWidth), Math.min(basicRectHeight*(i+1), mImageHeight));
                    mBitmaps[i][j] = mRegionDecoder.decodeRegion(temp, sOptions);
                }
            }

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
//                    mTestRect.offset(moveX, moveY);
//                    checkWidth(mTestRect);
//                    checkHeight(mTestRect);
//                    LogUtils.d("moveX=" + moveX + " moveY=" + moveY + "\nmTestRect=" + mTestRect.toShortString());
//                    invalidate();
                    onMove(moveX, moveY);
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
            checkWidth(mRect);
            invalidate();
        }

        if (mImageHeight > getHeight()) {
            mRect.offset(0, -moveY);
            checkHeight(mRect);
            invalidate();
        }
    }

    private void checkWidth(Rect mRect) {
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

    private void checkHeight(Rect mRect) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtils.e(mRect.toShortString());
        Bitmap bitmap = mRegionDecoder.decodeRegion(mRect, sOptions);
        canvas.drawBitmap(ImageUtils.compressByQuality(bitmap, 5), 0, 0, null);

//        LogUtils.e(mTestRect.toShortString());
//        canvas.drawRect(mTestRect, mPaint);
//        Bitmap bitmap2 = null;
//        try {
//            bitmap2 = mRegionDecoder.decodeRegion(mTestRect, sOptions);
//            canvas.translate(mTestRect.left, mTestRect.top);
////            canvas.drawBitmap(bitmap2, 0, 0, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //绘制3x3色块
//        for(int i=0; i<=2; i++) {
//            for(int j=0; j<=2; j++) {
//                mPaint.setColor(Color.argb(100f, (float)Math.random()*50, (float)Math.random()*10, (float)Math.random()*100));
//                canvas.drawRect(new Rect(basicRectHeight*i, basicRectHeight*j, basicRectHeight*(i+1), basicRectHeight*(j+1)), mPaint);
//            }
//        }

        int visibleLeft = mTestRect.left;
        int visibleRight = mTestRect.right;
        int visibleTop = mTestRect.top;
        int visibleBottom = mTestRect.bottom;

        //测试数310/100 -- 3
        //以下都是从0开始
        int columnStartIndex = visibleLeft/basicRectWidth;//从0开始
        int columnEndIndex = visibleRight/basicRectWidth;
        int rowStartIndex = visibleTop/basicRectHeight;
        int rowEndIndex = visibleBottom/basicRectHeight;
        for(int j=rowStartIndex; j<=rowEndIndex; j++) {
            for(int i=columnStartIndex; i<=columnEndIndex; i++) {
                if(i<0 || j<0 || j>=mBitmaps.length || i>=mBitmaps[j].length) {
                    //移出了边界
                    continue;
                }
                mPaint.setColor(Color.argb(100f, (float)Math.random()*50, (float)Math.random()*10, (float)Math.random()*100));
                Bitmap cubeBitmap = mBitmaps[j][i];
                canvas.drawBitmap(cubeBitmap, i*basicRectWidth, j*basicRectHeight, mPaint);
            }
        }
    }
}
