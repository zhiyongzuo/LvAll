package com.epro.dispatch.dispatch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.LogUtils

/**
 * @author zzy
 * @date 2020/8/3
 */
class DispatchLinearLayout(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {

//    constructor(context: Context?) : super(context)
//    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, null)

    constructor(context: Context?, attrs: AttributeSet? = null) : this(context, attrs, 0) {
        val a = "22"

    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        LogUtils.e("DispatchLinearLayout-dispatchTouchEvent-" + ev?.toString())
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtils.e("DispatchLinearLayout-onTouchEvent-" + event?.toString())
        return super.onTouchEvent(event)
    }
}