package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;

import org.junit.Test;

import java.util.Stack;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * <p>
 * 示例：
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * <p>
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 * <p>
 * 给定的 n 保证是有效的。
 * <p>
 * 进阶：
 * <p>
 * 你能尝试使用一趟扫描实现吗？
 *
 * @author zzy
 * @date 2020/10/9
 */
public class _19RemoveNthFromEnd {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    @Test
    public void test() {
        int i = 1;
        ListNode listNode = new ListNode(i++, new ListNode(i++, new ListNode(i++, new ListNode(i++, new ListNode(i++)))));
        System.out.println(GsonUtils.toJson(removeNthFromEnd(listNode, 2)));

//        ListNode listNode = new ListNode(i++);
//        System.out.println(GsonUtils.toJson(removeNthFromEnd(listNode, 1)));
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        int totalNodeNum = 0;

        ListNode tempListNode = head;
        while (tempListNode != null) {
            totalNodeNum++;
            tempListNode = tempListNode.next;
        }

        int tempOrderNum = 0;
        ListNode returnListNode = new ListNode(0);
        returnListNode.next = head;
        tempListNode = returnListNode;
        while (tempListNode != null) {
            tempOrderNum++;
            if (tempOrderNum==totalNodeNum-n+1 && tempListNode.next!=null) {
                tempListNode.next = tempListNode.next.next;
                break;
            }
            tempListNode= tempListNode.next;
        }
        return returnListNode.next;
    }

    @Test
    public void testNull() {
        ListNode listNodeA = new ListNode(8, new ListNode(9));
        ListNode listNodeB = listNodeA;

        //{"val":0,"next":{"val":9,"next":null}}
        //{"val":0,"next":{"val":9,"next":null}}
        //listNodeA.val = 0;

        //这里设置null，和下面不一样。。。是因为这个listNodeA.next是在堆里面？
        //{"val":8,"next":null}
        //{"val":8,"next":null}
//        listNodeA.next = null;

        //null
        //{"val":8,"next":{"val":9,"next":null}}
        listNodeA=null;

        System.out.println(GsonUtils.toJson(listNodeA));
        System.out.println(GsonUtils.toJson(listNodeB));
    }

    /**
     * 官网leetcode复制下来的
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnds(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int length  = 0;
        ListNode first = head;
        while (first != null) {
            length++;
            first = first.next;
        }
        length -= n;
        first = dummy;
        while (length > 0) {
            length--;
            first = first.next;
        }
        first.next = first.next.next;
        return dummy.next;
    }

    /**
     * 222222222200000000000
     * 20
     */
    @Test
    public void testIsValid() {
        System.out.println(isValid("({)"));
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack();
        for(int i=0; i<s.length(); i++) {
            char tempChar = s.charAt(i);
            if(!stack.empty() && isCompat(stack.peek(), tempChar)) {
                stack.pop();
            } else {
                stack.push(tempChar);
            }
        }
        if(stack.empty()) {
            return true;
        }
        return false;
    }

    public boolean isCompat(char c1, char c2) {
        if((c1=='(' && c2==')') || (c1=='{' && c2=='}') || (c1=='[' && c2==']')) {
            return true;
        }
        return false;
    }


    /**
     * 21. 合并两个有序链表
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     */

    @Test
    public void testMergeTwoLists() {
        ListNode listNode1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode listNode2 = new ListNode(1, new ListNode(3, new ListNode(4)));

//        listNode1.next = new ListNode(9);
//        System.out.println(GsonUtils.toJson(listNode1));//{"val":1,"next":{"val":9,"next":null}}

        System.out.println(GsonUtils.toJson(mergeTwoLists(listNode1, listNode2)));
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode returnListNode = new ListNode(0);
        if (l1==null) {
            returnListNode.next = l2;
        } else {
            returnListNode.next = l1;
        }

        while(l1!=null) {
            if(l2!=null) {
                if (l2.val<=l1.val) {
                    //ListNode tempListNode = l1; 这行代码换成下面就可以，原因呢
                    ListNode tempListNode = new ListNode(l1.val, l1.next);
                    l1.val = l2.val;
                    l1.next = tempListNode;
                    l2 = l2.next;
                } else {
                    l2.next = l1.next;
                    l1.next = l2;
                }
            }
            l1 = l1.next;
        }

        return returnListNode.next;
    }
}
