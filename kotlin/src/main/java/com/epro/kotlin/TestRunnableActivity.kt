package com.epro.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import kotlinx.android.synthetic.main.activity_test_runnable.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class TestRunnableActivity : AppCompatActivity() {

    companion object{
        var countNum = 0
        var singleExecutorServiece : ExecutorService =  Executors.newSingleThreadExecutor()
        fun startTestRunnableActivity() {
            ActivityUtils.startActivity(TestRunnableActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_runnable)

        testFunc(null)

        //在这里都是{第二部分}在{第一部分}前执行，，但是两条线程执行顺序其实是不确定的
        //几十行代码跑的比一行还快  工程app二期里
        tv_test.setOnClickListener {
            countNum++
            singleExecutorServiece.execute(object : Runnable{
                override fun run() {
                    for(i in 1..10) {
                        val t = i
                    }
                    runOnUiThread({
                        LogUtils.e("第一部分 countNum is ${countNum}")
                    })
                }
            })
            runOnUiThread({
                LogUtils.e("第二部分 countNum is ${countNum}")
            })

//            ThreadUtils.executeBySingle(object : ThreadUtils.Task<Any?>() {
//                @Throws(Throwable::class)
//                override fun doInBackground(): Any {
//                    return 1
//                }
//
//                override fun onSuccess(result: Any?) {
//                    runOnUiThread({
//                        LogUtils.e("第二部分 countNum is ${countNum}")
//                    })
//                }
//                override fun onCancel() {}
//                override fun onFail(t: Throwable) {}
//            })
        }


        //下面log打印按顺序1、2、3、4、5、6、7、8、9、10
//        Thread(Runnable {
//            runOnUiThread({LogUtils.e("1")})
//            runOnUiThread({LogUtils.e("2")})
//            runOnUiThread({LogUtils.e("3")})
//            runOnUiThread({LogUtils.e("4")})
//            runOnUiThread({LogUtils.e("5")})
//            runOnUiThread({LogUtils.e("6")})
//            runOnUiThread({LogUtils.e("7")})
//            runOnUiThread({LogUtils.e("8")})
//            runOnUiThread({LogUtils.e("9")})
//            runOnUiThread({LogUtils.e("10")})
//        }).start()


        //下面log打印是按顺序，1、2、3、4、5、xxxx、6、7、8、9、10
//        runOnUiThread({
//            LogUtils.e("1")
//            runOnUiThread {
//                LogUtils.e("2")
//                for (i in 0..9999) {
//                    var b = i
//                }
//                runOnUiThread {
//                    LogUtils.e("3")
//                    runOnUiThread {
//                        LogUtils.e("4")
//                        runOnUiThread {
//                            LogUtils.e("5")
//                        }
//                    }
//                }
//            }
//        })
//        LogUtils.d("xxxxxxxxxx")
//        runOnUiThread {
//            LogUtils.e("6")
//        }
//        runOnUiThread {
//            LogUtils.e("7")
//        }
//        runOnUiThread {
//            LogUtils.e("8")
//        }
//        runOnUiThread {
//            LogUtils.e("9")
//        }
//        runOnUiThread {
//            LogUtils.e("10")
//        }
    }

    fun testFunc(s:String?) {

    }
}
