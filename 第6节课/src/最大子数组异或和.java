import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class 最大子数组异或和 {
    /*
    * 题目:对于任意一个数组，我们把数组中所有元素异或
    * 起来称为这个子数组的异或和
    * 现在给定你一个数组arr，请返回这个数组中所有子数组中最大异或和。
    * */

    //设计前缀树(字典树)
    //前缀树提供两种方法,这两种方法的时间复杂度均为O(1)
    //1:向前缀树中添加一个数字
    //2:传入一个数字，返回当前前缀树中与它异或和最大的数
    public static class Node{
        public Node[] next=new Node[2];
        public Node(){}
    }
    public static class NumTire{
        private final Node root;
        public NumTire(){
            root=new Node();
        }
        public void add(int num){
            Node cur=root;
            for(int move=31;move>=0;move--){
                //num的move位数字,当前位置需要走向的路
                int path=(num>>move)&1;
                cur.next[path]=cur.next[path]==null?new Node():cur.next[path];
                cur=cur.next[path];
            }
        }
        public int getAns(int num){
            Node cur=root;
            int ans=0;
            for(int move=31;move>=0;move--){
                //num的第move位数字
                int path=(num>>move)&1;
                //期望遇到的数字
                int base=move==31?path:path^1;
                //如果只有一条路，不一定能够走向期望走的路
                //实际走的路
                base=cur.next[base]!=null?base:base^1;
                ans|=(path^base)<<move;
                cur=cur.next[base];
            }
            return ans;
        }
    }
    //子数组不能位空
    public static int getSubArrayMaxXOr(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        NumTire tire=new NumTire();
        tire.add(0);
        int ans=Integer.MIN_VALUE;
        int xor=0;
        for (int j : arr) {
            xor ^= j;
            ans = Math.max(ans, tire.getAns(xor));
            tire.add(xor);
        }
        return ans;
    }
    /* 写一个暴力解作为对数器  */
    public static int compareWays(int[] arr){
        if(arr==null||arr.length==0) {
            return 0;
        }
        int asn=Integer.MIN_VALUE;
        for(int L=0;L<arr.length;L++){
            for(int R=L;R<arr.length;R++){
                int curAns=0;
                for(int k=L;k<=R;k++){
                    curAns^=arr[k];
                }
                asn=Math.max(asn,curAns);
            }
        }
        return asn;
    }
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue)-(int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime=100000;
        int maxValue=1000;
        int maxLen=100;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            int ans1=getSubArrayMaxXOr(arr);
            int ans2=compareWays(arr);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(arr));
                System.out.println("ans1===="+ans1);
                System.out.println("asn2===="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
