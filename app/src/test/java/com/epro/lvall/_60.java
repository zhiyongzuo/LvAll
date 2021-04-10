package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 60. 排列序列
 * 给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
 *
 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
 *
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * 给定 n 和 k，返回第 k 个排列。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 3, k = 3
 * 输出："213"
 * 示例 2：
 *
 * 输入：n = 4, k = 9
 * 输出："2314"
 * 示例 3：
 *
 * 输入：n = 3, k = 1
 * 输出："123"
 *
 *
 * 提示：
 *
 * 1 <= n <= 9
 * 1 <= k <= n!
 * @date 2020/11/5
 */
public class _60 {

    @Test
    public void test() {
        System.out.println(getPermutation(3, 1));
//        System.out.println(getPermutation(9, 135401));
    }

    public String getPermutation(int n, int k) {
        List<List<Integer>> allNumList = new ArrayList<>();
        getAllPossible(1, new Stack<Integer>(), n, allNumList);

        System.out.println(GsonUtils.toJson(allNumList));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<allNumList.get(k-1).size(); i++) {
            stringBuilder.append(allNumList.get(k-1).get(i));
        }
        return stringBuilder.toString();
    }

    public void getAllPossible(int start, Stack<Integer> integerStack, int n, List<List<Integer>> returnList) {
        if(integerStack.size()==n) {
            returnList.add(new ArrayList<>(integerStack));
            return;
        }
        for(int i=1; i<=n; i++) {
            if(integerStack.contains(i)) {
                continue;
            }
            integerStack.push(i);
            getAllPossible(i+1, integerStack, n, returnList);
            integerStack.pop();
        }
    }
}
