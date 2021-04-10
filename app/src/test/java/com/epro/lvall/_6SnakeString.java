package com.epro.lvall;

import com.blankj.utilcode.util.LogUtils;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zzy
 * @date 2020/9/21
 */
public class _6SnakeString {
    @Test
    public void testSnakeString() {
        System.out.println(getSnakeString());
    }

    @NotNull
    private String getSnakeString() {
        String s = "LEETCODEISHIRING";// "LCIRETOESIIGEDHN"
        int rowNum = 3;

        List<StringBuilder> stringList = new ArrayList<>();
        for(int i=0; i<rowNum; i++) {
            stringList.add(new StringBuilder());
        }

        int indexRow = 0;
        int arrowIndex = 0;
        for(int i=0; i<s.length(); i++) {
            stringList.get(indexRow).append(s.charAt(i));
            if(indexRow==0) {
                arrowIndex = 1;
            } else if(indexRow==rowNum - 1) {
                arrowIndex = -1;
            }
            indexRow += arrowIndex;
        }

        StringBuilder returnStringBuilder = new StringBuilder();
        for(int i=0; i<stringList.size(); i++) {
            returnStringBuilder.append(stringList.get(i));
        }

        return returnStringBuilder.toString();
    }
}
