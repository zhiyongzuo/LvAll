package com.epro.dispatch.dispatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.blankj.utilcode.util.LogUtils
import com.epro.dispatch.R

class DispatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch2)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        LogUtils.e("DispatchActivity-dispatchTouchEvent-" + ev.toString())
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtils.e("DispatchActivity-onTouchEvent-" + event.toString())
        return super.onTouchEvent(event)
    }
}
