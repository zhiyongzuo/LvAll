package com.epro.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils

class ExecuteOrderActivity : AppCompatActivity() {

    companion object {
        fun startExecuteOrderActivity() {
            ActivityUtils.startActivity(ExecuteOrderActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_order)
        testFunc(TestBean())

    }

    fun testFunc(testBean : TestBean) {
        LogUtils.e("testFunc")
    }
}
