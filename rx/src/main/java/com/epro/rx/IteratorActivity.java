package com.epro.rx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class IteratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iterator);
        List<Integer> integerList = new CopyOnWriteArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);

        int size;
        int i=0;
        do{
            size = integerList.size();
            LogUtils.e(GsonUtils.toJson(integerList.get(i)));
            i++;
            if(i==size) {
                break;
            }
            if(i==2) {
                integerList.add(5);
                integerList.add(6);
                integerList.add(7);
            }
            LogUtils.e(GsonUtils.toJson(integerList));
        } while (true);

//        Iterator iterator = integerList.iterator();
//        int i=1;
//        do{
//            iterator = integerList.iterator();
//            LogUtils.e(i++);
//            if((Integer)iterator.next()==2) {
//                integerList.add(5);
//                integerList.add(6);
//                integerList.add(7);
//            }
//            LogUtils.e(GsonUtils.toJson(integerList));
//        } while (iterator.hasNext());


    }
}
