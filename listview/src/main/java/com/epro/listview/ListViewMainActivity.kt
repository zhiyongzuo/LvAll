package com.epro.listview

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils

class ListViewMainActivity : AppCompatActivity() {
    lateinit var listAdapter : ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_main)

        var listview = findViewById<CustomListView>(R.id.list_view)
        var listData = ArrayList<String>()
        for(index in 0 until 100) {
            listData.add("$index");
        }
        listAdapter = ArrayAdapter<String>(this, android.R.layout.test_list_item, listData)
        listview.adapter = listAdapter

//        setListViewHeightBasedOnChildren(listview)

//        setListViewHeightCustom(listview)
    }

    private fun setListViewHeightCustom(listview: ListView) {
        var totalListViewHeight = 0

        LogUtils.e("listview.width is " + listview.width)
        var desireWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(listview.width, View.MeasureSpec.EXACTLY)
        for (index in 0 until listview.adapter.count) {
            var childView = listview.adapter.getView(index, null, listview)//为什么这样取

//            childView.measure(desireWidthMeasureSpec, View.MeasureSpec.UNSPECIFIED)//都可以
//            childView.measure(desireWidthMeasureSpec, View.MeasureSpec.EXACTLY)//都可以
            childView.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.AT_MOST)//都可以
            totalListViewHeight += childView.measuredHeight
        }

        var listViewLayoutParams = listview.layoutParams;
        listViewLayoutParams.height = totalListViewHeight
        listview.layoutParams = listViewLayoutParams
        listview.post {
            LogUtils.e("222222222----listview.height is ${listview.height}")
        }
    }
}
