package com.epro.fragment.viewpagertest;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author zzy
 * @date 2021/8/26
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;


    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> mFragmentList) {
        super(fm, behavior);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        int position = mFragmentList.indexOf(object);
        Log.e("TAG", "getItemPosition:  position = "+position );
//        return position;
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
