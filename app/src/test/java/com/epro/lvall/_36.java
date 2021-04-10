package com.epro.lvall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 36. 有效的数独
 * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
 *
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 *
 *
 * 上图是一个部分填充的有效的数独。
 *
 * 数独部分空格内已填入了数字，空白格用 '.' 表示。
 * @author zzy
 * @date 2020/10/18
 */
public class _36 {

    @Test
    public void test() {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        System.out.println(isValidSudoku(board));
    }

    public boolean isValidSudoku(char[][] board) {
        List testList = new ArrayList<>();

        //验证每一列
        for(int i=0; i<board.length; i++) {
            testList.clear();
            for(int j=0; j<board[i].length; j++) {
                if(!(board[j][i]=='.') && testList.contains(board[j][i])) {
                    return false;
                } else {
                    testList.add(board[j][i]);
                }
            }
        }

        //验证每一行
        for(int i=0; i<board.length; i++) {
            testList.clear();
            for(int j=0; j<board[i].length; j++) {
                if(!(board[i][j]=='.')  && testList.contains(board[i][j])) {
                    return false;
                } else {
                    testList.add(board[i][j]);
                }
            }
        }

        //数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
        int gapNum=3;
        for(int i=0; i<board.length/gapNum; i++) {
            for(int j=0; j<board[i].length/gapNum; j++) {
                testList.clear();
                for(int m=i*3; m<i*3+3; m++) {
                    for(int n=j*3; n<j*3+3; n++) {
                        if(!(board[m][n]=='.')  && testList.contains(board[m][n])) {
                            return false;
                        } else {
                            testList.add(board[m][n]);
                        }
                    }
                }
            }
        }
        return true;
    }
}
