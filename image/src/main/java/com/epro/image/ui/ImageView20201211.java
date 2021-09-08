package com.epro.image.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.RequiresApi;

/**
 * @author zzy
 * @date 2020/12/11
 */
public class ImageView20201211 extends androidx.appcompat.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener{

    float downX;
    float downY;
    int left, top, right, bottom;

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private Matrix mMatrix;

    public ImageView20201211(Context context) {
        this(context, null);
    }

    public ImageView20201211(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageView20201211(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFloat = detector.getScaleFactor();
                LogUtils.e("scaleFloat:" + scaleFloat);
                System.out.println("before mMatrix:" + GsonUtils.toJson(mMatrix.toString()));

                if(scaleFloat>2 || scaleFloat<0.5) {
                    return true;
                }

                mMatrix.preScale(scaleFloat, scaleFloat);
//                mMatrix.preScale(scaleFloat, scaleFloat, detector.getFocusX(), detector.getFocusY());
                System.out.println("after mMatrix:" + GsonUtils.toJson(mMatrix.toString()));
                setImageMatrix(mMatrix);
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                //缩放开始。该detector是否处理后继的缩放事件。返回false时，不会执行onScale()。
                //return false;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int)distanceX, (int)distanceY);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    public void onGlobalLayout() {
//        mMatrix = new Matrix();
//        setScaleType(ScaleType.MATRIX);
//
//        LogUtils.e("getWidth getHeight:" + getWidth() + "---" + getHeight());
//        int intrinsicWidth = getDrawable().getIntrinsicWidth();
//        int intrinsicHeight = getDrawable().getIntrinsicHeight();
//        int ratio = intrinsicWidth / intrinsicHeight;
//        if(ratio<1) {
//            //宽长
//            mMatrix.preScale(getWidth() * 1f/intrinsicWidth, getWidth() * 1f/intrinsicWidth);
//        } else {
//            //高长
//            mMatrix.preScale(getHeight() * 1f/intrinsicHeight, getHeight() * 1f/intrinsicHeight);
//        }
//        setImageMatrix(mMatrix);
//        System.out.println("mMatrix:" + GsonUtils.toJson(mMatrix.toString()));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //mScaleGestureDetector.onTouchEvent(event);
        //mGestureDetector.onTouchEvent(event);

        //getX event.getX区别
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //getX、getRawX都可以
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //方法111111111method First   移动view内的内容
                float moveX = event.getRawX();
                float moveY = event.getRawY();
                //scrollBy正数时往左,源码最后是-x  -y(负移动距离)
                scrollBy((int)(downX-moveX), (int)(downY-moveY));
                downX = moveX;
                downY = moveY;
                //下面两行打印永不变
                //getLeft():0  getTop():0  getRight():1080  getBottom():2028
                //getWidth():1080   getHeight():2028
                //LogUtils.e("getLeft():" + getLeft() + "  getTop():" + getTop() + "  getRight():" + getRight() + "  getBottom():" + getBottom());
                //LogUtils.e("getWidth():" + getWidth() + "   getHeight():" + getHeight());

//                //方法2222222222      移动view
////                left = (int)(getX() - getWidth()/2);
////                top = (int)(getY() - getHeight()/2);
//                //下面差不多可以，上面不行...
//                left = (int)(event.getRawX() - getWidth()/2);
//                top = (int)(event.getRawY() - getHeight()/2);
//                right = left + getWidth();
//                bottom = top + getHeight();
//                layout(left, top, right, bottom);
//                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
}
