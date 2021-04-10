package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

/**
 * @author zzy
 * @date 2021/1/5
 *
 * 66. 加一
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 *
 *
 * 示例 1：
 *
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * 示例 2：
 *
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * 示例 3：
 *
 * 输入：digits = [0]
 * 输出：[1]
 *
 *
 * 提示：
 *
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */
public class _66 {

    @Test
    public void test() {
//        int[] digits = {1,2,3};
        int[] digits = {4,3,2,1};
//        int[] digits = {9, 9};
//        int[] digits = {9};
//        int[] digits = {0};
//        int[] digits = {8, 9, 9, 9};
        System.out.println(GsonUtils.toJson(plusOne(digits)));
    }

    public int[] plusOne(int[] digits) {
        int extraDigits = 0;
        for(int i=digits.length-1; i>=0; i--) {
            if(i==digits.length-1) {
                //最后一位
                if(digits[i] + 1 + extraDigits > 9) {
                    extraDigits = 1;
                    digits[i] = 0;
                    if(i==0) {
                        //只有一位，且大于9 (最后一位即是第一位)
                        int[] newDigits = new int[digits.length+1];
                        for(int j=0; j<newDigits.length; j++) {
                            if(j==0) {
                                newDigits[j] = 1;
                            } else {
                                newDigits[j] = digits[j-1];
                            }
                        }
                        return  newDigits;
                    }
                } else {
                    digits[i] += 1;
                }
            } else  {
                if(digits[i] + extraDigits > 9) {
                    extraDigits = 1;
                    digits[i] = 0;
                    if(i==0) {
                        //首位大于9
                        int[] newDigits = new int[digits.length+1];
                        for(int j=0; j<newDigits.length; j++) {
                            if(j==0) {
                                newDigits[j] = 1;
                            } else {
                                newDigits[j] = digits[j-1];
                            }
                        }
                        return  newDigits;
                    }
                } else {
                    digits[i] += extraDigits;
                    extraDigits = 0;
                }
            }
        }
        return digits;
    }

}
