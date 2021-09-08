package com.epro.image;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

public class GlideFilePathTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_file_path_test);

        final String picUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0616%252F945ec1baj00qusayy000ic000hs00arc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1628413590&t=ddbfa67c67c17106cd06d4df49ab8558";
        TextView tvTest = findViewById(R.id.tv_test);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("以点击");
                ThreadUtils.executeBySingle(new ThreadUtils.SimpleTask<Object>() {

                    @Override
                    public Object doInBackground() throws Throwable {
                        try {
                            FutureTarget<File> target = Glide.with(GlideFilePathTestActivity.this)
                                    .downloadOnly()
                                    .load(picUrl)
                                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                            File imageFile = target.get();
                            LogUtils.e(imageFile.getAbsolutePath());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return new Object();
                    }

                    @Override
                    public void onSuccess(Object result) {

                    }
                });
            }
        });
    }
}