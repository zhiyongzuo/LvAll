package com.epro.lvall;

import org.junit.Test;

/**
 *
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 *
 * 示例 1:
 *
 * 输入: "(()"
 * 输出: 2
 * 解释: 最长有效括号子串为 "()"
 * 示例 2:
 *
 * 输入: ")()())"
 * 输出: 4
 * 解释: 最长有效括号子串为 "()()"
 * @date 2020/10/16
 *
 */
public class _32 {
    @Test
    public void test() {
//        String s = ")()())";
        String s = "(()";
        System.out.println(longestValidParentheses(s));
    }

    public int longestValidParentheses(String s) {
        int maxLength = 0;
        for(int i=0; i<s.length(); i++) {
            int leftCount = 0;
            for(int j=i+1; j<s.length(); j++) {
                if(s.charAt(j)=='(') {
                    leftCount++;
                } else {
                    leftCount--;
                }
                if(leftCount<0) {
                    break;
                }
                int tempMaxLength = j-i+1;
                if(leftCount==0 && tempMaxLength>maxLength) {
                    maxLength = tempMaxLength;
                }
            }
        }
        return maxLength;
    }


}
