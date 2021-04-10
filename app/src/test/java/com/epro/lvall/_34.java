package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 你的算法时间复杂度必须是 O(log n) 级别。
 *
 * 如果数组中不存在目标值，返回 [-1, -1]。
 *
 * 示例 1:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: [3,4]
 * 示例 2:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: [-1,-1]
 * @date 2020/10/17
 */
public class _34 {

    @Test
    public void test() {
//        int[] nums = {5,7,7,8,8,10};
//        System.out.println(GsonUtils.toJson(searchRange(nums, 8)));

//        int[] nums = {1,2,2};
//        System.out.println(GsonUtils.toJson(searchRange(nums, 2)));

//        int[] nums = {5,7,7,8,8,10};
//        System.out.println(GsonUtils.toJson(searchRange(nums, 6)));
//
        int[] nums = {1,2,3,3,3,3,4,5,9};
        System.out.println(GsonUtils.toJson(searchRange(nums, 3)));
    }


    public int[] searchRange(int[] nums, int target) {
        int[] returnInt = {-1, -1};
        searchHalf(nums, target,0, nums.length-1, returnInt);
        return returnInt;
    }

    private void searchHalf(int[] nums, int target, int start, int end, int[] returnInt) {
        if(end-start==0) {
            if (nums[start]==target) {
                returnInt[0] = returnInt[0]==-1 ? start : Math.min(returnInt[0], start);
                returnInt[1] = Math.max(returnInt[1], start);
            }
            return;
        }
        if(end-start==1) {
            if (nums[start]==target) {
                returnInt[0] = returnInt[0]==-1 ? start : Math.min(returnInt[0], start);
                returnInt[1] = Math.max(returnInt[1], start);
            }
            if (nums[end]==target) {
                returnInt[0] = returnInt[0]==-1 ? end : Math.min(returnInt[0], end);
                returnInt[1] = Math.max(returnInt[1], end);
            }
            return;
        }
        int mid = (end-start)/2+start;
        if(mid>start && mid<end) {
            if(nums[mid]>target) {
                searchHalf(nums, target, start, mid, returnInt);
            } else if(nums[mid]<target) {
                searchHalf(nums, target, mid, end, returnInt);
            } else {
                //刚好相等
                returnInt[0] = returnInt[0]==-1 ? mid : Math.min(returnInt[0], mid);
                returnInt[1] = Math.max(returnInt[1], mid);
                for(int i=mid-1; i>=0; i--) {
                    if(nums[i]==target) {
                        returnInt[0] = i;
                    } else {
                        break;
                    }
                }
                for(int i=mid+1; i<=end; i++) {
                    if(nums[i]==target) {
                        returnInt[1] = i;
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
