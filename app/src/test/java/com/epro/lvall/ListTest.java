package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    @Test
    public void test() {
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("3");
        stringList.add("5");
        stringList.add("7");
        stringList.add("9");

        System.out.println(GsonUtils.toJson(stringList));//["1","3","5","7","9"]
        stringList.add(1, "0");
        System.out.println(GsonUtils.toJson(stringList));//["1","0","3","5","7","9"]

    }
}
