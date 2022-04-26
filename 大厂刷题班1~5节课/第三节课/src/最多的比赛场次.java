import java.util.Arrays;
import java.util.HashSet;

public class 最多的比赛场次 {
    /*
    * 腾讯面试题
    * 给定一个数组ability,代表每一个人的能力值，再给定一个非负整数k
    * 只有两个人的能力差恰好为k，这两个人才能进行比赛
    * 返回最多同时有多少场比赛。
    * */

    /*
    * 暴力解:枚举所有可能性，尝试当前场次任意匹配，后序解的可能性
    *
    * 技巧: 贪心
    * */
    public static int process(int[] ability, int k){
        //base case
        if(ability==null||ability.length<2){
            return 0;
        }
        int max=Integer.MIN_VALUE;
        //枚举出所有当前能凑对的第一场比赛
        for(int L=0;L<ability.length;L++){
            for(int R=L+1;R<ability.length;R++){
                if(Math.abs(ability[L]-ability[R])==k){
                    int[] next=getNextArray(ability,L,R);
                    max=Math.max(max,1+process(next,k));
                }
            }
        }
        return max==Integer.MIN_VALUE?0:max;
    }
    private static int[] getNextArray(int[] ability, int L, int R) {
        if(ability.length==2){
            return null;
        }
        int[] res=new int[ability.length-2];
        int index=0;
        for(int i=0;i<ability.length;i++){
            if(i!=L&&i!=R){
                res[index++]=ability[i];
            }
        }
        return res;
    }

    public static int compareWays(int[] ability,int k){
        if(ability==null||ability.length<2||k<0){
            return 0;
        }
        return process(ability,k);
    }
    //最优解:贪心算法:从小到大排序后，对于每一个能力低的人尝试与最接近他能力比他刚好高k的人配对
    public static int getCompetitions(int[] ability,int k){
        if(ability==null||ability.length<2||k<0){
            return 0;
        }
        Arrays.sort(ability);
        int N=ability.length;
        boolean[] visited=new boolean[N];
        //L会走到之前R成功的位置，用visited去重
        int L=0;
        int R=L+1;
        int count=0;
        while(R<N){
            if(visited[L]){
                L++;
            }
            else{
                if(L==R){//如果只有一个人
                    R++;
                }
                else{//L和R不是同一个
                    int distance=ability[R]-ability[L];
                    if(distance==k){//成功凑成一场比赛
                        visited[R++]=true;
                        L++;
                        count++;
                    }
                    else if(distance<k){
                        R++;
                    }
                    else{
                        L++;
                    }
                }
            }
        }
        return count;
    }

    //对数器
    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime=100000;
        int maxValue=100;
        int maxLen=20;
        int maxKValue=20;
        for(int i=0;i<testTime;i++){
            int k=(int)(Math.random()*maxKValue);
            int[] arr1=generateRandomArray(maxLen,maxValue);
            int[] arr2=copyArray(arr1);
            int ans1=compareWays(arr1,k);
            int ans2=getCompetitions(arr2,k);
            if(ans1!=ans2){
                System.out.println("k=="+k);
                System.out.println(Arrays.toString(arr2));
                System.out.println("ans1=="+ans1);
                System.out.println("ans2=="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
