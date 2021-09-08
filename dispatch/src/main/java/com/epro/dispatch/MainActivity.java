package com.epro.dispatch;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.Utils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(Utils.getApp(), DispatchCustomBtnActivity.class));
 //       startActivity(new Intent(Utils.getApp(), DispatchActivity.class));

    }
}
