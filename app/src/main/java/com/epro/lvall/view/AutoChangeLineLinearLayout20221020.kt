package com.epro.lvall.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils

class AutoChangeLineLinearLayout20221020 : LinearLayout {

    constructor(context: Context?, attributeSet: AttributeSet?, int: Int) : super(context, attributeSet, int) {
        println("AutoChangeLineLinearLayout20221020 parameter 3")
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context?) : this(context, null)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var currentWidth = 0
        var maxWidth = 0
        var currentHeight = 0
        var maxHeight = 0

        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)

        for (i in 0 until childCount) {
            var childView = getChildAt(i)
//            childView.measure(widthMeasureSpec, heightMeasureSpec)
//            childView.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY)
//            childView.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST)
//            childView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            childView.measure(ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, childView.layoutParams.width), MeasureSpec.UNSPECIFIED)
            //childView.javaClass.name:androidx.appcompat.widget.AppCompatTextView
            LogUtils.d("childView.javaClass.name:" + childView.javaClass.name)
            if(childView.measuredWidth + currentWidth >= specWidth) {
                maxWidth = specWidth
                currentWidth = childView.measuredWidth
                maxHeight = currentHeight + childView.height
            } else{
                currentWidth += childView.measuredWidth
                maxHeight = maxHeight.coerceAtLeast(childView.height)
            }
        }

        setMeasuredDimension(maxWidth.coerceAtLeast(specWidth), maxHeight.coerceAtLeast(specHeight))

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = l
        var top = t
        var right = r
        var bottom = b

        for(i in 0 until childCount) {
            var childView : TextView = getChildAt(i) as TextView
//            LogUtils.d("AutoChangeLineLinearLayout20221020 childView.width: " + childView.width + " childView.height: " + childView.height)
            childView.layout(left, top, left+childView.measuredWidth, top+childView.measuredHeight)
            LogUtils.d(childView.text.toString().trim() + " AutoChangeLineLinearLayout20221020 childView.measuredWidth: " + childView.measuredWidth + " childView.measuredHeight: " + childView.measuredHeight)

            left += childView.measuredWidth
            if(left>r-l) {
                left = 0
                top += childView.measuredHeight
            }
        }
    }

}
