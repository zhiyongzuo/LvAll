package com.epro.fragment.viewpagertest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epro.fragment.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author zzy
 * @date 2021/7/6
 */
public class TestFragment extends Fragment {

    public final static String TAG = TestFragment.class.getSimpleName();
    private String test;
    public View mContentView;
    public int index = 0;

    public void setTest(String test) {
        this.test = test;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate:  test = "+test + "  index=" + index++);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.layout_fg, null);
        Log.e(TAG, "onCreateView: test = "+test);
        return mContentView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: test = "+test);
        TextView testText = mContentView.findViewById(R.id.tv_test);
        testText.setText(test);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: test = "+test);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: test = "+test + "  index=" + index++ + "  " + hashCode());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy:  test = "+test);
    }
}
