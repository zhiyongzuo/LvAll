package com.epro.image.circleProgressBar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.epro.image.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatTextView;


/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/2/10
 *     desc  : 自定义进度条，新芽，沙丘大学下载进度条
 *     revise: 参考案例：夏安明博客http://blog.csdn.net/xiaanming/article/details/10298163
 *             案例地址：https://github.com/yangchong211
 * </pre>
 */
public class CircleProgressbar extends AppCompatTextView {

    /*
     * invalidate() postInvalidate()
     *      共同点：都是调用onDraw()方法，然后去达到重绘view的目的
     *      区别：invalidate()用于主线程，postInvalidate()用于子线程
     * requestLayout()
     *      也可以达到重绘view的目的，但是与前两者不同，它会先调用onLayout()重新排版，再调用ondraw()方法。
     */

    /**
     * 外部轮廓的颜色。
     */
    private int outLineColor = Color.BLACK;

    /**
     * 外部轮廓的宽度。
     */
    private int outLineWidth = 0;

    /**
     * 内部圆的颜色。
     */
    private ColorStateList inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
    /**
     * 中心圆的颜色。
     */
    private int circleColor;

    /**
     * 进度条的颜色。
     */
    private int progressLineColor = Color.BLUE;

    /**
     * 进度条的宽度。
     */
//    private int progressLineWidth = 16;
    private int progressLineWidth = AdaptScreenUtils.pt2Px(10);;

    /**
     * 画笔。
     */
    private Paint mPaint = new Paint();

    /**
     * 进度条的矩形区域。
     */
    private RectF mArcRect = new RectF();

    /**
     * 进度。
     */
    private int progress = 50;
    /**
     * 进度条类型。
     */
    private int mProgressType = ProgressBarUtils.ProgressType.COUNT_BACK;
    /**
     * 进度倒计时时间。
     */
    private long timeMillis = 0;

    /**
     * View的显示区域。
     */
    final Rect bounds = new Rect();
    /**
     * 进度条通知。
     */
    private OnCircleProgressListener mCountdownProgressListener;
    /**
     * Listener what。
     */
    private int listenerWhat = 0;


    private int innerCircleColor;
    private int outerCircleColor;
    private int innerProgressLineWidth;

    public CircleProgressbar(Context context) {
        this(context, null);
    }

    public CircleProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    /**
     * 初始化。
     *
     * @param context      上下文。
     * @param attributeSet 属性。
     */
    private void initialize(Context context, AttributeSet attributeSet) {
        mPaint.setAntiAlias(true);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleProgressbar);
        if (typedArray.hasValue(R.styleable.CircleProgressbar_circle_color)){
            inCircleColors = typedArray.getColorStateList(R.styleable.CircleProgressbar_circle_color);
        } else{
            inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
        }
        innerCircleColor = typedArray.getColor(R.styleable.CircleProgressbar_inner_circle_color, Color.TRANSPARENT);
        outerCircleColor = typedArray.getColor(R.styleable.CircleProgressbar_outer_circle_color, Color.TRANSPARENT);

        innerProgressLineWidth = (int) typedArray.getDimension(R.styleable.CircleProgressbar_inner_circle_width, 0);
        progressLineWidth = (int) typedArray.getDimension(R.styleable.CircleProgressbar_outer_circle_width, 0);

//        circleColor = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        circleColor = inCircleColors.getColorForState(getDrawableState(), Color.WHITE);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取view的边界
        getDrawingRect(bounds);

        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = size / 2;
        //LogUtils.e("outerRadius=" + outerRadius);//我的手机77.0

//        //画内部背景
//        int circleColor = inCircleColors.getColorForState(getDrawableState(), 0);
//        mPaint.setStyle(Paint.Style.FILL);
////        mPaint.setColor(circleColor);
//        mPaint.setColor(Color.WHITE);
////        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth, mPaint);
//        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - progressLineWidth, mPaint);

//        //画边框圆
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(outLineWidth);
////        mPaint.setColor(outLineColor);
//        mPaint.setColor(Color.WHITE);
//        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth / 2, mPaint);

        //画字
        Paint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        float textY = bounds.centerY() - (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(getText().toString(), bounds.centerX(), textY, paint);


        //画底部背景进度条--------------zzy另外新加的，全部浅蓝色进度条
        mPaint.setColor(innerCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(innerProgressLineWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int deleteWidthSec = innerProgressLineWidth + outLineWidth;
        mArcRect.set(bounds.left + deleteWidthSec, bounds.top + deleteWidthSec, bounds.right - deleteWidthSec, bounds.bottom - deleteWidthSec);
//        mArcRect.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
        canvas.drawArc(mArcRect, -90, 360, false, mPaint);

        //画进度条
//        mPaint.setColor(progressLineColor);
        mPaint.setColor(outerCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(progressLineWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int deleteWidth = innerProgressLineWidth + outLineWidth;
        mArcRect.set(bounds.left + deleteWidth, bounds.top + deleteWidth, bounds.right - deleteWidth, bounds.bottom - deleteWidth);

//        canvas.drawArc(mArcRect, 0, 360 * progress / 100, false, mPaint);
        canvas.drawArc(mArcRect, -90, 360 * progress / 100, false, mPaint);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int lineWidth = 4 * (outLineWidth + progressLineWidth);
        int lineWidth = progressLineWidth;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width > height ? width : height) + lineWidth;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        //是否需要更新圆的颜色
        validateCircleColor();
    }

    /**
     * 进度更新task。
     */
    private Runnable progressChangeTask = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            switch (mProgressType) {
                case ProgressBarUtils.ProgressType.COUNT:
                    progress += 1;
                    break;
                case ProgressBarUtils.ProgressType.COUNT_BACK:
                    progress -= 1;
                    break;
                default:
                    break;
            }
            if (progress >= 0 && progress <= 100) {
                if (mCountdownProgressListener != null){
                    mCountdownProgressListener.onProgress(listenerWhat, progress);
                }
                invalidate();
                if (timeMillis==0){
                    postDelayed(progressChangeTask, 500);
                }else {
                    postDelayed(progressChangeTask, timeMillis / 100);
                }
            } else{
                progress = validateProgress(progress);
            }
        }
    };


    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineColor 颜色值。
     */
    public void setOutLineColor(@ColorInt int outLineColor) {
        this.outLineColor = outLineColor;
        invalidate();
    }

    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineWidth 颜色值。
     */
    public void setOutLineWidth(@ColorInt int outLineWidth) {
        this.outLineWidth = outLineWidth;
        invalidate();
    }

    /**
     * 设置圆形的填充颜色。
     *
     * @param inCircleColor 颜色值。
     */
    public void setInCircleColor(@ColorInt int inCircleColor) {
        this.inCircleColors = ColorStateList.valueOf(inCircleColor);
        invalidate();
    }

    /**
     * 是否需要更新圆的颜色。
     */
    private void validateCircleColor() {
//        int circleColorTemp = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        int circleColorTemp = inCircleColors.getColorForState(getDrawableState(), Color.WHITE);
        if (circleColor != circleColorTemp) {
            circleColor = circleColorTemp;
            invalidate();
        }
    }

    /**
     * 设置进度条颜色。
     *
     * @param progressLineColor 颜色值。
     */
    public void setProgressColor(@ColorInt int progressLineColor) {
        this.progressLineColor = progressLineColor;
        invalidate();
    }

    /**
     * 设置进度条线的宽度。
     *
     * @param progressLineWidth 宽度值。
     */
    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
        invalidate();
    }

    /**
     * 设置进度。
     *
     * @param progress 进度。
     */
    public void setProgress(int progress) {
        this.progress = validateProgress(progress);
        invalidate();
    }

    /**
     * 验证进度。
     *
     * @param progress 你要验证的进度值。
     * @return 返回真正的进度值。
     */
    private int validateProgress(int progress) {
        if (progress > 100){
            progress = 100;
        } else if (progress < 0){
            progress = 0;
        }
        return progress;
    }

    /**
     * 拿到此时的进度。
     *
     * @return 进度值，最大100，最小0。
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 设置倒计时总时间。
     *
     * @param timeMillis 毫秒。
     */
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    /**
     * 拿到进度条计时时间。
     *
     * @return 毫秒。
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * 设置进度条类型。
     *
     * @param progressType {@link ProgressBarUtils.ProgressType}.
     */
    public void setProgressType(int progressType) {
        this.mProgressType = progressType;
        resetProgress();
        invalidate();
    }

    /**
     * 重置进度。
     */
    private void resetProgress() {
        switch (mProgressType) {
            case ProgressBarUtils.ProgressType.COUNT:
                progress = 0;
                break;
            case ProgressBarUtils.ProgressType.COUNT_BACK:
                progress = 100;
                break;
            default:
                break;
        }
    }

    /**
     * 拿到进度条类型。
     *
     * @return                          返回类型
     */
    public int getProgressType() {
        return mProgressType;
    }

    /**
     * 设置进度监听。
     *
     * @param mCountdownProgressListener 监听器。
     */
    public void setCountdownProgressListener(int what, OnCircleProgressListener mCountdownProgressListener) {
        this.listenerWhat = what;
        this.mCountdownProgressListener = mCountdownProgressListener;
    }

    /**
     * 开始。
     */
    public void start() {
        stop();
        post(progressChangeTask);
    }

    /**
     * 重新开始。
     */
    public void reStart() {
        resetProgress();
        start();
    }

    /**
     * 停止。
     */
    public void stop() {
        if(progressChangeTask!=null){
            removeCallbacks(progressChangeTask);
        }
    }

    /**
     * 当自定义控件销毁时，则调用该方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public interface OnCircleProgressListener {

        /**
         * 进度通知。
         *
         * @param progress 进度值。
         */
        void onProgress(int what, int progress);

    }


    private static class ProgressBarUtils {

        /**
         * 进度条类型。
         */
        @Retention(RetentionPolicy.SOURCE)
        public @interface ProgressType {
            /**
             * 顺数进度条，从0-100；
             */
            int COUNT = 1;
            /**
             * 倒数进度条，从100-0；
             */
            int COUNT_BACK = 2;
        }
        @IntDef({ProgressType.COUNT, ProgressType.COUNT_BACK})
        @interface CircleProgressType {}


        /**
         * 进度条text是否显示
         */
        @Retention(RetentionPolicy.SOURCE)
        public @interface NumberTextVisibility {
            /**
             * 显示
             */
            int Visible = 0;
            /**
             * 不显示
             */
            int Invisible = 1;
        }
        @IntDef({NumberTextVisibility.Visible, NumberTextVisibility.Invisible})
        @interface NumberVisibility {}


        /**
         * 进度条text是否显示
         */
        @Retention(RetentionPolicy.SOURCE)
        public @interface RingShowMode {
            /**
             * 空显示模式
             */
            int SHOW_MODE_NULL = 0;
            /**
             * 百分比+单位
             */
            int SHOW_MODE_PERCENT = 1;
        }
        @IntDef({RingShowMode.SHOW_MODE_NULL, RingShowMode.SHOW_MODE_PERCENT})
        @interface RingShowModeType {}


        public static float dp2px(Context context, float dp) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return dp * scale + 0.5f;
        }

        public static float sp2px(Context context, float sp) {
            final float scale = context.getResources().getDisplayMetrics().scaledDensity;
            return sp * scale;
        }


    }

}
