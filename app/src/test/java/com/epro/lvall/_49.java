package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 49. 字母异位词分组
 * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
 *
 * 示例:
 *
 * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出:
 * [
 *   ["ate","eat","tea"],
 *   ["nat","tan"],
 *   ["bat"]
 * ]
 * 说明：
 *
 * 所有输入均为小写字母。
 * 不考虑答案输出的顺序。
 * @author zzy
 * @date 2020/10/25
 */
public class _49 {

    @Test
    public void test() {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
//        String[] strs = {"ddddddddddg","dgggggggggg"};
//        String[] strs = {"",""};
        System.out.println(GsonUtils.toJson(groupAnagrams(strs)));
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> returnList = new ArrayList<>();
        //用来记录已使用的
        List<Integer> existList = new ArrayList<>();

        for(int i=0; i<strs.length; i++) {
            if(!existList.contains(i)) {
                List<String> tempList = new ArrayList<>();
                existList.add(i);
                String currentStr = strs[i];
                tempList.add(currentStr);

                Map<Character, Integer> currentMap = new HashMap<>();
                for(int m=0; m<currentStr.length(); m++) {
                    char indexChat = currentStr.charAt(m);
                    if(currentMap.get(indexChat)==null) {
                        currentMap.put(indexChat, 1);
                    } else {
                        int indexValue = currentMap.get(indexChat);
                        currentMap.put(indexChat, indexValue+1);
                    }
                }
                for(int j=i+1; j<strs.length; j++) {
                    if (strs[j].length()==currentStr.length()) {
                        if(strs[j].length()==0) {
                            existList.add(j);
                            tempList.add(strs[j]);
                        }

                        Map<Character, Integer> tempMap = new HashMap<>();
                        for(int m=0; m<strs[j].length(); m++) {
                            char indexChat = strs[j].charAt(m);
                            if(tempMap.get(indexChat)==null) {
                                tempMap.put(indexChat, 1);
                            } else {
                                int indexValue = tempMap.get(indexChat);
                                tempMap.put(indexChat, indexValue+1);
                            }
                            if(currentMap.get(indexChat)==null || tempMap.get(indexChat)>currentMap.get(indexChat)) {
                                break;
                            }
                            if(m==strs[j].length()-1) {
                                existList.add(j);
                                tempList.add(strs[j]);
                            }
                        }
                    }
                }
                returnList.add(tempList);
            }
        }
        return returnList;
    }
}
