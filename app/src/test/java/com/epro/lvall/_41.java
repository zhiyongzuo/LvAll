package com.epro.lvall;

import org.junit.Test;

import java.util.Arrays;

/**
 * 41. 缺失的第一个正数
 * 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
 *
 *
 *
 * 示例 1:
 *
 * 输入: [1,2,0]--------0,2    0,3
 * 输出: 3
 * 示例 2:
 *
 * 输入: [3,4,-1,1]   -------2,4    2,5    2,5
 * 输出: 2
 * 示例 3:
 *
 * 输入: [7,8,9,11,12]
 * 输出: 1
 * @author zzy
 * @date 2020/10/20
 */
public class _41 {

    @Test
    public void test() {
//        int[] nums = {1,2,0};
//        int[] nums = {-1,-2,60,40,43};
//        int[] nums = {3,4,-1,1};
//        int[] nums = {2,1};
//        int[] nums = {1,100};
//        int[] nums = {1};
//        int[] nums = {-1,-2};
//        int[] nums = {2,2};
//        int[] nums = {1,0};
        int[] nums = {1,2,3,4,5,6,7,8,9,20};
        System.out.println(firstMissingPositive(nums));
    }

    public int firstMissingPositive(int[] nums) {
        if(nums.length==1) {
            return nums[0]==1 ? 2 : 1;
        }
        Arrays.sort(nums);
        boolean hasOne = false;
        for(int i=0; i<nums.length-1; i++) {
            if(nums[i]>0) {
                if (!hasOne) {
                    hasOne = nums[i]==1||nums[i+1]==1;
                }
                if (nums[i+1]-nums[i]>1) {
                    return hasOne ? nums[i] + 1 : 1;
                }
            }
            if (!hasOne) {
                hasOne = nums[i]==1||nums[i+1]==1;
            }
            if(i==nums.length-2) {
                return hasOne ? nums[i+1] + 1 : 1;
            }
        }
        return 1;
    }

    public int firstMissingPositiveXXX(int[] nums) {
        if(nums==null || nums.length==0) {
            return 1;
        }

        int bigNum = Math.max(nums[0]+1, 1);
        int smallNum = Math.max(nums[0]-1, 0);
        boolean hasOne = nums[0]==1;
        for(int i=0; i<nums.length; i++) {
            int temp = nums[i];
            if(temp>0) {
                bigNum = Math.max(bigNum, temp+1);
                if (temp==1) {
                    hasOne = true;
                    if(smallNum==1) {
                        smallNum=0;
                    }
                } else {
                    smallNum = Math.min(smallNum, temp-1);
                }
            }
        }
        if(!hasOne) {
            return 1;
        }
        if(smallNum!=0) {
            return smallNum;
        } else {
            return bigNum;
        }
    }
}
