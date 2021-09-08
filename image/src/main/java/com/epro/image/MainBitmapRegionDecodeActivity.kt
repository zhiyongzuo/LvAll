package com.epro.image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main_bitmap_region_decode.*

class MainBitmapRegionDecodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bitmap_region_decode)
        //iv_bitmap_region_decoder.setInputStream(assets.open("church1.jpg"))
        iv_bitmap_region_decoder.setInputStream(assets.open("w1080h4320.jpg"))
    }
}