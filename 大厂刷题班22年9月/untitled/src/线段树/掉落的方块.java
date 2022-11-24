package 线段树;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//    在二维平面上的 x 轴上，放置着一些方块。
//    给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi] 表示：第 i 个方块边长为 sideLengthi ，其左侧边与 x 轴上坐标点 lefti 对齐。
//    每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，直到着陆到 另一个正方形的顶边 或者是 x 轴上 。一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。一旦着陆，它就会固定在原地，无法移动。
//    在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
//    返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度。
public class 掉落的方块 {
    //一定要注意：单点更新不涉及懒更新的下发，所以可以不需要update懒更新数组
    //而范围更新一定要懒更新数组
    //由于空间溢出,采用动态开点线段树
    public static class Node{
        public int max;
        public int update;
        public boolean change;

        public Node left;
        public Node right;
    }
    public static class DynamicSegmentTree{
        public Node root;
        public int N;
        public DynamicSegmentTree(int n){
            root=new Node();
            N=n;
        }

        private void pushUp(Node rt){
            rt.max=Math.max(rt.left.max,rt.right.max);
        }
        private void pushDown(Node rt){
            if(rt.left==null){
                rt.left=new Node();
            }
            if(rt.right==null){
                rt.right=new Node();
            }
            if(rt.change){
                rt.left.change=true;
                rt.left.update=rt.update;
                rt.right.change=true;
                rt.right.update=rt.update;
                rt.left.max=rt.update;
                rt.right.max=rt.update;
                rt.change=false;
            }
        }

        private void update(int L,int R,int M,Node rt,int l,int r){
            if(L<=l&&r<=R){
                rt.change=true;
                rt.update=M;
                rt.max=M;
                return;
            }
            int mid=(l+(r-l)/2);
            pushDown(rt);
            if(L<=mid){
                update(L,R,M,rt.left,l,mid);
            }
            if(R>mid){
                update(L,R,M,rt.right,mid+1,r);
            }
            pushUp(rt);
        }

        private int max(int L,int R,Node rt,int l,int r){
            if(L<=l&&r<=R){
                return rt.max;
            }
            int res=0;
            pushDown(rt);
            int mid=(l+(r-l)/2);
            if(L<=mid){
                res=Math.max(res,max(L,R,rt.left,l,mid));
            }
            if(R>mid){
                res=Math.max(res,max(L,R,rt.right,mid+1,r));
            }
            return res;
        }
        public int max(int L,int R){
            if(L>R){
                return 0;
            }
            return max(L,R,root,1,N);
        }
        public void update(int L,int R,int M){
            if(L>R){
                return;
            }
            update(L,R,M,root,1,N);
        }
    }
    public static List<Integer> fallingSquares(int[][] positions) {
        int N=1;
        for (int[] position : positions) {
            N=Math.max(N,position[0]+position[1]);
        }
        //注意这个方块下落的时候一定是左边右开下落，这样才可以避免擦边情况
        DynamicSegmentTree st=new DynamicSegmentTree(N);
        List<Integer> res=new ArrayList<>();
        for (int[] position : positions) {
            int preMax=st.max(position[0],position[0]+position[1]-1);
            st.update(position[0],position[0]+position[1]-1,preMax+position[1]);
            res.add(st.max(1,N));
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] positions={{1,2},{2,3},{6,1}};
        List<Integer> list = fallingSquares(positions);
        System.out.println(Arrays.toString(list.toArray()));
    }

}
