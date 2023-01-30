package com.example.platerecognization;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bs.park.R;
import com.example.platerecognization.util.PlateInfo;
import com.zeusee.main.lpr.CameraPlateActivity;
import com.zeusee.main.lpr.jni.PlateRecognition;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ScenCameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private ImageView ivTakePic, ivCancel, ivSure;
    private TextView tvTips;
    private Camera camera;
    private SurfaceView surfaceView;
    private Button back_btn, flash_btn, back;
    private RelativeLayout re;
    private int width, height;
    private TimerTask timer;
    private int preWidth = 0;
    private int preHeight = 0;
    private SurfaceHolder holder;
//    private int rotation = 0;
    private int rotation = 90;
    private Bitmap bitmap, bitmap1;
    private Timer time;
    // true:拍照识别采用拍摄照片（整图）根据路径识别，不受扫描框限制
    // false:采用视频流 单帧识别模式 识别扫描框内的车牌
    private boolean isAutoFocus = true; // 是否开启自动对焦 true:开启，定时对焦 false:不开起
    // ，只在图片模糊时对焦
    private int initPreWidth = 1920; //
    private int initPreHeight = 1080;//预览分辨率筛选上限，即在筛选合适的分辨率时  在这两个值以下筛选
    private boolean isFirstIn = true;
    //只拍照
    public boolean MODE_TAKE_PIC = true;
    public File mPicFile;

    public static void startActivity(Activity activity) {
        start(activity, false, null);
    }

    public static void start(Activity activity, OnCameraListener listener) {
        start(activity, false, listener);
    }

    public static void start(final Activity activity, boolean isOnlyTakePic, final OnCameraListener listener) {
        String filePath = PathUtils.getExternalAppCachePath() + File.separator + TimeUtils.getNowMills() + ".jpg";
        final File file = new File(filePath);
        Intent intent = new Intent(activity, ScenCameraActivity.class);
        intent.putExtra("INTENT_TYPE", isOnlyTakePic);
        intent.putExtra("INTENT_KEY_IN_FILE", file);

        activity.startActivity(intent);

//        activity.startActivityForResult(intent, new BaseActivity.OnActivityCallback() {
//            @Override
//            public void onActivityResult(int resultCode, @Nullable Intent data) {
//                if (listener == null) {
//                    return;
//                }
//
//                String number = "";
//                String color = "";
//                if (data!=null) {
//                    number = data.getStringExtra(EXTRA_NUMBER);
//                    color = data.getStringExtra(EXTRA_COLOR);
//                }
//
//                switch (resultCode) {
//                    case RESULT_OK:
//                        if (file.isFile()) {
//                            listener.onSelected(file, number, color);
//                        } else {
//                            listener.onCancel();
//                        }
//                        break;
//                    case RESULT_ERROR:
//                        String details = "";
//                        listener.onError(details);
//                        break;
//                    case RESULT_CANCELED:
//                    default:
//                        listener.onCancel();
//                        break;
//                }
//            }
//        });
    }

    ScaleGestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int uiRot = getWindowManager().getDefaultDisplay().getRotation();// 获取屏幕旋转的角度
        requestWindowFeature(Window.FEATURE_NO_TITLE);//
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scen_camera);

        if (getIntent()!=null) {
            MODE_TAKE_PIC = getIntent().getBooleanExtra("INTENT_TYPE",false);
            mPicFile = (File) getIntent().getSerializableExtra("INTENT_KEY_IN_FILE");
        }

        findiew();
        setRotationAndView(uiRot);
        getScreenSize();
        ivTakePic.setVisibility(View.VISIBLE);
    }

    // 设置相机取景方向和扫面框
    private void setRotationAndView(int uiRot) {
        setScreenSize(this);
//		System.out.println("屏幕宽：" + width + "     屏幕高：" + height);
        setLinearButton();
    }

    @SuppressLint("NewApi")
    private void findiew() {
        tvTips = findViewById(R.id.tv_tips);
        ivTakePic = findViewById(R.id.iv_take_pic);
        ivCancel = findViewById(R.id.iv_cancel);
        ivSure = findViewById(R.id.iv_sure);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceViwe_video);
        back_btn = (Button) findViewById(R.id.back_camera);
        flash_btn = (Button) findViewById(R.id.flash_camera);
        back = (Button) findViewById(R.id.back);
        re = (RelativeLayout) findViewById(R.id.memory);
        re.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ((bottom != oldBottom && right == oldRight) || (bottom == oldBottom && right != oldRight)) {
                    getScreenSize();
                    getPreToChangView(preWidth, preHeight);
                }
            }
        });
        // hiddenVirtualButtons(re);
        holder = surfaceView.getHolder();
        holder.addCallback(ScenCameraActivity.this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 因为箭头方向的原因，横竖屏状态下 返回按钮是两张不同的ImageView
        // 横屏状态下返回按钮

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        // 竖屏状态下返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        // 闪光灯监听事件
        flash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // b = true;
                // TODO Auto-generated method stub
                if (!getPackageManager().hasSystemFeature(
                        PackageManager.FEATURE_CAMERA_FLASH)) {
                    Toast.makeText(
                            ScenCameraActivity.this,
                            getResources().getString(
                                    getResources().getIdentifier("no_flash",
                                            "string", getPackageName())),
                            Toast.LENGTH_LONG).show();
                } else {
                    if (camera != null) {
                        Camera.Parameters parameters = camera.getParameters();
                        String flashMode = parameters.getFlashMode();
                        if (flashMode
                                .equals(Camera.Parameters.FLASH_MODE_TORCH)) {

                            parameters
                                    .setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            parameters.setExposureCompensation(0);
                        } else {
                            parameters
                                    .setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);// 闪光灯常亮
                            parameters.setExposureCompensation(-1);

                        }
                        try {
                            camera.setParameters(parameters);
                        } catch (Exception e) {

                            Toast.makeText(
                                    ScenCameraActivity.this,
                                    getResources().getString(
                                            getResources().getIdentifier(
                                                    "no_flash", "string",
                                                    getPackageName())),
                                    Toast.LENGTH_LONG).show();
                        }
                        try {
                            camera.startPreview();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });
        ivTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camera==null) {
                    return;
                }
                if(ivTakePic==null) {
                    return;
                }
                ivTakePic.setVisibility(View.GONE);
                try {
                    tvTips.setVisibility(View.VISIBLE);
                    tvTips.setText("图片保存中...");
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            if (data!=null) {
                                try {
                                    Bitmap bitmap = ImageUtils.rotate(ImageUtils.bytes2Bitmap(data), rotation, 0, 0);
                                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(mPicFile));
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                    bos.flush();
                                    bos.close();

                                    ivCancel.setVisibility(View.VISIBLE);
                                    ivSure.setVisibility(View.VISIBLE);
                                    tvTips.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    ToastUtils.showShort(e.getMessage());
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.showShort("jpeg data==null");
                                LogUtils.e("jpeg data==null");
                            }
                        }
                    });
                } catch (Exception e) {
//                    ToastUtils.showShort("拍照失败，请重试" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camera==null) {
                    return;
                }
                camera.startPreview();
                ivTakePic.setVisibility(View.VISIBLE);
                ivCancel.setVisibility(View.GONE);
                ivSure.setVisibility(View.GONE);
            }
        });
        ivSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSure.setEnabled(false);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    // 设置竖屏方向按钮布局
    private void setLinearButton() {
        int back_w;
        int back_h;
        int flash_w;
        int flash_h;
        int Fheight;
        RelativeLayout.LayoutParams layoutParams;
        back.setVisibility(View.VISIBLE);
        back_btn.setVisibility(View.GONE);
        back_h = (int) (height * 0.066796875);
        back_w = (int) (back_h * 1);
        layoutParams = new RelativeLayout.LayoutParams(back_w, back_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
                RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,
                RelativeLayout.TRUE);

        Fheight = (int) (width * 0.75);
        layoutParams.topMargin = (int) (((height - Fheight * 0.8 * 1.585) / 2 - back_h) / 2);
        layoutParams.leftMargin = (int) (width * 0.10486111111111111111111111111111);
        back.setLayoutParams(layoutParams);

        flash_h = (int) (height * 0.066796875);
        flash_w = (int) (flash_h * 1);
        layoutParams = new RelativeLayout.LayoutParams(flash_w, flash_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
                RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,
                RelativeLayout.TRUE);

        Fheight = (int) (width * 0.75);
        layoutParams.topMargin = (int) (((height - Fheight * 0.8 * 1.585) / 2 - flash_h) / 2);
        layoutParams.rightMargin = (int) (width * 0.10486111111111111111111111111111);
        flash_btn.setLayoutParams(layoutParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        OpenCameraAndSetParameters();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (!MODE_TAKE_PIC) {
            recogLocalPic(data);
        }
    }

    private void recogLocalPic(byte[] data) {
        String str;
        Log.d("test", "detecting");
        PlateRecognition.getInstance().plateRecognitionWithRaw(data, preHeight, preWidth, 0.8F, 40, 500, 1);
//        PlateRecognition.getInstance().plateRecognitionWithRaw(data, 640, 480, 0.8F, 40, 500, 1);
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
        if (!TextUtils.isEmpty(str)) {
            LogUtils.d(str);
            try {
                YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, preWidth, preHeight, null);

                //没旋转90°
//                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(mPicFile));
//                yuvimage.compressToJpeg(new Rect(0, 0, preWidth, preHeight), 100, bufferedOutputStream);
//                bufferedOutputStream.flush();
//                bufferedOutputStream.close();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                yuvimage.compressToJpeg(new Rect(0, 0, preWidth, preHeight), 100, baos);
                Bitmap bitmap = ImageUtils.rotate(ImageUtils.bytes2Bitmap(baos.toByteArray()), rotation, 0, 0);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(mPicFile));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                baos.flush();
                bos.close();
                baos.close();
                finish();
            } catch (Exception e) {
                ToastUtils.showShort(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (camera != null) {
            initCamera(holder, initPreWidth, initPreHeight);
            getPreToChangView(preWidth, preHeight);

//            if (rotation == 90 || rotation == 270) {
//                myview = new PlateViewfinderView(ScenCameraActivity.this, width, height, true);
//            } else {
//                myview = new PlateViewfinderView(ScenCameraActivity.this, width, height, false);
//            }
//            re.addView(myview);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) { }

    private void initCamera(SurfaceHolder holder, int setPreWidth, int setPreHeight) {
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> list = parameters.getSupportedPreviewSizes();
        Camera.Size size;

        int previewWidth = 480;
        int previewheight = 640;
        if (list == null) {
            size = parameters.getPreviewSize();
            previewWidth = size.width;
            previewheight = size.height;
        } else if (list.size() == 1) {
            //设备只有一组预览分辨率
            size = list.get(0);
            previewWidth = size.width;
            previewheight = size.height;
        } else {
            Iterator paramPoint = list.iterator();
            Camera.Size localSize;
            //判断当前尺寸有没有正好是屏幕尺寸的
            do {
                if (!paramPoint.hasNext()) {
                    //遍历到最后都没找到当前屏幕尺寸的，选择最佳尺寸
                    localSize = getCloselyPreSize(parameters, setPreWidth, setPreWidth);
                    break;
                }
                localSize = (Camera.Size) paramPoint.next();
            } while ((localSize.width != setPreWidth) || (localSize.height != setPreHeight));
            previewWidth = localSize.width;
            previewheight = localSize.height;
        }
        preWidth = previewWidth;
        preHeight = previewheight;
        System.out.println("预览分辨率：" + preWidth + "    " + preHeight);
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setPreviewSize(preWidth, preHeight);
        if (parameters.getSupportedFocusModes().contains(
                parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                && !isAutoFocus) {
            isAutoFocus = false;
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            parameters
                    .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (parameters.getSupportedFocusModes().contains(
                parameters.FOCUS_MODE_AUTO)) {
            isAutoFocus = true;
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        camera.setParameters(parameters);
        camera.setDisplayOrientation(rotation);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.setPreviewCallback(this);  //设置相机回调的几个方法
        camera.startPreview();
    }


    private Camera.Size getCloselyPreSize(Camera.Parameters paramParameters, int paramWidth, int paramHeight) {
        float f3 = paramWidth / paramHeight;
        Iterator localIterator = paramParameters.getSupportedPreviewSizes().iterator();
        Camera.Size size2 = null;
        while (localIterator.hasNext()) {
            Camera.Size size = (Camera.Size) localIterator.next();
            if (f3 == ((float) size.width) / ((float) size.height) && size.width <= 1920 && width <= size.width) {
                size2 = size;
            }
        }
        if (paramWidth == 0 || paramHeight == 0) {
//            float f = AutoScrollHelper.NO_MAX;
            float f = 3.4028235E38F;

            while (localIterator.hasNext()) {
                Camera.Size size3 = (Camera.Size) localIterator.next();
                float abs = Math.abs(f3 - (((float) size3.width) / ((float) size3.height)));
                if (abs < f) {
                    size2 = size3;
                    f = abs;
                }
            }
            if (size2 == null) {
                Camera.Size defaltSize = paramParameters.getPreviewSize();
                size2 = defaltSize;
            }
        }
        return size2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
        if (bitmap1 != null) {
            if (!bitmap1.isRecycled()) {
                bitmap1.recycle();
                bitmap1 = null;
            }
        }
    }

    /**
     * @return void 返回类型
     * @throws
     * @Title: closeCamera
     * @Description: TODO(这里用一句话描述这个方法的作用) 关闭相机
     */
    private void closeCamera() {
        // TODO Auto-generated method stub
        System.out.println("关闭相机 ");
        synchronized (this) {
            try {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (time != null) {
                    time.cancel();
                    time = null;
                }
                if (camera != null) {
                    camera.setPreviewCallback(null);
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }

            } catch (Exception e) {

            }
        }
    }

    /**
     * @param @param context 设定文件
     * @return void 返回类型
     * @throws
     * @Title: setScreenSize
     * @Description: 这里用一句话描述这个方法的作用) 获取屏幕真实分辨率，不受虚拟按键影响
     */
    @SuppressLint("NewApi")
    private void setScreenSize(Context context) {
        int x, y;
        WindowManager wm = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE));
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point screenSize = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(screenSize);
                x = screenSize.x;
                y = screenSize.y;
            } else {
                display.getSize(screenSize);
                x = screenSize.x;
                y = screenSize.y;
            }
        } else {
            x = display.getWidth();
            y = display.getHeight();
        }
        width = x;
        height = y;
    }

    //获取屏幕尺寸
    public void getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    //根据屏幕尺寸以及预览分辨率  给surfaceView重新定义尺寸，避免图像拉伸情况的出现
    public void getPreToChangView(int preWidth, int preHeight) {
        //横屏下
        if (width >= height) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.TRUE, RelativeLayout.TRUE);
            surfaceView.setLayoutParams(layoutParams);
        }
        //竖屏下
        if (height >= width) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.TRUE, RelativeLayout.TRUE);
            surfaceView.setLayoutParams(layoutParams);
        }
    }

    public void OpenCameraAndSetParameters() {
        try {
            if (null == camera) {
                camera = Camera.open();
                gestureDetector = new ScaleGestureDetector(this, new ScaleGestureListener());
            }
            if (timer == null) {
                timer = new TimerTask() {
                    @Override
                    public void run() {
                        // isSuccess=false;
                        if (camera != null) {
                            try {
                                camera.autoFocus(new Camera.AutoFocusCallback() {
                                    @Override
                                    public void onAutoFocus(boolean success,
                                                            Camera camera) {
                                        // isSuccess=success;

                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    ;
                };
            }
            time = new Timer();
            time.schedule(timer, 500, 2500);
            if (!isFirstIn) {
                initCamera(holder, initPreWidth, initPreHeight);
            }
            isFirstIn = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float mLastTouchX;
    private float mLastTouchY;
    /**
     * 向外放缩标志
     */
    private static final int ZOOM_OUT = 0;
    /**
     * 向内放缩标志
     */
    private static final int ZOOM_IN = 1;

    //重写onTouchEvent方法 获取手势
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //识别手势
        gestureDetector.onTouchEvent(event);
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                break;
            }
        }
        return true;
    }

    //操作类
    class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        int mScaleFactor;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            try {
                mScaleFactor = (int) detector.getScaleFactor();
                Camera.Parameters params = camera.getParameters();
                int zoom = params.getZoom();
                if (mScaleFactor == ZOOM_IN) {
                    if (zoom < params.getMaxZoom())
                        zoom += 1;
                } else if (mScaleFactor == ZOOM_OUT) {
                    if (zoom > 0)
                        zoom -= 1;
                }
                params.setZoom(zoom);
                camera.setParameters(params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeCamera();
        finish();
    }

    /**
     * 拍照选择监听
     */
    public interface OnCameraListener {

        /**
         * 选择回调
         *
         * @param file          文件
         */
        void onSelected(File file, String number, String color);

        /**
         * 错误回调
         *
         * @param details       错误详情
         */
        void onError(String details);

        /**
         * 取消回调
         */
        void onCancel();
    }
}