package com.epro.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ThreadUtils;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author zzy
 */
public class OverrideFirstZoomImageView extends androidx.appcompat.widget.AppCompatImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private Matrix mMatrix;
    private float scaleFloat = 0.3f;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private float MAX_SCALE_FLOAT = 3f;
    private float MIN_SCALE_FLOAT = 0.5f;
    private final float BIGGER = 1.37f;
    final float SMALL = 0.53f;
    private BitmapRegionDecoder mBitmapRegionDecoder;
    private int mOriginalDrawableHeight;
    private int mOriginalDrawableWidth;
    private Bitmap[][] originalBitmapArrays;
    private int splitScreenCount = 5;
    private int rowSpan, columnSpan;
    private int originalWidthCount,originalHeightCount;
    private Paint mPaint;
    private boolean isFirstRunning = true;

    private RectF visibleOriginalRect;
    //缩放比例
    float scaleFactorFloat = 1;

    int DEFAULT_IN_SAMPLE_SIZE = 8;

    public OverrideFirstZoomImageView(Context context) {
        this(context, null);
    }

    public OverrideFirstZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OverrideFirstZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OverrideFirstZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        mPaint = new Paint();

        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //onDown方法返回true， onDoubleTap方法才会回调，这是为什么。。zzy20200924
                // onTouch返回true就可以！!
                //有两种方法可以让onDoubleTap回调。1.onTouch返回true。2.onDown返回true。      3.onTouchEvent返回true好像也行
                LogUtils.e("OverrideFirstZoomImageView onDoubleTap");
                if(getScaleFloat() > MAX_SCALE_FLOAT + MIN_SCALE_FLOAT / 2) {
                    mMatrix.postScale(SMALL, SMALL, e.getX(), e.getY());
                } else {
                    mMatrix.postScale(BIGGER, BIGGER, e.getX(), e.getY());
                }
                checkBorderAndCenterWhenScale();
                setImageMatrix(mMatrix);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                visibleOriginalRect.offset(distanceX, distanceY);
                checkBorder();
                drawBitmap();
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                LogUtils.e("OverrideFirstZoomImageView onSingleTapConfirmed");
                return true;
            }
        });
    }

    private void checkBorder() {
        if(visibleOriginalRect.left<0) {
            visibleOriginalRect.left=0;
            visibleOriginalRect.right = getWidth() * scaleFactorFloat;
        }
        if(visibleOriginalRect.top<0) {
            visibleOriginalRect.top=0;
            visibleOriginalRect.bottom = getHeight() * scaleFactorFloat;
        }
        if(visibleOriginalRect.right>mOriginalDrawableWidth) {
            visibleOriginalRect.left = mOriginalDrawableWidth - getWidth()  * scaleFactorFloat;
            visibleOriginalRect.right=mOriginalDrawableWidth;
        }
        if(visibleOriginalRect.bottom>mOriginalDrawableHeight) {
            visibleOriginalRect.top = mOriginalDrawableHeight - getHeight() * scaleFactorFloat;
            visibleOriginalRect.bottom=mOriginalDrawableHeight;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void setInputStream(final InputStream inputStream) {
        ThreadUtils.executeBySingle(new ThreadUtils.Task<Drawable>() {
            @Override
            public Drawable doInBackground() throws Throwable {
                //为什么在这里 getWidth() getMeasuredWidth()都是0
                    LogUtils.e("setInputStream doInBackground ");
                try {
                    mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);

                    mOriginalDrawableWidth = mBitmapRegionDecoder.getWidth();
                    mOriginalDrawableHeight = mBitmapRegionDecoder.getHeight();

                    //Rect mOriginalRect = new Rect(0 , 0, mOriginalDrawableWidth, mOriginalDrawableHeight);
                    //Bitmap originalBitmap = mBitmapRegionDecoder.decodeRegion(mOriginalRect, null);
                    rowSpan = ScreenUtils.getScreenWidth() / splitScreenCount;
                    columnSpan = ScreenUtils.getScreenHeight() / splitScreenCount;
                    Rect tempRect;
                    originalWidthCount = mOriginalDrawableWidth/rowSpan;
                    originalHeightCount = mOriginalDrawableHeight/columnSpan;
                    //20201224下面太耗时间了，church1大图加载不出来
//                    originalBitmapArrays = new Bitmap[originalWidthCount][originalHeightCount];
//                    for(int i=0; i<originalWidthCount; i++) {
//                        for(int j=0; j<originalHeightCount; j++) {
//                            tempRect = new Rect((int)(i*rowSpan), (int)(j*columnSpan), (int)((i+1)*rowSpan), (int)((j+1)*columnSpan));
//                            originalBitmapArrays[i][j] = mBitmapRegionDecoder.decodeRegion(tempRect, null);
//                        }
//                    }


                    Rect mRect = new Rect(0, 0, mOriginalDrawableWidth, mOriginalDrawableHeight);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = DEFAULT_IN_SAMPLE_SIZE;
                    Bitmap bitmap = mBitmapRegionDecoder.decodeRegion(mRect, options);
                    bitmap = ImageUtils.compressByQuality(bitmap, 30);
                    return ConvertUtils.bitmap2Drawable(bitmap);
                } catch (IOException e) {
                    LogUtils.e(e.getMessage());
                    e.printStackTrace();
                }
               return null;
            }

            @Override
            public void onSuccess(Drawable mDrawable) {
                if(mDrawable==null) {
                    return;
                }
//                setScaleType(ScaleType.FIT_CENTER);
//                setImageDrawable(mDrawable);
//                LogUtils.e("setInputStream mDrawable.getIntrinsicWidth()=" + mDrawable.getIntrinsicWidth() + "  mDrawable.getIntrinsicHeight():" + mDrawable.getIntrinsicHeight());
                drawBitmap();
            }

            @Override
            public void onCancel() {
                LogUtils.e("setInputStream onCancel");
            }

            @Override
            public void onFail(Throwable t) {
                LogUtils.e("setInputStream onFail:" + t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if(!isFirstRunning) {
            return;
        }
        isFirstRunning = false;
        setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();

        int ivWidth = getWidth();
        int ivHeight = getHeight();

        int drawableWidth = getDrawable().getIntrinsicWidth();
        int drawableHeight = getDrawable().getIntrinsicHeight();
        System.out.println("onGlobalLayout "
                + "ivWidth=" + ivWidth   + " " + "ivHeight=" + ivHeight + " "
                + "drawableWidth=" + drawableWidth   + " " + "drawableHeight=" + drawableHeight + " ");
        if(drawableWidth/drawableHeight>1) {
            //横长的图width > height
            float scaleFloat = ivWidth * 1f / drawableWidth;//0.33333334
            mMatrix.preScale(scaleFloat, scaleFloat);
        } else {
            //竖长的图
            float scaleFloat = ivHeight * 1.0f / drawableHeight;
            mMatrix.preScale(scaleFloat, scaleFloat);
        }

        MIN_SCALE_FLOAT = getScaleFloat();
        System.out.println("onGlobalLayout before " + getScaleFloat());//before 0.33333334
        setImageMatrix(mMatrix);
        System.out.println("onGlobalLayout after " + getScaleFloat());// after 0.33333334
        visibleOriginalRect = new RectF(0 , 0, getWidth(), getHeight());
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        LogUtils.e("scaleFactorFloat before=" + scaleFactorFloat + "  detector=" + detector.getScaleFactor());
        scaleFactorFloat *= 1/detector.getScaleFactor();
        LogUtils.e("scaleFactorFloat after=" + scaleFactorFloat);
//        //下面preScale postScale看起来没什么区别
////        mMatrix.postScale(scaleFactorFloat, scaleFactorFloat, detector.getFocusX(), detector.getFocusY());
//        float totalScaleFloat = getScaleFloat();
//        if ((totalScaleFloat<MAX_SCALE_FLOAT && scaleFactorFloat>1.0f) || (totalScaleFloat>MIN_SCALE_FLOAT && scaleFactorFloat<1.0f)) {
//            if(totalScaleFloat * scaleFactorFloat > MAX_SCALE_FLOAT) {
//                //如果缩放大于最大值
//                scaleFactorFloat = MAX_SCALE_FLOAT / totalScaleFloat;
//            }
//            if(totalScaleFloat * scaleFactorFloat < MIN_SCALE_FLOAT) {
//                //如果缩放小于最小值
//                scaleFactorFloat = MIN_SCALE_FLOAT / totalScaleFloat;
//            }
//
//            mMatrix.preScale(scaleFactorFloat, scaleFactorFloat, detector.getFocusX(), detector.getFocusY());
//            checkBorderAndCenterWhenScale();
//            setImageMatrix(mMatrix);
//        }
//        //getScaleFloat()等于上一次的getScaleFloat() * scaleFactorFloat
////        LogUtils.e("OverrideFirstZoomImageView onScale scaleFactorFloat=" + scaleFactorFloat + "   getScaleFloat=" + getScaleFloat());

        mMatrix.preScale(scaleFactorFloat, scaleFactorFloat, detector.getFocusX(), detector.getFocusY());
        mMatrix.mapRect(visibleOriginalRect);
        if(Double.isNaN(visibleOriginalRect.left)) {
            visibleOriginalRect = new RectF(0, 0, getWidth(), getHeight());
        }
        if(visibleOriginalRect.left==visibleOriginalRect.right || visibleOriginalRect.top==visibleOriginalRect.bottom) {
            return true;
        }
        System.out.println("onScale visibleOriginalRect.toString()1:" + visibleOriginalRect.toString());
        checkBorder();
        System.out.println("onScale visibleOriginalRect.toString()2:" + visibleOriginalRect.toString());
        drawBitmap();
        return true;
    }

    public void checkBorderAndCenterWhenScale() {
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        float toTranslateX = 0.0f;
        float toTranslateY = 0.0f;

        RectF matrixRectF = new RectF();
        matrixRectF.set(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        mMatrix.mapRect(matrixRectF);

        if (matrixRectF.width()>=screenWidth) {
            //rectF宽度大于屏幕宽度
            if(matrixRectF.left>0) {
                toTranslateX = -matrixRectF.left;
                LogUtils.e("宽有问题1" + toTranslateX);
            }
            if(matrixRectF.right<screenWidth) {
                toTranslateX = screenWidth - matrixRectF.right;
                LogUtils.e("宽有问题2" + toTranslateX);
            }
        }

        if(matrixRectF.height()>=screenHeight) {
            if(matrixRectF.top>0) {
                toTranslateY = -matrixRectF.top;
                LogUtils.e("高有问题1" + toTranslateY);
            }
            if(matrixRectF.bottom<screenHeight) {
                toTranslateY = screenHeight - matrixRectF.bottom;
                LogUtils.e("高有问题2" + toTranslateY);
            }
        }
        LogUtils.e(matrixRectF.toString());
        mMatrix.postTranslate(toTranslateX, toTranslateY);
    }

    public void drawBitmap() {
        ThreadUtils.executeBySingle(new ThreadUtils.Task<Drawable>() {
            @Override
            public Drawable doInBackground() throws Throwable {
                Bitmap tempBitmap = mBitmapRegionDecoder.decodeRegion(new Rect((int)visibleOriginalRect.left, (int)visibleOriginalRect.top, (int)visibleOriginalRect.right, (int)visibleOriginalRect.bottom), null);
                return ConvertUtils.bitmap2Drawable(tempBitmap);
            }

            @Override
            public void onSuccess(Drawable drawable) {
                setScaleType(ScaleType.FIT_CENTER);
                setImageDrawable(drawable);
            }

            @Override
            public void onCancel() {
                LogUtils.e("drawBitmap onCancel");
            }

            @Override
            public void onFail(Throwable t) {
                LogUtils.e("drawBitmap onFail" + t.getMessage());
            }
        });
    }

    /**
     * 总共scale的大小
     * @return
     */
    public float getScaleFloat() {
        float[] scaleFloat = new float[9];
        mMatrix.getValues(scaleFloat);
        return scaleFloat[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        if(event.getPointerCount()>1) {
            mScaleGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        LogUtils.e("onDraw");
        if(visibleOriginalRect==null) {
            return;
        }
        int visibleLeft = (int)visibleOriginalRect.left;
        int visibleTop = (int)visibleOriginalRect.top;
        int visibleRight = (int)visibleOriginalRect.right;
        int visibleBottom = (int)visibleOriginalRect.bottom;

//        System.out.println("onDraw visibleLeft:" + visibleLeft + " visibleTop:" + visibleTop + " visibleRight:" + visibleRight + " visibleBottom:" + visibleBottom);

//        rowSpan *= scaleFactorFloat;
//        columnSpan *= scaleFactorFloat;
//
//        int rowStart = visibleLeft/rowSpan;
//        int rowEnd = rowStart + splitScreenCount - 1;
//        int columnStart = visibleTop/columnSpan;
//        int columnEnd = columnStart + splitScreenCount - 1;
//
//        //rowSpan=216   columnSpan=456
//        //System.out.println("onDraw rowSpan=" + rowSpan + "   columnSpan=" + columnSpan);
//        //rowStart:0 rowEnd:5 columnStart:0 columnEnd:9
//        //System.out.println("onDraw rowStart:" + rowStart + " rowEnd:" + rowEnd + " columnStart:" + columnStart + " columnEnd:" + columnEnd);
//
////        for(int i=rowStart; i<=rowEnd; i++) {
////            for(int j=columnStart; j<=columnEnd; j++) {
////                Bitmap tempBitmap = originalBitmapArrays[i][j];
////                if (tempBitmap!=null && !tempBitmap.isRecycled()) {
////                    //mPaint.setColor(Color.argb((float)Math.random() * 100f, (float)Math.random()*50, (float)Math.random()*10, (float)Math.random()*100));
////                    canvas.drawBitmap(tempBitmap, rowSpan * (i-rowStart), columnSpan * (j-columnStart), mPaint);
////                }
////            }
////        }
        super.onDraw(canvas);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        LogUtils.e("onMeasure");
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        LogUtils.e("onLayout");
//        super.onLayout(changed, left, top, right, bottom);
//    }
}
