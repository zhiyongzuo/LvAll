package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 48. 旋转图像
 * 给定一个 n × n 的二维矩阵表示一个图像。
 *
 * 将图像顺时针旋转 90 度。
 *
 * 说明：
 *
 * 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
 *
 * 示例 1:
 *
 * 给定 matrix =
 * [
 *   [1,2,3],
 *   [4,5,6],
 *   [7,8,9]
 * ],
 *
 * 原地旋转输入矩阵，使其变为:
 * [
 *   [7,4,1],
 *   [8,5,2],
 *   [9,6,3]
 * ]
 *
 * 示例 2:
 *
 * 给定 matrix =
 * [
 *   [ 5, 1, 9,11],
 *   [ 2, 4, 8,10],
 *   [13, 3, 6, 7],
 *   [15,14,12,16]
 * ],
 *
 * 原地旋转输入矩阵，使其变为:
 * [
 *   [15,13, 2, 5],
 *   [14, 3, 4, 1],
 *   [12, 6, 8, 9],
 *   [16, 7,10,11]
 * ]
 *
 * @author zzy
 * @date 2020/10/25
 */
public class _48 {

    @Test
    public void test() {
        int[][] matrix = {{1, 2, 3},
                          {4, 5, 6},
                          {7, 8, 9}};
        rotate(matrix);
    }

    public void rotate(int[][] matrix) {
        List<List<Integer>> tempList = new ArrayList<>();
        List<Integer> innerList;

        for(int i=0; i<matrix.length; i++) {
            innerList = new ArrayList<>();
            for(int j=0; j<matrix[i].length; j++) {
                innerList.add(Integer.valueOf(matrix[i][j]));
            }
            tempList.add(innerList);
        }

        for(int i=matrix.length-1; i>=0; i--) {
            for(int j=0; j<=matrix[i].length-1; j++) {
                matrix[j][i] = tempList.get(matrix.length-i-1).get(j);//0,0  1,0  2,0
            }
        }
        System.out.println(GsonUtils.toJson(matrix));
    }
}
