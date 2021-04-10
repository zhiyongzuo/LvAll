package com.epro.lvall;

import org.junit.Test;

/**
 * 64. 最小路径和
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例:
 *
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 * @author zzy
 * @date 2020/11/10
 */
public class _64 {

    @Test
    public void test() {
        int[][] grid = new int[][]{{1,3,1},{1,5,1},{4,2,1}};

        System.out.println(minPathSum(grid));
    }

    public int minPathSum(int[][] grid) {
        int maxStep = grid.length;
        int rightStepIndex = 0;
        int downStepIndex = 0;
        int returnInt = 0;
        TESTCLASS testclass = new TESTCLASS();

        invokeSelf(rightStepIndex, downStepIndex, returnInt, maxStep, grid, testclass);
        return testclass.value;
    }


    public void invokeSelf(int rightStepIndex, int downStepIndex, int returnInt, int maxStep, int[][] grid, TESTCLASS testclass) {
        if(rightStepIndex==maxStep-1 && downStepIndex==maxStep-1) {
            testclass.value = Math.min(testclass.value, returnInt);
        } else {
            returnInt = returnInt+grid[rightStepIndex][downStepIndex];
            if(rightStepIndex<maxStep-1) {
                invokeSelf(++rightStepIndex, downStepIndex, returnInt, maxStep, grid, testclass);
            }
            if(downStepIndex<maxStep-1) {
                invokeSelf(rightStepIndex, ++downStepIndex, returnInt, maxStep, grid, testclass);
            }
        }
    }

    class TESTCLASS {
        int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
