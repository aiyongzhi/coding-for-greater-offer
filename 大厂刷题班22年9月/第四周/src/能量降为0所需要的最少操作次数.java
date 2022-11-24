import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class 能量降为0所需要的最少操作次数 {
    // 来自美团
// 某天，小美在玩一款游戏，游戏开始时，有n台机器，
// 每台机器都有一个能量水平，分别为a1、a2、…、an，
// 小美每次操作可以选其中的一台机器，假设选的是第i台，
// 那小美可以将其变成 ai+10^k（k为正整数且0<=k<=9），
// 由于能量过高会有安全隐患，所以机器会在小美每次操作后会自动释放过高的能量
// 即变成 (ai+10^k)%m
// 其中%m表示对m取模，由于小美还有工作没有完成，所以她想请你帮她计算一下，
// 对于每台机器，将其调节至能量水平为0至少需要多少次操作
//（机器自动释放能量不计入小美的操作次数）。
// 第一行两个正整数n和m，表示数字个数和取模数值。
// 第二行为n个正整数a1, a2,...... an，其中ai表示第i台机器初始的能量水平。
// 1 <= n <= 30000，2 <= m <= 30000, 0 <= ai <= 10^12。

    public static int getMinTimes(int[] arr,int m){
        int N=arr.length;
        int[] dp=new int[N];
        Arrays.fill(dp,Integer.MAX_VALUE);
        bfs(dp,m);
        int times=0;
        for(int i=0;i<N;i++){
            if(arr[i]>=0&&arr[i]<m){
                times+=dp[arr[i]];
            }else{
                int curAns=Integer.MAX_VALUE;
                for(int add=1;add<=1000000000;add*=10) {
                    curAns=Math.min(curAns,dp[(arr[i]+add)%m]+1);
                }
                times+=curAns;
            }
        }
        return times;
    }

    public static void bfs(int[] dp,int m){
        Queue<Integer> queue=new LinkedList<>();//队列中存的是余数
        queue.add(0);
        //余数的范围是 0~m-1
        boolean[] visited=new boolean[m];
        visited[0]=true;//表示0为跟节点已经访问过了
        while (!queue.isEmpty()){
            int cur = queue.poll();
            for(int add=1;add<=1000000000;add*=10){
                int to=cur-add%m;
                if(to<0){
                    to+=m;
                }
                if(to>=0&&to<m && !visited[to] ){
                    visited[to]=true;
                    queue.add(to);
                    dp[to]=dp[cur]+1;
                }
            }
        }
    }

    public static void main(String[] args) {
    }
}
