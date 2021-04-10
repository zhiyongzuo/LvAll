package com.epro.lvall;

import com.blankj.utilcode.util.LogUtils;

import org.junit.Test;

/**
 * @author zzy
 * @date 2020/9/21
 */
public class _07ReverseInt {
    @Test
    public void testReverseInt() {
        System.out.println(reverseInt());
//        System.out.println((int)Math.pow(2, 31)-1);//2147483646  21亿
    }

    /**
     * 123---->321
     * 00000000 00000000 00000000------>
     *
     * 1 + 2 + 3 + ... a1 + (n-1)*d = n(a1+an)/2 = sn
     * 1 + 2 + 4 + ... a1q^(n-1)  = a1(1-q^n)/(1-q) = sn--------2^n-1(q=2)
     *
     * @return
     */
    public int reverseInt() {
        int sampleInt = -2147483648;

//        int maxInt = (int)Math.pow(2, 31)-1;
//        int minInt = -(int)Math.pow(2, 31);
//
//        StringBuilder returnStringBuilder = new StringBuilder(String.valueOf(sampleInt)).reverse();
//        String returnString = returnStringBuilder.toString();
//        if(returnString.charAt(returnString.length()-1) == '-') {
//            returnString = "-" + returnString.substring(0, returnString.length()-1);
//        }
//
//        if(returnString.charAt(0) == '0' && sampleInt!=0) {
//            returnString = returnString.substring(1);
//        }
//
//        if(Long.parseLong(returnString) > maxInt || Long.parseLong(returnString)<minInt) {
//            return 0;
//        } else {
//            return Integer.parseInt(returnString);
//        }

        int reverseSampleInt = sampleInt;
        if (sampleInt<0) {
            reverseSampleInt = -sampleInt;
        }
        StringBuilder returnString = new StringBuilder();
        String sampleString = String.valueOf(reverseSampleInt);
        int x = 1;
        for(int i=sampleString.length()-1; i>=0; i--) {
            System.out.println(((int) Math.pow(10, i)));
            x = (int) Math.pow(10, i);
            returnString.append(reverseSampleInt/x);
            reverseSampleInt %= x;
        }
        int returnInt = 0;
        Integer.parseInt(returnString.reverse().toString());
        return sampleInt<0 ? -returnInt : returnInt;
    }
}
