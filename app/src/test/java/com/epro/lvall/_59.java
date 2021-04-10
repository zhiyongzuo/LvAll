package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

/**
 * 59. 螺旋矩阵 II
 * 给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
 *
 * 示例:
 *
 * 输入: 3
 * 输出:
 * [
 *  [ 1, 2, 3 ],
 *  [ 8, 9, 4 ],
 *  [ 7, 6, 5 ]
 * ]
 *
 * [[1,2,3,4]
 * [12,13,14,5]
 * [11,16,15,6]
 * [10,9,8,7]]
 * @author zzy
 * @date 2020/11/4
 */
public class _59 {

    @Test
    public void test() {
        System.out.println(GsonUtils.toJson(generateMatrix(4)));
    }

    public int[][] generateMatrix(int n) {
        if(n==0) {
            return new int[0][0];
        }
        if(Math.pow(n, 2)>Integer.MAX_VALUE) {
            return new int[0][0];
        }

        int maxNum = (int) Math.pow(n, 2);
        int index=1;
        int top=0;
        int right = n-1;
        int bottom = n-1;
        int left = 0;
        int[][] returnInt = new int[n][n];

        while (index<=maxNum) {
            for(int i=left; i<=right && index<=maxNum; i++) {
                returnInt[top][i] = index++;
            }
            top++;

            for(int i=top; i<=bottom && index<=maxNum; i++) {
                returnInt[i][right] = index++;
            }
            right--;

            for(int i=right; i>=left && index<=maxNum; i--) {
                returnInt[bottom][i] = index++;
            }
            bottom--;

            for(int i=bottom; i>=top && index<=maxNum; i--) {
                returnInt[i][left] = index++;
            }
            left++;
        }
        return returnInt;
    }
}
