import java.util.Arrays;

public class 长度为3递增子序列的个数 {
/*
* 来自微众银行
* 给定一个数字n,代表一个长度为n的数组
* 再给定一个数组m，代表数组中每个数从1-m中选出
* 在所有可能形成的数组中，最长递增子序列长度为3的称为达标数组
* 请问在给定n，m的条件下，共有多少个达标数组
* */

    //i:当前来到的下标 f:[0..i-1]中长度为1的最长递增子序列的最小结尾 s t同理
    public static int process1(int i,int n,int m,int f,int s,int t){
        if(i==n){
            return t==0?0:1;
        }
        //还有位置可以填
        int ans=0;
        for(int cur=1;cur<=m;cur++){
            if(f==0|| f>=cur){//被第一个位置拦住
                ans+=process1(i+1,n,m,cur,s,t);
            }else if(s==0||s>=cur){
                ans+=process1(i+1,n,m,f,cur,t);
            }else if(t==0||t>=cur){
                ans+=process1(i+1,n,m,f,s,cur);
            }else{
                break;
            }
        }
        return ans;
    }
    //i:当前来到的下标 f:[0..i-1]中长度为1的最长递增子序列的最小结尾 s t同理
    public static int process2(int i,int n,int m,int f,int s,int t,int[][][][] dp){
        if(dp[i][f][s][t]!=-1){
            return dp[i][f][s][t];
        }
        int ans=0;
        if(i==n){
            ans=t==0?0:1;
        }else{
            for(int cur=1;cur<=m;cur++){
                if(f==0|| f>=cur){//被第一个位置拦住
                    ans+=process2(i+1,n,m,cur,s,t,dp);
                }else if(s==0||s>=cur){
                    ans+=process2(i+1,n,m,f,cur,t,dp);
                }else if(t==0||t>=cur){
                    ans+=process2(i+1,n,m,f,s,cur,dp);
                }else{
                    break;
                }
            }
        }
        dp[i][f][s][t]=ans;
        return ans;
    }
    public static int dpWays(int n,int m){
        int[][][][] dp=new int[n+1][m+1][m+1][m+1];
        for(int i=0;i<=n;i++){
            for(int j=0;j<=m;j++){
                for(int k=0;k<=m;k++){
                    Arrays.fill(dp[i][j][k],-1);
                }
            }
        }
        return process2(0,n,m,0,0,0,dp);
    }
    //对数器
    public static int number1(int n, int m) {
        return process1(0, n, m, new int[n]);
    }

    public static int process1(int i, int n, int m, int[] path) {
        if (i == n) {
            return lengthOfLIS(path) == 3 ? 1 : 0;
        } else {
            int ans = 0;
            for (int cur = 1; cur <= m; cur++) {
                path[i] = cur;
                ans += process1(i + 1, n, m, path);
            }
            return ans;
        }
    }

    public static int lengthOfLIS(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] ends = new int[arr.length];
        ends[0] = arr[0];
        int right = 0;
        int max = 1;
        for (int i = 1; i < arr.length; i++) {
            int l = 0;
            int r = right;
            while (l <= r) {
                int m = (l + r) / 2;
                if (arr[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            right = Math.max(right, l);
            ends[l] = arr[i];
            max = Math.max(max, l + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        int testTime=100;
        int maxValue=5;
        for(int i=0;i<testTime;i++){
            int n= (int) (Math.random()*maxValue)+1;
            int m= (int) (Math.random()*maxValue)+1;

            int ans1=number1(n,m);
            int ans2=dpWays(n,m);
            if(ans1!=ans2){
                System.out.println("n=="+n);
                System.out.println("m=="+m);
                System.out.println("ans1=="+ans1);
                System.out.println("ans2=="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
