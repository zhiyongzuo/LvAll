package com.epro.lvall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zzy
 * @date 2020/10/9
 */
public class _18 {
    /**
     *
     * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
     *
     * 注意：
     *
     * 答案中不可以包含重复的四元组。
     *
     * 示例：
     *
     * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
     *
     * 满足要求的四元组集合为：
     * [
     *   [-1,  0, 0, 1],
     *   [-2, -1, 1, 2],
     *   [-2,  0, 0, 2]
     * ]
     *
     */

    @Test
    public void test() {
        int[] nums = {1, 0, -1, 0, -2, 2};
//        LogUtils.d(fourSum(nums, 0));
        System.out.println(fourSum(nums, 0));
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> returnString = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0; i<nums.length; i++) {
            if(i>0 && nums[i]==nums[i-1]) {
                continue;
            }
            for(int j=i+1; j<nums.length; j++) {
                if(j>i+1 && nums[j]==nums[j-1]) {
                    continue;
                }
                for(int k=j+1; k<nums.length; k++) {
                    if(k>j+1 && nums[k]==nums[k-1]) {
                        continue;
                    }
                    for(int l=k+1; l<nums.length; l++) {
                        if(l>k+1 && nums[l]==nums[l-1]) {
                            continue;
                        }
                        int tempResult = nums[i] + nums[j] + nums[k] + nums[l];
                        if(tempResult==target) {
                            List<Integer> tempList = new ArrayList<>();
                            tempList.add(nums[i]);
                            tempList.add(nums[j]);
                            tempList.add(nums[k]);
                            tempList.add(nums[l]);
                            returnString.add(tempList);
                        }
                    }
                }
            }
        }

        return returnString;
    }
}
