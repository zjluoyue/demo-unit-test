package com.zjluoyue.offer;

import java.util.HashMap;

/**
 * Created by Jia on 2017/3/8.
 */


public class CloneRandomListNode {
    // O(n) 空间
    public static RandomListNode Clone(RandomListNode pHead) {
        HashMap<RandomListNode, RandomListNode>
                temp = new HashMap<>();
        if (pHead == null)
            return null;
        RandomListNode copyHead = new RandomListNode(pHead.label);
        // 指针节点
        RandomListNode p = copyHead;
        RandomListNode h = pHead;
        while (h.next != null) {
            if (h.random != null) {
                temp.put(h, h.random);
            }
            p.next = new RandomListNode(h.next.label);
            p = p.next;
            h = h.next;
        }
        // GC 回收指针
        h = null;
        p = copyHead;
        while (pHead != null) {
            p.random = temp.get(pHead);
            p = p.next;
            pHead = pHead.next;
        }
        return copyHead;
    }

    public static RandomListNode clone(RandomListNode pHead) {
        if (pHead == null)
            return null;

        RandomListNode pNode = pHead;
        // 复制链表
        while (pNode != null) {
            RandomListNode temp = new RandomListNode(pNode.label);
            temp.next = pNode.next;
            pNode.next = temp;
            pNode = temp.next;
        }
        // 复制随机节点
        pNode = pHead;
        while (pNode != null) {
            RandomListNode temp = pNode.next;
            if (pNode.random != null)
                temp.random = pNode.random.next;
            pNode = temp.next;
        }

        // 拆分链表
        pNode = pHead;
        RandomListNode pCloneHead = null;
        RandomListNode pClone = pCloneHead = pNode.next;
        pNode.next = pClone.next;
        pNode = pNode.next;
        while (pNode != null) {
            pClone.next = pNode.next;
            pClone = pClone.next;
            pNode.next = pClone.next;
            pNode = pNode.next;
        }

        return pCloneHead;
    }
    public static void main(String[] args) {
        RandomListNode head = new RandomListNode(1);
        head.next = new RandomListNode(2);
        head.next.next = new RandomListNode(3);
        head.next.next.next = new RandomListNode(4);
        head.random = head.next.next.next;
        System.out.println(head);
        System.out.println(clone(head));
    }
}
