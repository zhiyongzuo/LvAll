package com.epro.lvall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1：
 *
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * 示例 2：
 *
 * 输入：candidates = [2,3,5], target = 8,
 * 所求解集为：
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *  
 *
 * 提示：
 *
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都是独一无二的。
 * 1 <= target <= 500
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author zzy
 * @date 2020/10/19
 */
public class _39 {

//    public List<List<Integer>> combinationSum(int[] candidates, int target) {
//        List<List<Integer>> returnList = new ArrayList<>();
//
//        List newCandidates = Arrays.asList(candidates);
//        Collections.sort(newCandidates);
//        int maxNum = target / (Integer) newCandidates.get(0);
//        for(int i=0; i<candidates.length; i++) {
//            List<Integer> insideList = new ArrayList<>();
//            for(int j=maxNum-1; j>=0; j--) {
//                for(int k=0; k<j; k++) {
//                    insideList.add(candidates[i]);
//                }
//            }
//        }
//
//    }

    @Test
    public void test() {
        int[] candidates = {2,3,5};
        combinationSum(candidates, 8);
    }


    List<List<Integer>> res=new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates==null)return res;
        dfs(target,0,new Stack<Integer>(),candidates);
        return res;
    }
    //深度遍历
    private void dfs(int target, int index, Stack<Integer> pre, int[] candidates) {
        //等于零说明结果符合要求
        if (target==0){
            res.add(new ArrayList<>(pre));
            return;
        }
        //遍历，index为本分支上一节点的减数的下标
        for (int i=index;i<candidates.length;i++){
            //如果减数大于目标值，则差为负数，不符合结果
            if (candidates[i]<=target){
                pre.push(candidates[i]);
                //目标值减去元素值
                dfs(target-candidates[i],i,pre, candidates);
                //每次回溯将最后一次加入的元素删除
                pre.pop();
            }
        }
    }
}
