package com.epro.lvall

import kotlinx.coroutines.*
import org.junit.Test

/**
 * @author zzy
 * @date 2020/9/14
 */
class KotlinForTest {
    @Test
    fun clickToTest() = runBlocking {
        launch {
            delay(200L)
            //222222222222222222222222222222222222222222222222222222
            println ("Task from runBlocking")
        }
        coroutineScope {
            // 创建⼀个协程作⽤域
            launch {
                delay(500L)
                //333333333333333333333333333333333333333333333333333
                println ("Task from nested launch")
            }
            delay (100L)
            println ("Task from coroutine scope")
            // 这⼀⾏会在内嵌 launch 之前输出111111111111111111111111
         }
        /////////////////////////////////444444444444444444444444444
        println ("Coroutine scope is over")
        // 这⼀⾏在内嵌 launch 执⾏完毕后才输出
    }
}