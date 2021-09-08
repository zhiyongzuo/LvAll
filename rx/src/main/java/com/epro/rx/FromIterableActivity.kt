package com.epro.rx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.internal.functions.Functions
import java.util.*
import kotlin.collections.ArrayList

class FromIterableActivity : AppCompatActivity() {

    companion object{
        fun startFromIterableActivity() {
            ActivityUtils.startActivity(FromIterableActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from_iterable)
        var testList = ArrayList<ObservableSource<Int>>()
        for(i in 1..10) {
            testList.add(Observable.just(i)
                    .map(Function { LogUtils.e(it)
                     it
                    }))
        }
        Observable.fromIterable(testList)
                .flatMap(Functions.identity(), false, 1)
                .subscribe(Consumer {  })
    }
}
