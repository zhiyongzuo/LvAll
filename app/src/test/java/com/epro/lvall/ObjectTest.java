package com.epro.lvall;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author zzy
 * @date 2021/8/28
 */
public class ObjectTest {

    @Test
    public void test() {
        GsonTest gsonTest = new GsonTest();
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Integer> intArrayList = new ArrayList<>();

        System.out.println(gsonTest.getClass());//class com.epro.lvall.GsonTest
        System.out.println(stringArrayList.getClass());//class java.util.ArrayList
        System.out.println(intArrayList.getClass());//class java.util.ArrayList
    }
}
