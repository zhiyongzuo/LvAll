package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 46. 全排列
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 * @author zzy
 * @date 2020/10/22
 */
public class _46 {

    @Test
    public void test() {
        int[] nums = {1,2,3};
//        int[] nums = {1};
//        int[] nums = {1,1,2};
        System.out.println(GsonUtils.toJson(permute(nums)));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> returnList = new ArrayList<>();
        getSingle(nums,0, new Stack<Integer>(), returnList);
        return returnList;
    }

    public void getSingle(int[] nums, int startIndex, Stack<Integer> integerStack, List<List<Integer>> returnList) {
        if(integerStack.size()==nums.length) {
            returnList.add(new ArrayList<>(integerStack));
            return;
        }
        for(int i=0; i<nums.length; i++) {
            if (!integerStack.contains(nums[i])) {
                integerStack.push(nums[i]);
                getSingle(nums, i, integerStack, returnList);
                integerStack.pop();
            }
        }
    }

    @Test
    public void testExample() {
        int[] nums = {1,2,3};

//        Integer[] intArray1 = {1,2,3};
//        List<Integer> list1 = Arrays.asList(intArray1);//int[]打印[[@84573]]        Integer[]打印[1, 2, 3]
//        System.out.println(list1);

//        List<Integer> testList = new ArrayList<Integer>();
//        for (int num : nums) {
//            testList.add(num);
//        }
//        Collections.swap(testList, 0, 1);
//        System.out.println(GsonUtils.toJson(testList));

        System.out.println(GsonUtils.toJson(new Solution().permute(nums)));
    }

    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<List<Integer>>();

            List<Integer> output = new ArrayList<Integer>();
            for (int num : nums) {
                output.add(num);
            }

            int n = nums.length;
            backtrack(n, output, res, 0);
            return res;
        }

        public void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
            // 所有数都填完了
            if (first == n) {
                res.add(new ArrayList<Integer>(output));
            }
            for (int i = first; i < n; i++) {
                // 动态维护数组
                Collections.swap(output, first, i);
                System.out.println("before" + output);
                // 继续递归填下一个数
                backtrack(n, output, res, first + 1);
                // 撤销操作
                Collections.swap(output, first, i);
                System.out.println("after" + output);

            }
        }
    }
}
