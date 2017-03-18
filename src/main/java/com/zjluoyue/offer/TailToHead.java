package com.zjluoyue.offer;
import java.util.ArrayList;
import java.util.Stack;

public class TailToHead {

    ArrayList<Integer> arrays = new ArrayList<>();

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        Stack<Integer> nodes = new Stack<Integer>();

        while (listNode != null) {
            nodes.push(listNode.val);
            listNode = listNode.next;
        }

        while (!nodes.empty()) {
            arrayList.add(nodes.pop());
        }
        return arrayList;
    }

    public ArrayList<Integer> printListRecuFromTailToHead(ListNode listNode) {
        if (listNode != null) {
            printListRecuFromTailToHead(listNode.next);
            arrays.add(listNode.val);
        }
        return arrays;
    }
}