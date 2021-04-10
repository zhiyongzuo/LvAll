package com.epro.lvall;

import org.junit.Test;

/**
 * @author zzy
 * @date 2021/1/6
 *
 * 67. 二进制求和
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 *
 * 输入为 非空 字符串且只包含数字 1 和 0。
 *
 *
 *
 * 示例 1:
 *
 * 输入: a = "11", b = "1"
 * 输出: "100"
 * 示例 2:
 *
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 *
 * 输入: a = "11", b = "11"
 * 输出: "110"
 *
 * 提示：
 *
 * 每个字符串仅由字符 '0' 或 '1' 组成。
 * 1 <= a.length, b.length <= 10^4
 * 字符串如果不是 "0" ，就都不含前导零。
 */
public class _67 {

    @Test
    public void test() {
//        System.out.println(addBinary("11", "1"));
//        System.out.println(addBinary("111", "1"));
        System.out.println(addBinary("1011", "1"));
    }

    public String addBinary(String a, String b) {
        int returnNum = 0;
        int extraNum = 0;
        int minLength = Math.min(a.length(), b.length());
        for(int i=minLength-1; i>=0; i--) {
            int ai = Integer.parseInt(String.valueOf(a.charAt(i)));
            int bi = Integer.parseInt(String.valueOf(b.charAt(i)));
            if(extraNum + ai + bi > 2) {
                extraNum = 1;
                returnNum += Math.pow(10, minLength - i) + Math.pow(10, minLength-1-i);
            } else if(extraNum + ai + bi > 1) {
                extraNum = 1;
                returnNum += Math.pow(10, minLength - i);
            } else {
                extraNum = 0;
            }
        }

        String longStr = a.length()>b.length() ? a : b;
        for(int i=longStr.length()-minLength-1; i>=0; i--) {
            int longi = Integer.parseInt(String.valueOf(longStr.charAt(i)));
            if(longi + extraNum > 1) {
                //1011   1
                extraNum = 1;
                returnNum += Math.pow(10, longStr.length()-i);
                returnNum -= Math.pow(10, longStr.length()-i-1);
            } else {
                extraNum = 0;
                if(returnNum-Math.pow(10, longStr.length()-i)<0) {
                    returnNum += Math.pow(10, longStr.length()-i);
                }
            }
        }
        return String.valueOf(returnNum);
    }
}
