package 线段树;

public class 累加和功能 {
    /*
    * 线段树:
    * 提供：范围上累加add方法
    * 范围上更新,update方法
    * 范围上求累加和query
    *
    * 其实线段树的思路很简单
    * 用范围构建二叉树比如1-n
    *               1~n/2  n/2+1~n
    *                ...........
    * 这个线段范围可以表示范围上的累加和，范围上的最大值，最小值
    *
    * 还有就是懒更新技术(也可以理解为缓存技术)
    * 写代码的时候一定要注意：处理新任务一定要将懒更新的值向下划分
    * */

    public static class SegmentTree{

        private int[] arr;
        private int[] lazy;//懒更新住节点的累加和信息
        private int[] update;//懒更新住节点的更新信息
        private boolean[] change;//表示这个位置是否被修改过
        private int[] sum;//累加和线段树
        public SegmentTree(int[] numbers){
            int MAXN=numbers.length+1;
            arr=new int[MAXN];
            System.arraycopy(numbers, 0, arr, 1, MAXN - 1);
            lazy=new int[MAXN<<2];
            update=new int[MAXN<<2];
            change=new boolean[MAXN<<2];
            sum=new int[MAXN<<2];
        }
        public void pushUp(int rt){
            sum[rt]=sum[rt<<1]+sum[rt<<1|1];
        }
        //构建一颗累加和线段树
        public void build(int l,int r,int rt){
            //base case
            if(l==r){
                sum[rt]=arr[l];
                return;
            }
            //递归构造累加和线段树
            int mid=(l+r)/2;
            build(l,mid,rt<<1);
            build(mid+1,r,rt<<1|1);
            pushUp(rt);
        }
        public void pushDown(int rt,int ln,int rn){
            if(change[rt]){
                update[rt<<1]=update[rt];
                update[rt<<1|1]=update[rt];
                change[rt]=false;
                change[rt<<1]=true;
                change[rt<<1|1]=true;
                lazy[rt<<1]=0;
                lazy[rt<<1|1]=0;
                sum[rt<<1]=update[rt]*ln;
                sum[rt<<1|1]=update[rt]*rn;
            }
            if(lazy[rt]!=0){
                lazy[rt<<1]+=lazy[rt];
                lazy[rt<<1|1]+=lazy[rt];
                sum[rt<<1]+=lazy[rt]*ln;
                sum[rt<<1|1]+=lazy[rt]*rn;
                lazy[rt]=0;
            }
        }
        //任务是arr[L...R]全加M,当前来到的节点是rt号节点(范围是l.....r)
        public void add(int L,int R,int M,int l,int r,int rt){
            if(L>R){
                return;
            }
            //base case
            //懒更新住就不要往下丢任务
            if(L<=l&&r<=R){
                lazy[rt]+=M;
                sum[rt]+=(r-l+1)*M;
                return;
            }
            int mid=(l+r)/2;
            pushDown(rt,mid-l+1,r-mid);
            //下发任务
            if(L<=mid){
                add(L,R,M,l,mid,rt<<1);
            }
            if(R>mid){
                add(L,R,M,mid+1,r,rt<<1|1);
            }
            pushUp(rt);
        }
        //任务是将arr[L...R]范围更新成M，当前线段树节点编号为rt，范围是[l...r]
        public void update(int L,int R,int M,int l,int r,int rt){
            if(L>R){
                return;
            }
            //base case
            //能否懒更新住
            if(L<=l&&r<=R){
                update[rt]=M;
                change[rt]=true;
                lazy[rt]=0;
                sum[rt]=(r-l+1)*M;
                return;
            }
            int mid=(l+r)/2;
            pushDown(rt,mid-l+1,r-mid);
            //下放当前任务
            if(L<=mid){
                update(L,R,M,l,mid,rt<<1);
            }
            if(R>mid){
                update(L,R,M,mid+1,r,rt<<1|1);
            }
            pushUp(rt);
        }
        public int querySum(int L,int R,int l,int r,int rt){
            if(L>R){
                return Integer.MAX_VALUE;//表示无效区间
            }
            //能懒加载住就懒加载住
            if(L<=l&&r<=R){
                return sum[rt];
            }
            int mid=(l+r)/2;
            pushDown(rt,mid-l+1,r-mid);
            //下方任务
            int sum=0;
            if(L<=mid){
                sum+=querySum(L,R,l,mid,rt<<1);
            }
            if(R>mid){
                sum+=querySum(L,R,mid+1,r,rt<<1|1);
            }
            return sum;
        }
        public int sum(int L,int R){
            if(L>R){
                return Integer.MAX_VALUE;
            }
            return querySum(L,R,1,arr.length-1,1);
        }
        public void add(int L,int R,int M){
            if(L>R){
                return;
            }
            add(L,R,M,1,arr.length-1,1);
        }
        public void update(int L,int R,int M){
            if(L>R){
                return;
            }
            update(L,R,M,1,arr.length-1,1);
        }
    }

    public static void main(String[] args) {
        int[] arr={1,2,3,4,5};
        SegmentTree segmentTree=new SegmentTree(arr);
        segmentTree.build(1,arr.length,1);
        segmentTree.update(1,4,2);
        System.out.println(segmentTree.sum(1,4));
    }
}
