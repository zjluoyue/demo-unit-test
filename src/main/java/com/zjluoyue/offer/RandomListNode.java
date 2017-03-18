package com.zjluoyue.offer;

/**
 * Created by Jia on 2017/3/8.
 */
public class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }

    @Override
    public String toString() {
        return "label=" + label +
                "(" + random +
                ")->" + next;
    }
}
