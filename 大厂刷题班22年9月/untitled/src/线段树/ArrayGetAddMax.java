package 线段树;

// 来自美团
// void add(int L, int R, int C)代表在arr[L...R]上每个数加C
// int get(int L, int R)代表查询arr[L...R]上的累加和
// 假设你可以在所有操作开始之前，重新排列arr
// 请返回每一次get查询的结果都加在一起最大能是多少
// 输入参数:
// int[] arr : 原始数组
// int[][] ops，二维数组每一行解释如下:
// [a,b,c]，如果数组有3个数，表示调用add(a,b,c)
// [a,b]，如果数组有2个数，表示调用get(a,b)
// a和b表示arr范围，范围假设从1开始，不从0开始
// 输出：
// 假设你可以在开始时重新排列arr，返回所有get操作返回值累计和最大是多少？

//这题最重要的是贪心策略:也就是说:arr怎么样的排序,才能是get累加和最大
//arr中get次数最多的位置应该对于arr中最大数，亿次类推，get次数越多的位置对应于arr中的数越大


/*
 * 如果数组的长度为N，总共有 N! 个序列
 * 肯定不可能全部序列尝试一次，再得出最优解
 * 肯定有一种贪心策略可以直接得出最终的答案
 *
 * 数学上:
 * 最终的累加和分为=
 * (每个位置的查询次数*(每个位置的初始值+每个位置最终获得的增量))累加和
 * 想要使得最终的结果最大只能使得每一个位置
 * */

import java.util.Arrays;

public class ArrayGetAddMax {
    //线段树功能如下
    //范围上增加 add
    //范围上求累加和 get
    public static class SegmentTree{
        public int[] sum;
        public int[] lazy;
        public int N;
        public int[] arr;

        public SegmentTree(int[] arr){
            this.arr=arr;
            N=arr.length;
            sum=new int[N<<2];
            lazy=new int[N<<2];
        }
        public void build(int rt,int l,int r){
            //base case
            if(l==r){
                sum[rt]=arr[l];
                return;
            }
            int mid=(l+(r-l)/2);
            build(rt<<1,l,mid);
            build(rt<<1|1,mid+1,r);
            pushUp(rt);
        }
        private void pushUp(int rt){
            sum[rt]=sum[rt<<1]+sum[rt<<1|1];
        }
        private void pushDown(int rt,int ln,int rn){
            if(lazy[rt]!=0){
                lazy[rt<<1]+=lazy[rt];
                lazy[rt<<1|1]+=lazy[rt];
                sum[rt<<1]+=lazy[rt]*ln;
                sum[rt<<1|1]+=lazy[rt]*rn;
                lazy[rt]=0;
            }
        }
        private void add(int L,int R,int M,int rt,int l,int r){
            //能懒更新住就懒更新
            if(L<=l&&r<=R){
                lazy[rt]+=M;
                sum[rt]+=(r-l+1)*M;
                return;
            }
            int mid=(l+(r-l)/2);
            pushDown(rt,mid-l+1,r-mid);
            if(L<=mid){
                add(L,R,M,rt<<1,l,mid);
            }
            if(R>mid){
                add(L,R,M,rt<<1|1,mid+1,r);
            }
            pushUp(rt);
        }
        private int sum(int L,int R,int rt,int l,int r){
            if(L<=l&&r<=R){
                return sum[rt];
            }
            int mid=(l+(r-l)/2);
            pushDown(rt,mid-l+1,r-mid);
            int res=0;
            if(L<=mid){
                res+=sum(L,R,rt<<1,l,mid);
            }
            if(R>mid){
                res+=sum(L,R,rt<<1|1,mid+1,r);
            }
            return res;
        }
        public void add(int L,int R,int M){
            if(L>R){
                return;
            }
            add(L,R,M,1,1,arr.length-1);
        }
        public int get(int L,int R){
            if(L>R){
                return 0;
            }
            return sum(L,R,1,1,arr.length-1);
        }
    }
    public static int arrayGetAddMax(int[] arr,int[][] options){
        //1:利用线段树查看每个位置的查询次数
        SegmentTree getTimes=new SegmentTree(new int[arr.length+1]);
        for (int[] option : options) {
            if(option.length==2){
                getTimes.add(option[0],option[1],1);
            }
        }
        int N=arr.length;

        //每个位置get的次数
        int[][] getMap=new int[N][2];
        for(int i=0;i<N;i++){
            getMap[i][0]=i+1;
            getMap[i][1]=getTimes.get(i+1,i+1);
        }
        Arrays.sort(getMap,(a,b)->a[1]-b[1]);
        Arrays.sort(arr);
        int[] numbers=new int[N+1];
        for(int i=0;i<N;i++){
            numbers[getMap[i][0]]=arr[i];
        }
        System.out.println(Arrays.toString(numbers));
        SegmentTree st=new SegmentTree(numbers);
        st.build(1,1,N);
        int res=0;
        for (int[] option : options) {
            if(option.length==3){
                st.add(option[0],option[1],option[2]);
            }else{
                res+=st.get(option[0],option[1]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5 };
        int[][] ops = { { 1, 3 }, { 2, 4 }, { 1, 5 } };
        //0 0 0 0 0
        //0 1 1 1 0
        //0 1 2 2 1
        //
        System.out.println(arrayGetAddMax(arr, ops));
    }
}
