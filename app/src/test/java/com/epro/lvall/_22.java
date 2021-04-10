package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 22. 括号生成
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 *  
 *
 * 示例：
 *
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *      ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author zzy
 * @date 2020/10/12
 */
public class _22 {

    @Test
    public void test22() {
        System.out.println(GsonUtils.toJson(generateParenthesis(2)));
    }
//
//    public List<String> generateParenthesis(int n, int position, List<String> returnList) {
//        for(int i=0; i<2*n; i++) {
//            StringBuilder stringBuilder = new StringBuilder();
//            returnList.add(stringBuilder.toString());
//        }
//        if(position!=n) {
//            returnList.add("(");
//            generateParenthesis();
//        }
//    }
//
//    /**
//     * 生成全部组合
//     */
//    public List<String> generateParenthesis(int n) {
//        List<String> returnList = new ArrayList<>();
//
//        for(int i=0; i<2*n; i++) {
//            StringBuilder stringBuilder = new StringBuilder();
//            returnList.add(stringBuilder.toString());
//        }
//    }
//
    public List<String> generateParenthesis(int n) {
        List<String> combinations = new ArrayList<String>();
        generateAll(new char[2 * n], 0, combinations);
        return combinations;
    }

    public void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (valid(current)) {
                result.add(new String(current));
            }
        } else {
            current[pos] = '(';
            generateAll(current, pos + 1, result);
            current[pos] = ')';
            generateAll(current, pos + 1, result);
        }
    }

    public boolean valid(char[] current) {
        int balance = 0;
        for (char c: current) {
            if (c == '(') {
                ++balance;
            } else {
                --balance;
            }
            if (balance < 0) {
                return false;
            }
        }
        return balance == 0;
    }
}
