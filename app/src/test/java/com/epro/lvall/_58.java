package com.epro.lvall;

import org.junit.Test;

/**
 * 58. 最后一个单词的长度
 * 给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
 *
 * 如果不存在最后一个单词，请返回 0 。
 *
 * 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。
 *
 * 输入: "Hello World"
 * 输出: 5
 * @author zzy
 * @date 2020/11/2
 */
public class _58 {
    @Test
    public void test() {
//        String s = "Hello World s";
//        String s = "        ";
//        String s = "a";
//        String s = " a";
//        String s = "a ";
        String s = "b   a    ";
        System.out.println(lengthOfLastWord(s));
    }

    public int lengthOfLastWord(String s) {
        if(" ".equals(s) || "".equals(s)) {
            return 0;
        }
        //a
        if(s.lastIndexOf(" ") == 0 && s.length()==1) {
            return 1;
        }
        //a**
        //**:空格
        while (s.lastIndexOf(" ") == s.length()-1 && s.length()>0) {
            s = s.substring(0, s.length()-1);
        }
        return s.length() - s.lastIndexOf(" ") -1;
    }
}
