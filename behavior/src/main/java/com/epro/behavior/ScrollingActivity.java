package com.epro.behavior;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.epro.behavior.behaviordemo.TestActivity1;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class ScrollingActivity extends AppCompatActivity {
    private AppBarLayout mAppBarLayout;
    private TextView tvRed, tvGreen, tvBlue;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        mCoordinatorLayout = findViewById(R.id.coordinator);
        mAppBarLayout = findViewById(R.id.app_bar);
        tvRed = findViewById(R.id.tv_1);
        tvGreen = findViewById(R.id.tv_2);
        tvBlue = findViewById(R.id.tv_3);

        tvRed.setBackgroundColor(Color.RED);
        tvGreen.setBackgroundColor(Color.GREEN);
        tvBlue.setBackgroundColor(Color.BLUE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                //每次点击，appBarLayout中的target View都会滑动个dy距离

//                LogUtils.e("tvBlue.getTop()=" + tvBlue.getTop() + "  mAppBarLayout.getTotalScrollRange()=" + mAppBarLayout.getTotalScrollRange()
//                        + "   mAppBarLayout.getTotalScrollRange()-tvBlue.getTop()=" + (mAppBarLayout.getTotalScrollRange()-tvBlue.getTop())
//                        + "   tvBlue.getTop()-mAppBarLayout.getScrollY()=" + (tvBlue.getTop()-mAppBarLayout.getScrollY())
//                );
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)mAppBarLayout.getLayoutParams();
                CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
//                behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, tvBlue, 0, tvBlue.getTop(), new int[]{0, 0}, ViewCompat.TYPE_TOUCH);
//                behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, tvBlue, 0, 0, new int[]{0, 0}, ViewCompat.TYPE_TOUCH);//不动

                int[] outLocation = new int[2];
                tvBlue.getLocationOnScreen(outLocation);
                LogUtils.e("outLocation[0]=" + outLocation[0] + "  outLocation[1]=" + outLocation[1]);
                behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, tvBlue, 0, outLocation[1], new int[]{0, 0}, ViewCompat.TYPE_TOUCH);//
                //测试 dy为负数  负数没效果
//                behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, tvBlue, 0, -500, new int[]{0, 0}, ViewCompat.TYPE_TOUCH);//

                ActivityUtils.startActivity(new Intent(ScrollingActivity.this, TestActivity1.class));
            }
        });
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)mAppBarLayout.getLayoutParams();
//                CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
//                int[] outLocation = new int[2];
//                tvBlue.getLocationOnScreen(outLocation);
//                LogUtils.e("outLocation[0]=" + outLocation[0] + "  outLocation[1]=" + outLocation[1]);
//                if(outLocation[1]< ScreenUtils.getScreenHeight()-tvBlue.getHeight()) {
//                    behavior.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, tvBlue, 0, 0
//                            , new int[]{0, 0}, ViewCompat.TYPE_TOUCH);
//
//                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}