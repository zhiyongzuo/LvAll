package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

/**
 * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 *
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 *
 * 必须原地修改，只允许使用额外常数空间。
 *
 * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-permutation
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author zzy
 * @date 2020/10/16
 */
public class _31 {

    @Test
    public void test() {
//        int[] nums = {1, 2, 3};//1,2,3 → 1,3,2
//        int[] nums = {3, 2, 1};//3,2,1 → 1,2,3
//        int[] nums = {1, 1, 5};//1,1,5 → 1,5,1
        int[] nums = {1, 3, 2};//打印{3, 1, 2}  需要{2，1，3}
//        int[] nums = {1,3,4,2};
        //{1，3，4，2}-》{1，4，3，2}-》{1，4，2，3}
//        int[] nums = {2,3,1};//{1，2，3}-》{3，1，2}
//        int[] nums = {5,4,7,5,3,2};//[5,5,4,7,3,2]->[5,5,2,3,4,7]
        nextPermutation(nums);
    }

    public void nextPermutation(int[] nums) {
        int numChangeTime=0;
        for(int i=nums.length-1; i>0; i--) {
            if(nums[i]>nums[i-1]) {
                int oldFirst = nums[i-1];
                nums[i-1] = nums[i];
                nums[i] = oldFirst;
                for(int k=i; k<nums.length; k++) {
                    if (nums[i-1]>nums[k] && nums[k]>oldFirst) {
                        int tempT = nums[i-1];
                        nums[i-1] = nums[k];
                        nums[k] = tempT;
                    }
                }
                numChangeTime++;
                //i后面的从小到大重新排序
                for(int j=i; j<nums.length; j++) {
                    for(int k=j+1; k<nums.length; k++) {
                        if(nums[j]>nums[k]) {
                            int tempSecond = nums[j];
                            nums[j] = nums[k];
                            nums[k] = tempSecond;
                        }
                    }
                }
                break;
            }
            if(i==1 && numChangeTime==0) {
                //不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）
                for(int j=0; j<nums.length/2; j++) {
                    int temp = nums[j];
                    nums[j] = nums[nums.length-j-1];
                    nums[nums.length-j-1] = temp;
                }
            }
        }
        System.out.println(GsonUtils.toJson(nums));
    }
}
