package com.epro.lvall;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.epro.lvall.util.Permission;
import com.epro.verifycode.hooker.SystemServiceBinderHooker;
import com.google.gson.Gson;
import com.reeman.nerves.RobotActionProvider;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(Utils.getApp(), DispatchActivity.class));
//        startActivity(new Intent(Utils.getApp(), CutomDispatchViewActivity.class));

        convert();
        findViewById(R.id.tv_shut_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("关机");
                RobotActionProvider.getInstance().shutDown();
            }
        });

//        startac

//        View view = LayoutInflater.from(this).inflate(R.layout.item_for_test, null);
//        view.measure(0, 0);
//        //E/test_item: wrap96 0      30dp-92px      112px   65px     30dp-93px
//        Log.e("test_item ", view.getMeasuredHeight() + " " + view.getHeight());
//
//
//        mImageView = findViewById(R.id.iv);
//        Log.e("11", getResourcesUri(R.drawable.app));
//        Glide.with(this).load(getResourcesUri(R.drawable.app)).into(mImageView);
        //没做特殊处理的话，https要加证书，否则显示不出来
        // javax.net.ssl.SSLHandshakeException(java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.)
//        Glide.with(this).load("https://sqzl.gz96333.org//wwztp-api/captcha.jpg?uuid=76f5758b-867f-4f81-8d2d-6b1d1778a329").into(mImageView);

//        Glide.with(this).load("https://raw.githubusercontent.com/JessYanCoding/MVPArmsTemplate/master/art/step.png").into(mImageView);

        //图标要求是纯色和透明的
        ImageView imageView = findViewById(R.id.iv);
        imageView.setImageDrawable(changeColor(R.drawable.ic_launcher_test, R.color.colorAccent));
        imageView.setColorFilter(Color.BLACK);

        SystemServiceBinderHooker systemServiceBinderHooker = null;
//        systemServiceBinderHooker = new SystemServiceBinderHooker(Context.WIFI_SERVICE, "android.net.wifi.IWifiManager", new SystemServiceBinderHooker.HookCallback() {
        systemServiceBinderHooker = new SystemServiceBinderHooker(Context.USB_SERVICE, "android.hardware.usb.IUsbManager", new SystemServiceBinderHooker.HookCallback() {
            @Override
            public void onServiceMethodInvoke(Method method, Object[] args) {
                LogUtils.d("hooooook onServiceMethodInvoke");
            }

            @Override
            public Object onServiceMethodIntercept(Object receiver, Method method, Object[] args) throws Throwable {
                LogUtils.d("hooooook onServiceMethodIntercept");
                return null;
            }
        });
        systemServiceBinderHooker.hook();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("onClick");

                // 调用一般都是通过 context 获取系统服务
//                WifiManager mWifi = (WifiManager) Utils.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                mWifi.startScan();

                UsbManager usbManager =  (UsbManager)Utils.getApp().getApplicationContext().getSystemService(Context.USB_SERVICE);
                usbManager.getDeviceList();
            }
        });

        testPermission();
    }

    private Drawable changeColor(@DrawableRes int drawableId, @ColorRes int colorId) {
        Drawable drawable = ContextCompat
                .getDrawable(this, drawableId)
                .mutate();
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        ColorStateList colors = ColorStateList.valueOf(ContextCompat.getColor(this, colorId));
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

    public void convert() {
        String s = "{\"id\":4077395,\"field_id\":242566,\"body\":\"body\"}";
        System.out.print(new Gson());
    }

    @SuppressLint("WrongConstant")
    public void testPermission() {
        PermissionUtils.permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA, Permission.READ_PHONE_STATE)
                .callback(new PermissionUtils.FullCallback() {
                              @Override
                              public void onGranted(@NonNull List<String> granted) {
                                 LogUtils.d("onGranted");
                              }

                              @Override
                              public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                                  LogUtils.d("onDenied");
                              }
                          }
                ).request();
    }
}
