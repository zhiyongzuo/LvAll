package com.epro.dispatch.dispatch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils

/**
 * @author zzy
 * @date 2020/7/28
 */
class DispatchCustomBtnView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : Button(context, attrs, defStyleAttr, defStyleRes) {
    private var positionX = 0f
    private var positionY = 0f

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : this(context, attrs, defStyleAttr, 0) {
//        super(context, attrs, defStyleAttr);

        //below ??????????????? min 14   require 21
        init()
    }

    fun init() {
        positionX = ScreenUtils.getScreenWidth() / 2.toFloat()
        positionY = ScreenUtils.getScreenHeight() / 2.toFloat()
        moveToPosition()
    }

    private fun moveToPosition() {
        x = positionX
        y = positionY
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        LogUtils.e("DispatchView-event.getAction()" + event.action)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                positionX = event.rawX
                positionY = event.rawY
                moveToPosition()
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }
        //        return true;
        //不返回true，返回下面代码的话，就不会跟手移动了-----事件分发，所有ACTINO_DOWN后的事件都被viewgroup吃掉了
        return super.onTouchEvent(event)
    }

    init {
        init()
    }
}