package com.epro.lvall;

import org.junit.Test;

/**
 *
 * 50. Pow(x, n)
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 *
 * 示例 1:
 *
 * 输入: 2.00000, 10
 * 输出: 1024.00000
 * 示例 2:
 *
 * 输入: 2.10000, 3
 * 输出: 9.26100
 * 示例 3:
 *
 * 输入: 2.00000, -2
 * 输出: 0.25000
 * 解释: 2-2 = 1/22 = 1/4 = 0.25
 * 说明:
 *
 * -100.0 < x < 100.0
 * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。
 *
 *
 * @author zzy
 * @date 2020/10/26
 */
public class _50 {

    @Test
    public void test() {
        System.out.println(myPow(2.0, 10));
//        System.out.println(myPow(2.0, -2));
//        System.out.println(myPow(2.1, 2147483647));//3s350ms
//        System.out.println(new Solution().myPow(2.0, 10));//0ms
    }

    public double myPow(double x, int n) {
        boolean negative = n<0;
        long b = n;
        if(negative) {
            b = -b;
        }
        double returnDouble = 1;

        while (b>0) {
            if((b & 1) == 1) {
                //1,5会进入，2不会----奇数进入
                //(1,5)0000 0001 & 0000 0101 = 0000 0001
                //(1,2)0000 0001 & 0000 0010 = 0000 0000

                //2^10=4^5=4*16^2=4*256^1
                returnDouble *= x;
            }
            x *= x;
            b>>=1;
        }
        return negative ? 1/returnDouble : returnDouble;
    }

    class Solution {
        public double myPow(double x, int n) {
            if(x == 0.0f) return 0.0d;
            long b = n;
            double res = 1.0;
            if(b < 0) {
                x = 1 / x;
                b = -b;
            }
            while(b > 0) {
                if((b & 1) == 1) res *= x;
                x *= x;
                b >>= 1;
            }
            return res;
        }
    }

}
