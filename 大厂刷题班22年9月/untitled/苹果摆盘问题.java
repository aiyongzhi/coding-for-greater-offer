public class 苹果摆盘问题 {
    //m个同样的苹果，认为苹果之间没有差别
    //n个同样的盘子，认为盘子之间没有差别
    //请问将m个苹果放入n个盘子中有多少种不同的摆法?
    //可以有空盘子
    //假设有5个苹果,3个盘子
    //那么认为1,1,3 和 1,3,1 和 3,1,1 为一样的摆法。

    //现在还剩[i...n-1]这么多盘子，要摆放rest个苹果请问方法数
    public static int process1(int i,int n,int rest,int pre){
        if(i==n){
            return rest==0?1:0;
        }
        //盘子数!=0
        if(rest==0){
            return 1;
        }
        //如果rest<pre则无法进行摆盘，直接认为无效
        if(rest<pre) {
            return 0;
        }
        int ans=0;
        //还能继续摆
        for(int cur=pre;cur<=rest;cur++){
            ans+=process1(i+1,n,rest-cur,cur);
        }
        return ans;
    }

    //上述解法有三个可变参数，时间复杂度为O(m^2 * n)
    //我们也可以这样划分可能性
    //假设有7个苹果，10个盘子，那么这10个盘子中必然有三个盘子是空的
    //可以直接将空盘子敲碎，变成求解7个苹果，7个盘子的问题
    //然而7个苹果，7个盘子，又分为
    //7个盘子全用和7个盘子不全用，这两个不重复的解

    public static int process(int apples,int plates,int[][] dp){
        if(dp[apples][plates]!=-1){
            return dp[apples][plates];
        }
        if(apples==0){
            dp[0][plates]=1;
            return 1;
        }
        //apples!=0
        if(plates==0){
            dp[apples][0]=0;
            return 0;
        }
        if(plates>apples){
            dp[apples][plates]=process(apples,apples,dp);
            return dp[apples][plates];
        }else{
            dp[apples][plates]=process(apples,plates-1,dp)+process(apples-plates,plates,dp);
            return dp[apples][plates];
        }
    }
    public static int getAllWays(int m,int n){
        int[][] dp=new int[m+1][n+1];
        Arrays.fill(dp,-1);
        return process(m,n,dp);
    }
}
