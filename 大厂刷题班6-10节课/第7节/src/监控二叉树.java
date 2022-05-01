import java.util.Date;

public class 监控二叉树 {
    /*
    * 力扣链接:https://leetcode-cn.com/problems/binary-tree-cameras/
    * 给定你一颗二叉树，我们规定
    * 如果在x节点上放相机，它可以监控x节点本身，x的直接父节点，和x的直接子节点
    * 请返回监控这颗二叉树的所有节点所需要的最少相机数
    *
    * */
    public class TreeNode{
        public TreeNode left;
        public TreeNode right;
    }
    //二叉树的递归套路
    public class Info{
        public long noCover;//x节点没有被覆盖，但x以下的所有节点均被覆盖
        public long coverNoCamera;
        public long coverHasCamera;
        public Info(long a,long b,long c){
            noCover=a;
            coverNoCamera=b;
            coverHasCamera=c;
        }
    }
    public Info process1(TreeNode node){
        if(node==null){
            return new Info(Integer.MAX_VALUE,0,Integer.MAX_VALUE);
        }
        //1:向左右子树要信息
        Info leftInfo=process1(node.left);
        Info rightInfo=process1(node.right);
        //2:整合出自身的信息
        //x不覆盖，但其下方节点全要被覆盖所需的最少相机数
        long noCover=leftInfo.coverNoCamera+rightInfo.coverNoCamera;
        //x覆盖但不放相机，其下方节点全要被覆盖
        long coverNoCamera=Math.min(
                leftInfo.coverHasCamera+rightInfo.coverHasCamera,
                Math.min(leftInfo.coverHasCamera+rightInfo.coverNoCamera,
                        rightInfo.coverHasCamera+leftInfo.coverNoCamera)
        );
        //x覆盖且放相机
        long coverHasCamera=Math.min(Math.min(leftInfo.noCover,leftInfo.coverHasCamera),
                leftInfo.coverNoCamera)+
                Math.min(rightInfo.noCover,Math.min(rightInfo.coverHasCamera,rightInfo.coverNoCamera))
                +1;
        return new Info(noCover,coverNoCamera,coverHasCamera);
    }
    public int minCameraCover1(TreeNode root){
        if(root==null){
            return 0;
        }
        Info info=process1(root);
        return (int)Math.min(info.noCover+1,Math.min(info.coverHasCamera,info.coverNoCamera));
    }
    //贪心算法优化，其实以x为头的这棵树，只需要返回一种情况
    public enum Status{
        NO_COVER,COVER_NO_CAMERA,COVER_HAS_CAMERA
    }
    public static class Data{
        public Status status;
        public int cameras;
        public Data(Status a, int b){
            this.status=a;
            this.cameras=b;
        }
    }
    public static Data process2(TreeNode node){
        if(node==null){
            return new Data(Status.COVER_NO_CAMERA,0);
        }
        //1:向左右子树要信息
        Data leftData=process2(node.left);
        Data rightData=process2(node.right);
        //2:整合出自己的信息
        int cameras=leftData.cameras+rightData.cameras;
        //情况一:左子树或者右子树的头没有覆盖
        if(leftData.status==Status.NO_COVER || rightData.status==Status.NO_COVER){
            return new Data(Status.COVER_HAS_CAMERA,cameras+1);
        }
        //左子树和右子树头都覆盖了
        if(leftData.status==Status.COVER_HAS_CAMERA || rightData.status==Status.COVER_HAS_CAMERA){
            return new Data(Status.COVER_NO_CAMERA,cameras);
        }
        //贪心
        return new Data(Status.NO_COVER,cameras);
    }
    public int minCameraCover(TreeNode root){
        if(root==null){
            return 0;
        }
        Data data=process2(root);
        return data.cameras+(data.status==Status.NO_COVER?1:0);
    }
}
