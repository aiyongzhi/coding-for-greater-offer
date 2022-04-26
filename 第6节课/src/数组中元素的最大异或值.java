public class 数组中元素的最大异或值 {
    /*
    * 题目:给你一个由非负整数组成的数组 nums 。另有一个查询数组 queries ，其中 queries[i] = [xi, mi] 。

第 i 个查询的答案是 xi 和任何 nums 数组中不超过 mi 的元素按位异或（XOR）得到的最大值。换句话说，答案是 max(nums[j] XOR xi) ，
* 其中所有 j 均满足 nums[j] <= mi 。如果 nums 中的所有元素都大于 mi，最终答案就是 -1 。
返回一个整数数组 answer 作为查询的答案，其中 answer.length == queries.length 且 answer[i] 是第 i 个查询的答案
    *
    *
    * */

    //这题需要对最大子数组异或和进行改写
    //在每一个节点处，记录一下所有走过这个路口中的最小值
    //如果这个最小值都大于查询约束值，就无法进行下去，直接返回-1
    //如果这个最小值小于等于查询约束值，就可以走
    public static class Node{
        public int min;
        public Node[] next=new Node[2];
        public Node(){
            min=Integer.MAX_VALUE;
        }
    }
    public static class NumTire{
        private final Node root;
        public NumTire(){
            root=new Node();
        }
        public void add(int num){
            Node cur=root;
            cur.min=Math.min(cur.min,num);
            for(int move=31;move>=0;move--){
                //num的move位数字,当前位置需要走向的路
                int path=(num>>move)&1;
                cur.next[path]=cur.next[path]==null?new Node():cur.next[path];
                cur=cur.next[path];
                cur.min=Math.min(cur.min,num);
            }
        }
        public int getAns(int num,int m){
            Node cur=root;
            if(cur.min>m){
                return -1;
            }
            int ans=0;
            for(int move=31;move>=0;move--){
                //num的第move位数字
                int path=(num>>move)&1;
                //期望遇到的数字
                int base=move==31?path:path^1;
                //如果只有一条路，不一定能够走向期望走的路
                //实际走的路
                base=cur.next[base]!=null&&cur.next[base].min<=m?base:base^1;
                if(cur.next[base]==null){
                    return -1;
                }
                ans|=(path^base)<<move;
                cur=cur.next[base];
            }
            return ans;
        }
    }
    public static int[] maximizeXor(int[] nums, int[][] queries){
        NumTire tire=new NumTire();
        for (int num : nums) {
            tire.add(num);
        }
        int[] ans=new int[queries.length];
        for(int i=0;i<ans.length;i++){
            ans[i]=tire.getAns(queries[i][0],queries[i][1]);
        }
        return ans;
    }
}
