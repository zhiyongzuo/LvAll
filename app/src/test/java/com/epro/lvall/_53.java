package com.epro.lvall;

import org.junit.Test;

/**
 * 53. 最大子序和
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 进阶:
 *
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 * @author zzy
 * @date 2020/10/27
 */
public class _53 {

    @Test
    public void test() {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
//        System.out.println(maxSubArray(nums));
        System.out.println(new Solution().maxSubArray(nums));
    }

    public int maxSubArray(int[] nums) {
        if(nums.length==0) {
            return 0;
        }
        int maxSum = nums[0];
        for(int i=0; i<nums.length; i++) {
            int tempSum = 0;
            for(int j=i; j<nums.length; j++) {
//                System.out.println("tempSum + nums[j]=" + tempSum + nums[j] + "vvvvv" + (tempSum + nums[j]));
                tempSum += nums[j];
                maxSum = Math.max(tempSum, maxSum);
            }
        }
        return maxSum;
    }

    class Solution {
        public int maxSubArray(int[] nums) {
            //-2,1,-3,4,-1,2,1,-5,4
            int ans = nums[0];
            int sum = 0;
            for(int num: nums) {
                if(sum > 0) {
                    sum += num;
                } else {
                    sum = num;
                }
                ans = Math.max(ans, sum);
            }
            return ans;
        }
    }

//    作者：guanpengchn
//    链接：https://leetcode-cn.com/problems/maximum-subarray/solution/hua-jie-suan-fa-53-zui-da-zi-xu-he-by-guanpengchn/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

}
