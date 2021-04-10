package com.epro.lvall;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author zzy
 * @date 2020/9/29
 */
public class _16 {
    @Test
    public void test() {
//        int[] nums = {1,2,4,8,16,32,64,128};//2+16+64  i=1, j=4, j=6
//        System.out.println(threeSumClosest(nums, 82));
        int[] nums = {1,2,5,10,11};
        System.out.println(threeSumClosest(nums, 12));
    }

    public int threeSumClosest(int[] nums, int target) {
        if(nums.length<3) {
            return 0;
        }
        int sumFirstThree = nums[0] + nums[1] + nums[2];
        int minGap = sumFirstThree - target;
        int returnInt = sumFirstThree;
        if(minGap<0) {
            minGap = -minGap;
        }
        Arrays.sort(nums);
        for(int i=0; i<nums.length; i++) {
            if(i>0 && nums[i]==nums[i-1]) {
                continue;
            }
            for(int j=i+1; j<nums.length; j++) {
                if(j>i+1 && nums[j]==nums[j-1]) {
                    continue;
                }

                int k = nums.length - 1;
                for(; k>j; k--) {
                    int x = nums[i] + nums[j] + nums[k];
                    int tempGap = x - target;
                    if(tempGap<0) {
                        tempGap = -tempGap;
                    }
                    if(tempGap<minGap) {
                        minGap = tempGap;
                        returnInt = x;
                    }
                }
            }
        }
        return returnInt;
    }
}
