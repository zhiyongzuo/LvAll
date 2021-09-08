package com.epro.image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_override_first_zoom_image_view.*

class OverrideFirstZoomImageViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_override_first_zoom_image_view)
//        override_first_zoom_iamge_view.setInputStream(assets.open("church1.jpg"))
        override_first_zoom_iamge_view.setInputStream(assets.open("w1080h4320.jpg"))
    }

    override fun onResume() {
        super.onResume()
//        LogUtils.e("onResume")
    }
}