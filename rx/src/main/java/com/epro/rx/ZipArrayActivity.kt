package com.epro.rx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.R
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class ZipArrayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_zip_array)

        //测试zipArray操作符
//        var observableFirst = Observable.zip(Observable.just(1), Observable.just(2), BiFunction(function = {
//
//        }))
//        Observable.zipArray();
    }
}