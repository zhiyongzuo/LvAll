package com.epro.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        final MyDataBase dataBase = MyDataBase.getInstance(getApplicationContext());
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setBM("1");
        userInfoBean.setCSRLOCK("1");
        userInfoBean.setFWZ("1");
        userInfoBean.setGH("1");
        userInfoBean.setID("1");
        dataBase.getUserInfoBeanDao().insert(userInfoBean);

        TextView textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("1onClick", "onClick");
                UserInfoBean userInfoBean = dataBase.getUserInfoBeanDao().findUserInfoBeanByCsrId("1");
                userInfoBean.setBM("2");
                userInfoBean.setCSRLOCK("2");
                userInfoBean.setFWZ("2");
                dataBase.getUserInfoBeanDao().update(userInfoBean);
                Toast.makeText(MainActivity.this, "succeses", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
