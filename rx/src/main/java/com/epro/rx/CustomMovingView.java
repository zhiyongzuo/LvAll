package com.epro.rx;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2020/7/28
 */
public class CustomMovingView extends Button {
    private float positionX,positionY;

    public CustomMovingView(Context context) {
//        super(context);
        this(context, null);
    }

    public CustomMovingView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public CustomMovingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);

        //below ??????????????? min 14   require 21
        this(context, attrs, defStyleAttr, 0);
        init();
    }

    public CustomMovingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        positionX = ScreenUtils.getScreenWidth()/2;
        positionY = ScreenUtils.getScreenHeight()/2;
        moveToPosition();
    }

    private void moveToPosition() {
        setX(positionX);
        setY(positionY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("DispatchView-event.getAction()" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                positionX = event.getRawX();
                positionY = event.getRawY();
                moveToPosition();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
//        return true;
        //不返回true，返回下面代码的话，就不会跟手移动了-----事件分发，所有ACTINO_DOWN后的事件都被viewgroup吃掉了
        return super.onTouchEvent(event);
    }
}
