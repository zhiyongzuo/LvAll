package com.example.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.epro.verifycode.IMyAidlInterface

/**
 * Version 改成29 build failed
 * */
class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return object : IMyAidlInterface.Stub() {
            override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
                TODO("Not yet implemented")
            }

            override fun add(aInt: Int, bInt: Int): Int {
                return aInt + bInt
            }

        }
    }

}