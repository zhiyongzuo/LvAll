package com.epro.lvall;

import android.text.TextUtils;

import org.junit.Test;

/**
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 *
 * 示例 1:
 *
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 * 示例 2:
 *
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 * 说明：
 *
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/multiply-strings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author zzy
 * @date 2020/10/22
 */
public class _43 {

    @Test
    public void test() {
        //测试 空格索引位置
        //String s = "Hello World s";
        //System.out.println(s.indexOf(" "));//5
        //System.out.println(s.lastIndexOf(" "));//11

        //测试char转int
//        int temp1 = "5".charAt(0);
//        int temp2 = "5".charAt(0) - '0';//可以cha转int，正常
//        System.out.println("temp1=" + temp1 + "   temp2=" + temp2);
        //测试在6后面加三个0
//        System.out.println(6*Math.pow(10, 3));//6000.0
//        System.out.println((int) (6 * Math.pow(10, 3)));//6000

//        System.out.println(multiply("123", "456"));
//        System.out.println(multiply("123456789", "987654321"));//"121932631112635269"
//        System.out.println(multiply("498828660196", "840477629533"));
        //419254329864656431168468
        //Long类型最大
        //9223372036854775807

//        Map<Character, Integer> currentMap = new HashMap<>();
//        System.out.println(currentMap.get("A"));//null
        //“a”的值是129，转换成二进制就是10000001，而“b”的值是128，转换成二进制就是10000000。根据与运算符的运算规律，只有两个位都是1，结果才是1，可以知道结果就是10000000，即128。
//        System.out.println(2&2);//2//
//        System.out.println(0&0);//0
//        System.out.println(1&1);//1
//        System.out.println(1&2);//0

//        System.out.println(new Solution().multiply("123", "456"));
    }

    public String multiply(String num1, String num2) {
        if(TextUtils.equals(num1, "0") || TextUtils.equals(num2, "0")) {
            return "0";
        }

        long tempResult = 0;
        for(int i=num1.length()-1; i>=0; i--) {
            long tempNumI = num1.charAt(i) - '0';
            if(i<num1.length()-1) {
                tempNumI = (long) (tempNumI * Math.pow(10, num1.length()-i-1));
            }
            for(int j=num2.length()-1; j>=0; j--) {
                long tempNumJ = num2.charAt(j) - '0';
                if(j<num2.length()-1) {
                    tempNumJ = (long) (tempNumJ * Math.pow(10, num2.length()-j-1));
                }
                tempResult += tempNumI * tempNumJ;
            }
        }
        return String.valueOf(tempResult);
    }

    class Solution {
        /**
         * 计算形式
         *    num1
         *  x num2
         *  ------
         *  result
         */
        public String multiply(String num1, String num2) {
            if (num1.equals("0") || num2.equals("0")) {
                return "0";
            }
            // 保存计算结果
            String res = "0";

            // num2 逐位与 num1 相乘
            for (int i = num2.length() - 1; i >= 0; i--) {
                int carry = 0;
                // 保存 num2 第i位数字与 num1 相乘的结果
                StringBuilder temp = new StringBuilder();
                // 补 0
                for (int j = 0; j < num2.length() - 1 - i; j++) {
                    temp.append(0);
                }
                int n2 = num2.charAt(i) - '0';

                // num2 的第 i 位数字 n2 与 num1 相乘
                for (int j = num1.length() - 1; j >= 0 || carry != 0; j--) {
                    int n1 = j < 0 ? 0 : num1.charAt(j) - '0';
                    int product = (n1 * n2 + carry) % 10;
                    temp.append(product);
                    carry = (n1 * n2 + carry) / 10;
                }
                // 将当前结果与新计算的结果求和作为新的结果
                res = addStrings(res, temp.reverse().toString());
            }
            return res;
        }

        /**
         * 对两个字符串数字进行相加，返回字符串形式的和
         */
        public String addStrings(String num1, String num2) {
            StringBuilder builder = new StringBuilder();
            int carry = 0;
            for (int i = num1.length() - 1, j = num2.length() - 1;
                 i >= 0 || j >= 0 || carry != 0;
                 i--, j--) {
                int x = i < 0 ? 0 : num1.charAt(i) - '0';
                int y = j < 0 ? 0 : num2.charAt(j) - '0';
                int sum = (x + y + carry) % 10;
                builder.append(sum);
                carry = (x + y + carry) / 10;
            }
            return builder.reverse().toString();
        }
    }
}
