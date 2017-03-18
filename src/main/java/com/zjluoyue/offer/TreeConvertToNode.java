package com.zjluoyue.offer;

/**
 * Created by Jia on 2017/3/9.
 */
public class TreeConvertToNode {
    public TreeNode Convert(TreeNode pRootOfTree) {
        TreeNode pHeadTreeNode = convertNode(pRootOfTree);
        while (pHeadTreeNode != null && pHeadTreeNode.left != null) {
            pHeadTreeNode = pHeadTreeNode.left;
        }
        return pHeadTreeNode;
    }

    public TreeNode convertNode(TreeNode pRootOfTree) {
        if (pRootOfTree == null)
            return pRootOfTree;
        if (pRootOfTree.left != null) {
            TreeNode leftRoot = convertNode(pRootOfTree.left);
            while (leftRoot.right != null)
                leftRoot = leftRoot.right;
            leftRoot.right = pRootOfTree;
            pRootOfTree.left = leftRoot;
        }
        if (pRootOfTree.right != null) {
            TreeNode rightRoot = convertNode(pRootOfTree.right);
            while (rightRoot.left != null)
                rightRoot = rightRoot.left;
            rightRoot.left = pRootOfTree;
            pRootOfTree.right = rightRoot;
        }
        return pRootOfTree;
    }
    TreeNode head = null;
    TreeNode realHead = null;
    public TreeNode Convert1(TreeNode pRootOfTree) {
        ConvertSub(pRootOfTree);
        return realHead;
    }

    private void ConvertSub(TreeNode pRootOfTree) {
        if(pRootOfTree==null) return;
        ConvertSub(pRootOfTree.left);
        if (head == null) {
            head = realHead = pRootOfTree;
        } else {
            head.right = pRootOfTree;
            pRootOfTree.left = head;
            head = pRootOfTree;
        }
        ConvertSub(pRootOfTree.right);
    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(6);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(8);
        root.right = new TreeNode(14);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(16);
        System.out.println(root);
        TreeNode pRoot = new TreeConvertToNode().convertNode(root);
        while (pRoot != null) {
            System.out.println(pRoot.val);
            pRoot = pRoot.right;

        }
    }
}
