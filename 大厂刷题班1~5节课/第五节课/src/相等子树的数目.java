import javax.sound.sampled.DataLine.Info;
import java.util.HashMap;

public class 相等子树的数目 {
    /*
    * 题目:任意一颗以x为头的子树，如果它的左子树和右子树完全相同，我们就称以x
    * 为头的这颗子树是相等子树，现在给你一颗二叉树的根节点root，
    * 请你返回这颗二叉树上相等子树的数目
    *
    * */
    public static class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
    /*
    * 典型的二叉树的递归套路:
    * 以x为头的子树相等子树的数目有三种来源
    * 1)x的左子树
    * 2)x的右子树
    * 3)x为头的这颗树
    *
    * */
    public static int sumOfSameSubTree1(TreeNode root){
        return process1(root);
    }
    //1:暴力方法 时间复杂度为O(N*logN)
    public static int process1(TreeNode node){
        //base case
        if(node==null){
            return 0;
        }
        int sumOfSameSubTree= process1(node.left)+process1(node.right);
        if(isSameTree(node.left,node.right)){
            sumOfSameSubTree+=1;
        }
        return sumOfSameSubTree;
    }
    //2:写一个递归看看两颗子树是否相同
    public static boolean isSameTree(TreeNode root1,TreeNode root2){
        //base case
        //都为空的情况
        if(root1==null&&root2==null){
            return true;
        }
        //有一个为空
        if((root1==null)^(root2==null)){
            return false;
        }
        //均不为空
        return root1.val==root2.val&&isSameTree(root1.left,root2.left)&&
                isSameTree(root1.right,root2.right);
    }
    /*
    * 上面的方法之所以慢，是因为每一次验证以x为头的这颗子树是不是相等子树的时候
    * 都遍历了一次
    * 一次遍历之所以无法搞定，就是因为我们无法在一次遍历同时兼顾判断一颗二叉树的左右子树是否相等
    * 因次我们可以用预处理来加速
    * */
    /*
    * 利用后序序列化的方式，把一颗二叉树的左右子树的结构用字符串表示，
    * */
    public static int sumOfSameSubTree2(TreeNode root){
        if(root==null){
            return 0;
        }
        Hash hash=new Hash("SHA-256");
        return process(root,hash).ans;
    }
    /*二叉树的递归套路:左右子树收集相等子树的数目的同时，请把序列化结果字符串同时生成*/
    public static class Info{
        public int ans;
        public String str;
        public Info(int ans, String str) {
            this.ans = ans;
            this.str = str;
        }
    }
    public static Info process(TreeNode node,Hash hash){
        if(node==null){
            return new Info(0,hash.hashCode("-null-"));
        }
        //向左右子树要信息
        Info leftInfo=process(node.left,hash);
        Info rightInfo=process(node.right,hash);
        //整合出自己的信息
        StringBuilder sb=new StringBuilder();
        sb.append(leftInfo.str);
        sb.append(node.val);
        sb.append(rightInfo.str);
        int ans=leftInfo.ans+rightInfo.ans+((leftInfo.str.equals(rightInfo.str))?1:0);
        return new Info(ans,hash.hashCode(sb.toString()));
    }
    public static void main(String[] args) {
        TreeNode root=new TreeNode(6);
        root.left=new TreeNode(3);
        root.right=new TreeNode(3);
        root.left.left=new TreeNode(2);
        root.left.right=new TreeNode(1);
        root.right.left=new TreeNode(2);
        root.right.right=new TreeNode(1);
        System.out.println(sumOfSameSubTree2(root));
    }
}
