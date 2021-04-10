package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 *
 * 示例:
 *
 * 输入: [1,1,2]
 * 输出:
 * [
 *   [1,1,2],
 *   [1,2,1],
 *   [2,1,1]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author zzy
 * @date 2020/10/22
 */
public class _47 {
    private HashSet s;


    @Test
    public void test() {
        int[] nums = {1,1,2};
//        int[] nums = {1,2,3};
        s = new HashSet();
        System.out.println(GsonUtils.toJson(new Solution().permuteUnique(nums)));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> returnList = new ArrayList<>();
        getSingle(nums,0, new Stack<Integer>(), new Stack<Integer>(), returnList);
        return returnList;
    }

    public void getSingle(int[] nums, int startIndex, Stack<Integer> indexStack, Stack<Integer> integerStack, List<List<Integer>> returnList) {
        if(integerStack.size()==nums.length) {
            if (!s.contains(integerStack)) {
                returnList.add(new ArrayList<>(integerStack));
                s.add(new ArrayList<>(integerStack));
            }
            return;
        }
        for(int i=startIndex; i<nums.length; i++) {
//            if (!indexStack.contains(i)) {
                indexStack.push(i);
                integerStack.push(nums[i]);
                getSingle(nums, i, indexStack, integerStack, returnList);
                integerStack.pop();
//            }
        }
    }

    class Solution {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        public List<List<Integer>> permuteUnique(int[] nums) {
            int n = nums.length;
            boolean[] used = new boolean[n];
            Arrays.sort(nums); // 排序, 以保证相等的元素相邻
            backtrack(nums, used, 0, n);
            return res;
        }

        public void backtrack(int[] nums, boolean[] used, int cur, int n) {
            if (cur == n) {
                res.add(new ArrayList<>(temp));
                return;
            }
            for (int i = 0; i < n; i++) {
                // 判重, 若使用过该元素则跳过
                if (used[i])
                    continue;
                // 剪枝, i>0 是为了让 nums[i-1] 不越界
                // 正常不剪枝的回溯: 对于每一层回溯搜索, 会判断其它未被使用的所有元素(会有重复的元素), 都被填充到该位置一次;
                // 剪枝的意思是: 保证相邻的重复元素在每一层的回溯搜索中, 只被回溯搜索填充一个, 其余的不再会填充, 且遵循靠左的第一个未被填充的元素被填充,
                // 若没有这个剪枝的过程, 那么这些重复的相邻元素, 会被回溯搜索填充cnt(相邻重复元素)次;
                // eg: 对于重复的四个元素 [0, 0, 0, 0], (0 表示未填充) 第一层回溯填充第一个0, 第二层回溯第一个0因已被used, 即continue, 第二个0不会被continue, 执行回溯
                // [0, 0, 0, 0] -> [1, 0, 0, 0] -> [1, 1, 0, 0] -> [1, 1, 1, 0] -> [1, 1, 1, 1] (1 表示填充)

                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
                    continue;
                temp.add(nums[i]);
                used[i] = true;
                // 进入下一层
                backtrack(nums, used, cur + 1, n);
                // 回复原来状态
                temp.remove(temp.size() - 1);
                used[i] = false;
            }
        }
    }
}
