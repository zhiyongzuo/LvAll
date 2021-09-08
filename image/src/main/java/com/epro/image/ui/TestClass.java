package com.epro.image.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.epro.image.OverrideFirstZoomImageView;

import java.io.FileInputStream;
import java.io.InputStream;

import androidx.annotation.Nullable;

/**
 *
 * 2^0+2^1+2^2 = 2^3-1
 *
 * Integer总数量 2^32
 *
 * 原码1...000
 * 反码1...111
 * 补码1...000
 *
 * 1原码    0...01
 *  反码    1...10
 *  补码    1...11
 *
 *  2原码    0..010
 *   反码    1..101
 *   补码    1..110
 *
 * @author zzy
 * @date 2020/9/24
 */
public class TestClass extends OverrideFirstZoomImageView {
    public TestClass(Context context) {
        super(context);
    }

    public TestClass(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestClass(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestClass(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void test() {
//        float a = SMALL;
        OverrideFirstZoomImageView overrideFirstZoomImageView = new OverrideFirstZoomImageView(null);
//        overrideFirstZoomImageView.SMALL

        try {
            InputStream inputStream = new FileInputStream("E:\\ap2\\LvAll\\image\\src\\main\\res\\drawable" + "\\pikacu.gif");
//            Iterator itr = ImageIO.getImageReaders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
