package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

/**
 *
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 *
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 *
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 *
 * 示例 1:
 *
 * 给定 nums = [3,2,2,3], val = 3,
 *
 * 函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
 *
 * 你不需要考虑数组中超出新长度后面的元素。
 *
 *
 * @author zzy
 * @date 2020/10/14
 */
public class _27 {


    @Test
    public void test() {
        int[] testArray = {3, 2, 2, 3};
        System.out.println(removeElement(testArray, 3));
    }

    public int removeElement(int[] nums, int val) {
        int index = 0;
        for(int i=0; i<nums.length; i++) {
            if(nums[i]==val) {
                nums[i] = getLastCompatNum(i+1, nums, val);
                if(nums[i]!=val) {
                    index++;
                }
            } else {
                index++;
            }
        }
        System.out.println(GsonUtils.toJson(nums));
        return index;
    }

    public Integer getLastCompatNum(int startNum, int[] nums, int val) {
        for(int i=startNum; i<nums.length; i++) {
            if(val!=nums[i]) {
                int temp = nums[i];
                nums[i] = val;
                return temp;
            }
        }
        return val;
    }
}
