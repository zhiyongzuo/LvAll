package com.example.platerecognization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.bs.park.R;
import com.zeusee.main.lpr.jni.PlateRecognition;
import com.zeusee.main.lpr.CameraPlateActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CameraPlateActivity.startActivity(MainActivity.this);
                ScenCameraActivity.startActivity(MainActivity.this);
            }
        });

        checkPermission();
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT > 21) {
            int i = ActivityCompat.checkSelfPermission((Context)this, "android.permission.WRITE_EXTERNAL_STORAGE");
            int j = ActivityCompat.checkSelfPermission((Context)this, "android.permission.READ_EXTERNAL_STORAGE");
            if (i != 0 || j != 0) {
                ActivityCompat.requestPermissions(this, new String[] { "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE" }, 1);
                return;
            }
            initRecognizer();
            return;
        }
        initRecognizer();
    }

    public void copyFilesFromAssets(Context paramContext, String paramString1, String paramString2) {
        try {
            String[] arrayOfString = paramContext.getAssets().list(paramString1);
            int j = arrayOfString.length;
            int i = 0;
            if (j > 0) {
                if (!(new File(paramString2)).mkdir())
                    Log.d("mkdir", "can't make folder");
                j = arrayOfString.length;
                while (i < j) {
                    String str1 = arrayOfString[i];
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append(paramString1);
                    stringBuilder1.append("/");
                    stringBuilder1.append(str1);
                    String str2 = stringBuilder1.toString();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(paramString2);
                    stringBuilder2.append("/");
                    stringBuilder2.append(str1);
                    copyFilesFromAssets(paramContext, str2, stringBuilder2.toString());
                    i++;
                }
            } else {
                InputStream inputStream = paramContext.getAssets().open(paramString1);
                FileOutputStream fileOutputStream = new FileOutputStream(new File(paramString2));
                byte[] arrayOfByte = new byte[1024];
                while (true) {
                    i = inputStream.read(arrayOfByte);
                    if (i != -1) {
                        fileOutputStream.write(arrayOfByte, 0, i);
                        continue;
                    }
                    fileOutputStream.flush();
                    inputStream.close();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void initRecognizer() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append(File.separator);
        stringBuilder.append("car");
        String str = stringBuilder.toString();
        copyFilesFromAssets((Context)this, "car", str);
        Log.d("modelPath", str);
        PlateRecognition.getInstance().initPlateRecognition(str);
    }

    protected void onDestroy() {
        PlateRecognition.getInstance().releaseJni();
        super.onDestroy();
    }
}