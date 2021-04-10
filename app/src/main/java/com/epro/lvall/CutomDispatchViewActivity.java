package com.epro.lvall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

public class CutomDispatchViewActivity extends AppCompatActivity {
    private DispatchView dispatchViewA, dispatchViewB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutom_dispatch_view);
        dispatchViewA = findViewById(R.id.btn_1);
        dispatchViewB = findViewById(R.id.btn_2);
        dispatchViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("dispatchViewA onClick");
            }
        });

        dispatchViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("dispatchViewB onClick");
            }
        });
    }
}
