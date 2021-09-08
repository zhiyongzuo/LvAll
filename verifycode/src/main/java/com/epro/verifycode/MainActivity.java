package com.epro.verifycode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView etCode1;
    public TextView tvCode2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        etCode1 = findViewById(R.id.tv_get_code_old);
        tvCode2 = findViewById(R.id.login_code_btn_sg);

        etCode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountTimer countTimer = new CountTimer(60000, 1000, MainActivity.this);
                countTimer.setSetOld(true);
                countTimer.start();
            }
        });

        tvCode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountTimer countTimer = new CountTimer(60000, 1000, MainActivity.this);
                countTimer.setSetOld(false);
                countTimer.start();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
