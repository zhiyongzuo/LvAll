package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2020/9/30
 */
public class _17 {

    /**
     * 1
     * 12   1,2,12
     * 123  1,23  1,(2,3,23)  想多了，不是这样的，应该是
     * 输入："23"
     * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     *
     */
//    @Test
//    public void test() {
//        System.out.println(GsonUtils.toJson(letterCombinations("23")));
//    }
//
//    private void findCombination(String digits, int index, String s){
//        if(index == digits.length()){
//            res.add(s);
//            return;
//        }
//
//        Character c = digits.charAt(index);
//        String letters = letterMap[c - '0'];
//        for(int i = 0 ; i < letters.length() ; i ++){
//            findCombination(digits, index+1, s + letters.charAt(i));
//        }
//
//        return;
//    }

//    public List<String> letterCombinations(String digits) {
//        List<String> returnString = new ArrayList<>();
//        for(int i=0; i<digits.length(); i++) {
//            //获取i个字母的组合
//            getIAppendString(digits, i+1, returnString);
//        }
//        return  returnString;
//    }
//
//    public List<String> getIAppendString(String digits, int maxLength, List<String> returnString) {
//        if(maxLength==1) {
//            return getCorrespondenseString(digits.charAt(0));
//        }else if(maxLength==2) {
//            getAppendString(digits.charAt(0), digits.charAt(1), returnString);
//            return returnString;
//        }
//        //三个以上字母
//        for(int i=0; i<digits.length(); i++) {
//            getIAppendString(digits.substring(i, i+maxLength-1), i+1, returnString);
//        }
//        return returnString;
//    }

//    public List<String> getAppendString(char s1, String s2) {
//        if(s2.length()==1) {
//            return getAppendString(s1, s2.charAt(0));
//        } else {
//            getAppendString()
//        }
//    }

//    public List<String> getAppendString(char s1, char s2, List<String> returnStringList) {
//        List<String> s1List = getCorrespondenseString(s1);
//        List<String> s2List = getCorrespondenseString(s2);
//        for(int i=0; i<s1List.size(); i++) {
//            for(int j=0; j<s2List.size(); j++) {
//                returnStringList.add(s1List.get(i) + s2List.get(j));
//            }
//        }
//    }
//
//    public List<String> getCorrespondenseString(char numChar) {
//        if(numChar=='1') {
//
//        }
//    }

    private String letterMap[] = {
            " ",    //0
            "",     //1
            "abc",  //2
            "def",  //3
            "ghi",  //4
            "jkl",  //5
            "mno",  //6
            "pqrs", //7
            "tuv",  //8
            "wxyz"  //9
    };

    private ArrayList<String> res;

    public List<String> letterCombinations(String digits) {
        res = new ArrayList<String>();
        if(digits.equals(""))
            return res;

        findCombination(digits, 0, "");
        return res;
    }

    private void findCombination(String digits, int index, String s){
        if(index == digits.length()){
            res.add(s);
            return;
        }

        Character c = digits.charAt(index);
        String letters = letterMap[c - '0'];
        for(int i = 0 ; i < letters.length() ; i ++){
            findCombination(digits, index+1, s + letters.charAt(i));
        }

        return;
    }

    @Test
    public void test() {
        System.out.println(GsonUtils.toJson(letterCombinations("234")));
    }
}
