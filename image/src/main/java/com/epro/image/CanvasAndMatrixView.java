package com.epro.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/9/23
 */
public class CanvasAndMatrixView extends View {

    Matrix matrix = new Matrix();
    Paint paint;
    private Canvas mCanvas;
    private Bitmap drawBitmap;
    private boolean isMatrixMove = false;
    private int canvasTranslate;

    public CanvasAndMatrixView(Context context) {
        super(context);
    }

    public CanvasAndMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasAndMatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIsMatrixMove(boolean isMatrixMove) {
        this.isMatrixMove = isMatrixMove;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        drawBitmap = ImageUtils.getBitmap(R.mipmap.ic_launcher);
        if (!isMatrixMove) {
            canvasMove();
        } else {
            matrixMove();
        }
    }

    /**
     * canvasMove()和matrixMove()
     * 我的理解 canvas没有移动，移动的是坐标轴
     *
     */
    private void canvasMove() {
        canvasTranslate += 10;
        matrix = new Matrix();
        ToastUtils.showShort("canvasMove");
        mCanvas.drawColor(Color.RED);
        mCanvas.translate(canvasTranslate, canvasTranslate);
        mCanvas.drawBitmap(drawBitmap,0, 0, paint);
    }

    private void matrixMove() {
        ToastUtils.showShort("matrixMove");
        mCanvas.drawColor(Color.WHITE);
        matrix.preTranslate(10, 10);
        mCanvas.drawBitmap(drawBitmap, matrix, paint);
    }
}
