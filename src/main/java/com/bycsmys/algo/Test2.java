package com.bycsmys.algo;

import com.bycsmys.algo.common.ListNode;

public class Test2 {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        l1.next.next = new ListNode(9);
        l1.next.next.next = new ListNode(9);
        l1.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next = new ListNode(9);
        l1.next.next.next.next.next.next = new ListNode(9);


        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(9);
        l2.next.next.next = new ListNode(9);

        ListNode listNode = addTwoNumbers(l1, l2);

        System.out.println(listNode);
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        int carry = 0;
        ListNode res = new ListNode();
        ListNode head = res;

        while (l1 != null || l2 != null || carry !=0) {
            int val1 = (l1 == null) ? 0 : l1.val;
            int val2 = (l2 == null) ? 0 : l2.val;

            int bitRes = val1 + val2 + carry;

            carry = bitRes / 10;

            int bit = bitRes % 10;

            res.next = new ListNode(bit);
            res = res.next;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

        }

        return head.next;


    }

}
