package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 54. 螺旋矩阵
 * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
 *
 * 示例 1:
 *
 * 输入:
 * [
 *  [ 1, 2, 3 ],
 *  [ 4, 5, 6 ],
 *  [ 7, 8, 9 ]
 * ]
 * 输出: [1,2,3,6,9,8,7,4,5]
 * 示例 2:
 *
 * 输入:
 * [
 *   [1, 2, 3, 4],
 *   [5, 6, 7, 8],
 *   [9,10,11,12]
 * ]
 * 输出: [1,2,3,4,8,12,11,10,9,5,6,7]
 * @author zzy
 * @date 2020/10/27
 */
public class _54 {

    @Test
    public void test() {
        //[0,0],[0,1],[0,2]   ,[1,2],[2,2],[2,1],[2,0],  [1,0],[1,1]
        int[][] matrix = {{1, 2, 3 }, {4, 5, 6 }, {7, 8, 9}};
        System.out.println(GsonUtils.toJson(spiralOrder(matrix)));
    }


    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> returnList = new ArrayList<>();
        int rowNum = matrix.length;
        int columnNum = matrix[0].length;
        long totalNum = rowNum * columnNum;
        int top = 0;
        int right = columnNum-1;
        int bottom = rowNum-1;
        int left = 0;
        int index = 0;
        while (index<totalNum) {
            //top
            for(int j=left; j<=right && index<totalNum; j++) {
                returnList.add(matrix[top][j]);
                index++;
            }
            top++;
            //right
            for(int i=top; i<=bottom && index<totalNum; i++) {
                returnList.add(matrix[i][right]);
                index++;
            }
            right--;
            //bottom
            for(int i=right; i>=left && index<totalNum; i--) {
                returnList.add(matrix[bottom][i]);
                index++;
            }
            bottom--;
            //left
            for(int i=bottom; i>=top && index<totalNum; i--) {
                returnList.add(matrix[i][left]);
                index++;
            }
            left++;
        }
        return returnList;


        //Solution 1 from leetcode
//        List<Integer> order = new ArrayList<Integer>();
//        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
//            return order;
//        }
//        int rows = matrix.length, columns = matrix[0].length;
//        boolean[][] visited = new boolean[rows][columns];
//        int total = rows * columns;
//        int row = 0, column = 0;
//        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
//        int directionIndex = 0;
//        for (int i = 0; i < total; i++) {
//            order.add(matrix[row][column]);
//            visited[row][column] = true;
//            int nextRow = row + directions[directionIndex][0], nextColumn = column + directions[directionIndex][1];
//            if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns || visited[nextRow][nextColumn]) {
//                directionIndex = (directionIndex + 1) % 4;
//            }
//            row += directions[directionIndex][0];
//            column += directions[directionIndex][1];
//        }
//        return order;



        //solution 2 from leetcode
//        LinkedList<Integer> result = new LinkedList<>();
//        if(matrix==null||matrix.length==0) return result;
//        int left = 0;
//        int right = matrix[0].length - 1;
//        int top = 0;
//        int bottom = matrix.length - 1;
//        int numEle = matrix.length * matrix[0].length;
//        while (numEle >= 1) {
//            for (int i = left; i <= right && numEle >= 1; i++) {
//                result.add(matrix[top][i]);
//                numEle--;
//            }
//            top++;
//            for (int i = top; i <= bottom && numEle >= 1; i++) {
//                result.add(matrix[i][right]);
//                numEle--;
//            }
//            right--;
//            for (int i = right; i >= left && numEle >= 1; i--) {
//                result.add(matrix[bottom][i]);
//                numEle--;
//            }
//            bottom--;
//            for (int i = bottom; i >= top && numEle >= 1; i--) {
//                result.add(matrix[i][left]);
//                numEle--;
//            }
//            left++;
//        }
//        return result;
    }

}
