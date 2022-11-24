package 线段树;

import sun.dc.pr.PRError;

import java.util.Arrays;
import java.util.concurrent.FutureTask;

//统计二叉树的节点个数
public class Test {
    public static class Node{
        public int val;
        public Node left;
        public Node right;
        public Node(int value){
            this.val=value;
        }
    }

    public static int process1(Node root){
        if(root==null){
            return 0;
        }
        int count=1;
        count+=process1(root.left);
        count+=process1(root.right);
        return count;
    }


    //统计二叉树的高度
    public static int process2(Node root){
        if(root==null){
            return 0;
        }
        int leftHeight=process2(root.left);
        int rightHeight=process2(root.right);
        return Math.max(leftHeight,rightHeight)+1;
    }
}
