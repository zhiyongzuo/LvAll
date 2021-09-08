package com.epro.dispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;

/**
 * 事件分发测试
 * 设ViewGroup VG中，有2个Button：A，B：
 *
 * 3.按下A，再按下A（多点触控），为什么释放后A的点击事件只会触发一次。
 * 4.按下A，按下VG（空白区域），为什么先释放A，却无法触发A的点击事件，继续释放VG，又会触发A的点击事件。
 * 5.按下VG（空白区域），为什么点击A，B无响应。
 */
public class DispatchCustomBtnActivity extends AppCompatActivity {
    private Button btnA, btnB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        btnA = findViewById(R.id.btn_1);
        btnB = findViewById(R.id.btn_2);

        //如果不给btnA、btnB赋点击事件，自定义view用父类（TextView）的onTouchEvent，事件分发会被拦截。。textView实现的OnTouchEvent有、意思
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("btnA onClick");
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("btnB onClick");
            }
        });

    }
}
