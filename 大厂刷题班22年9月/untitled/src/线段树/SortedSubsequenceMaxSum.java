package 线段树;

// 来自字节
// 一共有n个人，从左到右排列，依次编号0~n-1
// h[i]是第i个人的身高
// v[i]是第i个人的分数
// 要求从左到右选出一个子序列，在这个子序列中的人，从左到右身高是不下降的
// 返回所有符合要求的子序列中，分数最大累加和是多大
// n <= 10的5次方, 1 <= h[i] <= 10的9次方, 1 <= v[i] <= 10的9次方
//这题不能用背包:dp没戏

//这题动态开点线段树能红的住
public class SortedSubsequenceMaxSum {

    //线段树的常用场景是从左向右尝试，以每一个下标结尾的最优解时需要去前面求出的结果中查找
    //子数组    线段树是热门考点: 遇到子数组问题，没办法用dp，就考虑考虑线段树

    public static class Node{
        public int max;
        public int update;
        public Node left;
        public Node right;
        public Node(){
            max=0;
            update=-1;
        }
    }

    public static class DynamicSegmentTree{
        public Node root;
        public int N;
        public DynamicSegmentTree(int n){
            N=n;
            root=new Node();
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
            if(rt.update!=-1){//下放懒更新
                rt.left.update=rt.update;
                rt.right.update=rt.update;
                rt.left.max=rt.update;
                rt.right.max=rt.update;
                rt.update=-1;
            }
        }
        private void update(int L,int R,int M,Node rt,int l,int r){
            //能懒更新住就懒更新
            if(L<=r&&r<=R){
                rt.update=M;
                rt.max=M;
                return;
            }
            int mid=l+(r-l)/2;
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
            int mid=l+(r-l)/2;
            pushDown(rt);
            int ans=0;
            if(L<=mid){
                ans=Math.max(ans,max(L,R,rt.left,l,mid));
            }
            if(R>mid){
                ans=Math.max(ans,max(L,R,rt.right,mid+1,r));
            }
            return ans;
        }
        public void update(int index,int M){
            update(index,index,M,root,1,N);
        }
        public int max(int L,int R){
            return max(L,R,root,1,N);
        }
    }
    public static int getMaxSum(int[] h,int[] v){
        int n=h.length;
        int maxH=0;
        for (int j : h) {
            maxH = Math.max(j, maxH);
        }
        DynamicSegmentTree segmentTree=new DynamicSegmentTree(maxH);
        int ans=0;
        for(int i=0;i<n;i++){
            int preMax=segmentTree.max(1,h[i]);
            ans=Math.max(ans,preMax+v[i]);
            segmentTree.update(h[i],preMax+v[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] h={9,2,6,7};
        int[] v={1,5,3,2};
        System.out.println(getMaxSum(h,v));
    }
}
