package com.epro.image.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.blankj.utilcode.util.Utils
import com.epro.image.R

class MainActivity20201211 : AppCompatActivity() {

    companion object {
        fun gotoMainActivity20201211() {
            startActivity(Intent(Utils.getApp(), MainActivity20201211::class.java));
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main20201211)
    }
}