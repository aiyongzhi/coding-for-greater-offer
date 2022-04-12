import com.sun.xml.internal.ws.addressing.WsaActionUtil;

import java.util.*;

public class 在一颗二叉树上返回与target节点距离为k的所有节点 {
    /*
    * 三个参数,二叉树的头节点head,给定二叉树上某一个节点target,
    * 一个正数k,可以向上或者向下走，返回所有与target距离为k的节点
    *
    * */
    public static class TreeNode{
        public int value;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int value) {
            this.value = value;
        }
    }
    public static List<TreeNode> findAllTargetNode(TreeNode root,TreeNode target,int k){
        if(root==null||target==null||k<0){
            return null;
        }
        List<TreeNode> res=new ArrayList<>();
        if(k==0){
            res.add(target);
            return res;
        }
        //一:生成每个节点的父节点fatherMap 用层序遍历的方式生成
        HashMap<TreeNode,TreeNode> fatherMap=new HashMap<>();
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);

        fatherMap.put(root,null);
        while(!queue.isEmpty()){
            TreeNode cur=queue.poll();
            if(cur.left!=null){
                fatherMap.put(cur.left, cur);
                queue.add(cur.left);
            }
            if(cur.right!=null){
                fatherMap.put(cur.right,cur);
                queue.add(cur.right);
            }
        }

        //二:每个节点都有指向父节点的指针，整个二叉树就变成了一个图
        //此时我们以target节点向四周进行宽度优先遍历，第k圈的所有节点就是解
        //但是一定要去重
        HashSet<TreeNode> set=new HashSet<>();
        queue.add(target);
        set.add(target);
        for(int i=0;i<k;i++){
            int time=queue.size();
            for(int j=0;j<time;j++){
                TreeNode cur=queue.poll();
                if(cur.left!=null&&!set.contains(cur.left)){
                    queue.add(cur.left);
                    set.add(cur.left);
                }
                if(cur.right!=null&&!set.contains(cur.right)){
                    queue.add(cur.right);
                    set.add(cur.right);
                }
                if(fatherMap.get(cur)!=null&&!set.contains(fatherMap.get(cur))){
                    queue.add(fatherMap.get(cur));
                    set.add(fatherMap.get(cur));
                }
            }
        }
        while(!queue.isEmpty()){
            res.add(queue.poll());
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode root=new TreeNode(1);
        root.left=new TreeNode(2);
        root.right=new TreeNode(3);
        root.left.left=new TreeNode(4);
        root.left.right=new TreeNode(5);
        root.left.right.left=new TreeNode(7);
        root.left.right.right=new TreeNode(8);
        root.right.right=new TreeNode(6);
        List<TreeNode> res=findAllTargetNode(root,root.left,2);
        for (TreeNode re : res) {
            System.out.print(re.value+" ");
        }
    }
}
