package com.example.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.epro.verifycode.IMyAidlInterface
import com.epro.verifycode.IRemoteService

/**
 * Version 改成29 build failed
 * */
class MyService : Service() {
    var lastHeartBeatTime = 0L
    var enableCheckHeartBeat = false;

    override fun onCreate() {
        super.onCreate()
        checkHeartBeat()
    }

    override fun onBind(intent: Intent): IBinder {
//        return object : IMyAidlInterface.Stub() {
//            override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
//            }
//
//            override fun add(aInt: Int, bInt: Int): Int {
//                return aInt + bInt
//            }
//
//        }
        return object : IRemoteService.Stub(){
            override fun leave(iBinder: IBinder?) {
                TODO("Not yet implemented")
            }

            override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
                TODO("Not yet implemented")
            }

            override fun updateHeartBeat() {
                lastHeartBeatTime = System.currentTimeMillis()
            }

            override fun setHeartBeatCheckEnable(enable: Boolean) {
                enableCheckHeartBeat = enable
                lastHeartBeatTime = System.currentTimeMillis()
            }

            override fun addInRemoteInterface(aInt: Int, bInt: Int): Int {
                return aInt + bInt
            }

            override fun join(iBinder: IBinder?, name: String?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun checkHeartBeat() {
        Utils.runOnUiThreadDelayed({
            try {
                checkHeartBeat()
                if(!enableCheckHeartBeat) {
                    return@runOnUiThreadDelayed
                }
                val timeSpan = TimeUtils.getTimeSpan(System.currentTimeMillis(), lastHeartBeatTime, TimeConstants.SEC)
                if(timeSpan>60) {
                    ToastUtils.showShort("心跳间隔超过60s")
                    //restart the MaiActivity or reboot
                }
            } catch (e: Exception) {
            }
        }, 20 * 1000)
    }

}