package com.zeusee.main.lpr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.park.R;
import com.example.platerecognization.util.PlateInfo;
import com.zeusee.main.lpr.jni.PlateRecognition;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class CameraPlateActivity extends AppCompatActivity implements Camera.PreviewCallback, SurfaceHolder.Callback {

    public static final String FOCUS_MODE_MACRO = "macro";

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    private int PreviewHeight = 0;

    private int PreviewWidth = 0;

    int SwitchFlag = 0;

    Camera camera = null;

    SurfaceView camerasurface = null;

    long currentTime;

    TextView detectTime;

    Handler handler = new Handler() {
        public void handleMessage(Message param1Message) {
            if (param1Message.what != 200)
                return;
            CameraPlateActivity.this.warning.setVisibility(View.INVISIBLE);
            CameraPlateActivity.this.warning.setText("");
        }
    };

    SurfaceHolder holder;

    private Camera.Size mPreviewSize;

    Camera.Parameters parameters;

    TextView warning;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CameraPlateActivity.class);
        context.startActivity(intent);
    }

    private static Camera.Size getOptimalPreviewSize(List<Camera.Size> paramList, int paramInt1, int paramInt2) {
        double d3 = paramInt1 / paramInt2;
        Camera.Size size1 = null;
        if (paramList == null)
            return null;
        Iterator<Camera.Size> iterator = paramList.iterator();
        double d2 = Double.MAX_VALUE;
        double d1 = Double.MAX_VALUE;
        while (iterator.hasNext()) {
            Camera.Size size = iterator.next();
            if (Math.abs(size.width / size.height - d3) <= 0.1D && Math.abs(size.height - paramInt2) < d1) {
                d1 = Math.abs(size.height - paramInt2);
                size1 = size;
            }
        }
        Camera.Size size2 = size1;
        if (size1 == null) {
            iterator = paramList.iterator();
            d1 = d2;
            while (true) {
                size2 = size1;
                if (iterator.hasNext()) {
                    Camera.Size size = iterator.next();
                    if (Math.abs(size.height - paramInt2) < d1) {
                        d1 = Math.abs(size.height - paramInt2);
                        size1 = size;
                    }
                    continue;
                }
                break;
            }
        }
        return size2;
    }

    private void initCamera() {
        this.camera = Camera.open(0);
        this.parameters = this.camera.getParameters();
        this.parameters.setFocusMode("macro");
        this.parameters.setFocusMode("continuous-picture");
        this.camera.setParameters(this.parameters);
        try {
            this.camera.setPreviewDisplay(this.camerasurface.getHolder());
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.parameters = this.camera.getParameters();
        List<Camera.Size> list = this.parameters.getSupportedPreviewSizes();
        this.camera.setDisplayOrientation(90);
        this.mPreviewSize = getOptimalPreviewSize(list, display.getWidth(), display.getHeight());
        this.PreviewWidth = 640;
        this.PreviewHeight = 480;
        this.parameters.setPreviewSize(this.PreviewWidth, this.PreviewHeight);
        this.parameters.setPictureSize(this.PreviewWidth, this.PreviewHeight);
        this.camera.setParameters(this.parameters);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.camerasurface.getLayoutParams();
        float f = display.getWidth();
        int i = this.PreviewHeight;
        f /= i;
        layoutParams.height = (int)(this.PreviewWidth * f);
        layoutParams.width = (int)(i * f);
        this.camerasurface.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        try {
            this.camera.setPreviewDisplay(this.camerasurface.getHolder());
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.camera.startPreview();
        this.camera.setPreviewCallback(this);
    }

    private void showRes(String paramString) {
//        if (this.SwitchFlag == 0) {
//            this.SwitchFlag = 1;
//            (new AlertDialog.Builder((Context)this)).setTitle(").setMessage(paramString).setOnCancelListener(new DialogInterface.OnCancelListener() {
//            public void onCancel(DialogInterface param1DialogInterface) {
//                CameraPlateActivity.this.SwitchFlag = 0;
//            }
//        }).show();
    }

    public void callCamera() {
        if (Build.VERSION.SDK_INT > 21) {
            int i = ActivityCompat.checkSelfPermission((Context)this, "android.permission.RECORD_AUDIO");
            if (ActivityCompat.checkSelfPermission((Context)this, "android.permission.CAMERA") != 0 || i != 0) {
                ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.CAMERA", "android.permission.RECORD_AUDIO" }, 1);
                return;
            }
            initCamera();
            return;
        }
        initCamera();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_camera_plate);
        this.currentTime = System.currentTimeMillis();
        this.detectTime = (TextView)findViewById(R.id.detect_time);
        this.warning = (TextView)findViewById(R.id.warning);
        this.camerasurface = (SurfaceView)findViewById(R.id.surface_view);
        if (paramBundle == null)
            new Bundle();
        this.camerasurface.getHolder().addCallback(this);
        this.camerasurface.setKeepScreenOn(true);

        // /storage/emulated/0/car
//        String str = "/storage/emulated/0/car";
//        PlateRecognition.getInstance().initPlateRecognition(str);
    }

    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "Activity On Pasue");
    }

    public void onPreviewFrame(byte[] paramArrayOfbyte, Camera paramCamera) {
        if (System.currentTimeMillis() - this.currentTime > 150L) {
            String str;
            Log.d("test", "detecting");
            PlateRecognition.getInstance().plateRecognitionWithRaw(paramArrayOfbyte, this.PreviewHeight, this.PreviewWidth, 0.8F, 40, 500, 1);
            PlateInfo plateInfo = PlateRecognition.getInstance().getBestConfidenceInfo();
            if (plateInfo.getErrorCode() == -1) {
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("");
                stringBuilder1.append("车牌号:");
                        stringBuilder1.append(plateInfo.getPlateName());
                stringBuilder1.append("\n");
                str = stringBuilder1.toString();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append("置信度:");
                        stringBuilder2.append(String.valueOf(plateInfo.getConfidence()));
                stringBuilder2.append("\n");
                str = stringBuilder2.toString();
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append("车牌颜色:");
                        stringBuilder2.append(plateInfo.color[plateInfo.getPlateType()]);
                stringBuilder2.append("\n");
                str = stringBuilder2.toString();
            } else {
                str = "";
            }
            if (str != "") {
                if (!this.warning.getText().toString().contains(plateInfo.getPlateName()))
                    ((Vibrator)getSystemService(Context.VIBRATOR_SERVICE)).vibrate(80L);
                this.warning.setVisibility(View.VISIBLE);
                this.warning.setText(str);
                this.handler.removeCallbacksAndMessages(null);
                this.handler.sendEmptyMessageDelayed(200, 2000L);
            }
            TextView textView = this.detectTime;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(150));
            stringBuilder.append("ms");
            textView.setText(stringBuilder.toString());
            this.currentTime = System.currentTimeMillis();
        }
    }

    public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
        if (paramInt != 1)
            return;
        if (paramArrayOfint.length > 0 && paramArrayOfint[0] == 0) {
            initCamera();
            return;
        }
        Toast.makeText((Context)this, "you refused the camera function", Toast.LENGTH_SHORT).show();
    }

    protected void onResume() {
        super.onResume();
        Camera camera = this.camera;
        if (camera != null)
            camera.startPreview();
    }

    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {
        if (paramSurfaceHolder.getSurface() == null)
            return;
        try {
            this.camera.stopPreview();
        } catch (Exception exception) {}
        try {
            this.camera.setPreviewCallback(this);
            this.camera.setPreviewDisplay(paramSurfaceHolder);
            this.camera.startPreview();
            return;
        } catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error starting camera preview: ");
            stringBuilder.append(exception.getMessage());
            Log.d("TAG", stringBuilder.toString());
            return;
        }
    }

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
        callCamera();
    }

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
        if(camera==null) {
            return;
        }
        camera.setPreviewCallback(null);
        this.camera.stopPreview();
        this.camera.release();
        this.camera = null;
    }
}