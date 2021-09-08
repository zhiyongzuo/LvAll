package com.epro.image.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.epro.image.OverrideFirstZoomImageViewActivity;
import com.epro.image.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private ImageView mImageView;
    private EditText mEditText;
    private int clickTimes=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //跳转 canvas matrix translate测试
        //startActivity(new Intent(Utils.getApp(), CanvasAndMatrixActivity.class));
        //跳转 ZoomView
        startActivity(new Intent(Utils.getApp(), OverrideFirstZoomImageViewActivity.class));
        //跳转BitmapRegionDecoder测试类
        //startActivity(new Intent(Utils.getApp(), MainBitmapRegionDecodeActivity.class));

        //MainActivity20201211.Companion.gotoMainActivity20201211();

        mTextView = findViewById(R.id.tv);
        try {
            mImageView = findViewById(R.id.iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEditText = findViewById(R.id.et);
        mEditText.setText("/storage/emulated/0/Android/data/com.gas.gz/cache/luban_disk_cache/1591862844941251.jpg");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Glide.with(MainActivity.this).load(mEditText.getText().toString().trim()).placeholder(R.drawable.ic_launcher_background).into(mImageView);

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mImageView.getLayoutParams());
                params.gravity = FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY;
                mImageView.setLayoutParams(params);

                if(clickTimes%2 ==0) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mImageView.getLayoutParams());
                    layoutParams.gravity = Gravity.CENTER;
                    mImageView.setLayoutParams(layoutParams);
                    clickTimes = 3;
                    mImageView.setImageResource(R.drawable.w516h309);
                } else if(clickTimes%3 ==0) {
                    clickTimes = 5;
                    mImageView.setImageResource(R.drawable.w1080h4320);
                } else if(clickTimes%5 ==0) {
                    clickTimes = 2;
                    mImageView.setImageResource(R.drawable.w1440h2560);
                }

            }
        });
    }
}
