package com.epro.rx

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.epro.verifycode.IRemoteService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class AIDLClientActivity : AppCompatActivity() {
    var iRemoteService : IRemoteService? = null

    var mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
           ToastUtils.showShort("onServiceDisconnected")
            iRemoteService = null;
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            ToastUtils.showShort("onServiceConnection")
            iRemoteService = IRemoteService.Stub.asInterface(service)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_i_d_l_client)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action: " + (iMyAidlInterface?.add(3, 5) ?: "iMyAidlInterface==null"), Snackbar.LENGTH_LONG)
            Snackbar.make(view, "Replace with your own action: " + iRemoteService?.addInRemoteInterface(3, 5), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        var intent = Intent()
        intent.setClassName("com.example.aidlserver", "com.example.aidlserver.MyService")
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

}