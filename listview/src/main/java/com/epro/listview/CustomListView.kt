package com.epro.listview

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import com.blankj.utilcode.util.LogUtils

/**
 * 之所以要重写listview的原因是scrollview重写了measureChild的方法，强行把模式设置为UNSPECIFIED
 * 而listview在UNSPECIFIED模式下，高度只有一个item那么高
 * @author zzy
 * @date 2020/8/15
 */
class CustomListView(context : Context, attrs : AttributeSet, defStyleAttr : Int) : ListView(context, attrs, defStyleAttr) {

    constructor(context : Context, attrs : AttributeSet) : this(context, attrs, 0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)//可以
//        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 4, MeasureSpec.AT_MOST)//可以
        val expandSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST)//

        if(MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.AT_MOST) {
            LogUtils.e("heightMeasureSpec)==MeasureSpec.AT_MOST")
        } else if(MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.UNSPECIFIED) {
            LogUtils.e("heightMeasureSpec)==MeasureSpec.UNSPECIFIED")//--------------------默认是这个
        } else if(MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.EXACTLY) {
            LogUtils.e("heightMeasureSpec)==MeasureSpec.EXACTLY")
        } else{
            LogUtils.e("MeasureSpec.getMode(heightMeasureSpec)等于" + MeasureSpec.getMode(heightMeasureSpec))
        }

        LogUtils.e("MeasureSpec.getSize(heightMeasureSpec)---" + MeasureSpec.getSize(heightMeasureSpec))//1677
        LogUtils.e("MeasureSpec.getSize(expandSpec)---" + MeasureSpec.getSize(expandSpec))//536870911


        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}