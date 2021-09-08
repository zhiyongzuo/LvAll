package com.epro.fragment.viewpagertest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.epro.fragment.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * ViewPager 刷新无效?
     * https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650825533&idx=1&sn=e7bfcc69f9804d738ee0c7ed7fefb900&chksm=80b7b7a3b7c03eb57fd372423b0f27c00310a898ed783f9ad09c8d327fa5528e93e5539f024d&mpshare=1&scene=1&srcid=0630ksG6IL5Cnr2gQpLyPDMH&sharer_sharetime=1625024439929&sharer_shareid=11a6c02d9b1604cd790154b9cc676a7f&version=3.1.8.3015&platform=win#rd
 */
public class TestActivity extends FragmentActivity implements View.OnClickListener {

    List<Fragment> mFragmentList;
    ViewPager mViewPager;
    public BaseFragmentPagerAdapter mAdapter;
//    public MyFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mViewPager = findViewById(R.id.vp);
        findViewById(R.id.btn_change).setOnClickListener(this);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(getFg("AAA"));
        mFragmentList.add(getFg("BBB"));
        mFragmentList.add(getFg("CCC"));
        mFragmentList.add(getFg("DDD"));
        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
//        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mFragmentList);
        mViewPager.setAdapter(mAdapter);
    }

    private TestFragment getFg(String a){
        TestFragment fragment = new TestFragment();
        fragment.setTest(a);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        Log.e("vvv", "11111 onClick: mFragmentList.get(0).hashCode() = "+ mFragmentList.get(0).hashCode());
        mFragmentList = new ArrayList<>();
        mFragmentList.add(getFg("AAA"));
        mFragmentList.add(getFg("BBB"));
        mFragmentList.add(getFg("CCC"));
        mFragmentList.add(getFg("DDD"));
        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
//        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mFragmentList);
        mViewPager.setAdapter(mAdapter);
        Log.e("vvv", "22222 onClick: mFragmentList.get(0).hashCode() = "+ mFragmentList.get(0).hashCode());


//        TestFragment eee = getFg("EEE");
//        //新增
//        mAdapter.addFragment(eee);
//        //插入
//        mAdapter.insertFragment(1, eee);
//        //删除
//        mAdapter.removeFragment(1);
//        //删除
//        mAdapter.removeFragment(mFragmentList.get(1));
//        //替换
//        mAdapter.replaceFragment(1, eee);
//        //替换
//        mAdapter.replaceFragment(mFragmentList.get(0), eee);
    }
}