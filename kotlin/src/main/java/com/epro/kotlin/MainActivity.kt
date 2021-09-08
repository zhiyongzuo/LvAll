package com.epro.kotlin

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastDoubleClickTime = 0L;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        TestRunnableActivity.startTestRunnableActivity()
//        ExecuteOrderActivity.startExecuteOrderActivity()


        tv_hello_world.setOnClickListener(View.OnClickListener {
            LogUtils.e(System.currentTimeMillis(), lastDoubleClickTime, System.currentTimeMillis() - lastDoubleClickTime)
            if (System.currentTimeMillis() - lastDoubleClickTime < 500) {
                //切换全屏
                LogUtils.e("simpleExoPlayerView double click")
                val layoutParams = ConstraintLayout.LayoutParams(tv_hello_world.layoutParams)
                if (!ScreenUtils.isFullScreen(this@MainActivity)) {
                    ScreenUtils.setFullScreen(this@MainActivity)
                    layoutParams.width = ScreenUtils.getScreenWidth()
                    layoutParams.height = ScreenUtils.getScreenHeight()
                } else {
                    ScreenUtils.setNonFullScreen(this@MainActivity)
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                tv_hello_world.setLayoutParams(layoutParams)
                lastDoubleClickTime = 0;
                return@OnClickListener
            }
            lastDoubleClickTime = System.currentTimeMillis()
        })
    }
}
