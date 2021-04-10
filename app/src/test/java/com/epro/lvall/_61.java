package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

/**
 * 61. 旋转链表
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 *
 * 示例 1:
 *
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 * 示例 2:
 *
 * 输入: 0->1->2->NULL, k = 4
 * 输出: 2->0->1->NULL
 * 解释:
 * 向右旋转 1 步: 2->0->1->NULL
 * 向右旋转 2 步: 1->2->0->NULL
 * 向右旋转 3 步: 0->1->2->NULL
 * 向右旋转 4 步: 2->0->1->NULL
 * @author zzy
 * @date 2020/11/7
 */
public class _61 {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }


    @Test
    public void test() {
//        ListNode head = new ListNode(1);
//        assembleListNode(head, 2, 5);

//        ListNode head = new ListNode(0);
//        assembleListNode(head, 1, 2);

        ListNode head = new ListNode(1);

        System.out.println(GsonUtils.toJson(head));
        rotateRight(head, 1);
    }

    private void assembleListNode(ListNode head, int val, int maxNum) {
        if(val==maxNum+1) {
            return ;
        }
        head.next = new ListNode(val);
        assembleListNode(head.next, val+1, maxNum);
    }

    private void getSpecificListNode(ListNode head, ListNode returnListNode, int startIndexNum, int endIndexNum) {
        if(startIndexNum==endIndexNum) {
            return ;
        }
        returnListNode.next = new ListNode(head.val);
        getSpecificListNode(head.next, returnListNode.next, ++startIndexNum, endIndexNum);
    }

    private void getSpecificAllListNode(ListNode head, ListNode returnListNode, ListNode rightListNode,  int startIndexNum, int endIndexNum) {
        if(startIndexNum==endIndexNum) {
            returnListNode.next = rightListNode;
            return ;
        }
        returnListNode.next = new ListNode(head.val);
        getSpecificAllListNode(head.next, returnListNode.next, rightListNode, ++startIndexNum, endIndexNum);
    }



    public ListNode rotateRight(ListNode head, int k) {
        ListNode tempListNode = head;
        int totalNum = 0;
        while (tempListNode!=null) {
            tempListNode = tempListNode.next;
            totalNum++;
        }

        int moveNum = k%totalNum;
        if(moveNum==0) {
            return head;
        }
        ListNode leftListNode = null;
        ListNode rightListNode = null;

        for(int i=1; i<=totalNum; i++) {
            if (i==1) {
                rightListNode = new ListNode(head.val);
                getSpecificListNode(head.next, rightListNode, i, totalNum-moveNum);
            }
            if(i==totalNum-moveNum+1) {
                leftListNode = new ListNode(head.val);
                getSpecificAllListNode(head.next, leftListNode, rightListNode, i, totalNum);
            }
            head = head.next;
        }

        System.out.println("leftListNode " + GsonUtils.toJson(leftListNode));
        System.out.println("rightListNode " + GsonUtils.toJson(rightListNode));
        return leftListNode;
    }
}
