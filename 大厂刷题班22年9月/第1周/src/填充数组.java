public class 填充数组 {
//    牛妹给了牛牛一个长度为  的下标从开始的正整型数组  ，粗心的牛牛不小心把其中的一些数字删除了。
//
//    假如被删除了，则。对于所有被删除的数字，牛牛必须选择一个正整数填充上。现在牛牛想知道有多少种填充方案使得：
//
//    且对于所有的满足 。
//    函数传入一个下标从开始的数组  和一个正整数  ，请返回合法的填充方案数对 取模的值,保证不存在方案数为0的数据。


    //用mim-max范围内的数，填充n个数确保这n个数有序
    public static int process(int i,int pre,int max,int n){
        //base case
        if(i==n){
            return 1;
        }
        int ans=0;
        for(int cur=pre;cur<=max;cur++){
            ans+=process(i+1,cur,max,n);
        }
        return ans;
    }
    //上述暴力递归进行优化:优化为动态规划并用斜率优化
    private static final int MOD=(int)(Math.pow(10,9)+7);
    public static long dpWays(int min,int max,int n){
        long[][] dp=new long[n+1][max+1];
        //base case
        //填最后一行
        for(int pre=min;pre<=max;pre++){
            dp[n][pre]=1;
        }
        //填普遍位置
        //从下往上从右往左 动归斜优
        for(int i=n-1;i>=0;i--){
            for(int pre=max;pre>=min;pre--){
                dp[i][pre]=(dp[i+1][pre]+(pre+1<=max?dp[i][pre+1]:0))%MOD;
            }
        }
        return dp[0][min];
    }

    public static void main(String[] args) {
        int[] a=new int[]{0,4,5};
        int k=6;
        System.out.println(FillArray(a,k));
    }
    public static int FillArray (int[] a, int k) {
        int pre=1;
        int n=a.length;
        int zero=0;
        long ans=1;
        for(int i=0;i<n;i++){
            int cur=a[i];
            if(cur==0){
                zero++;
            }else{
                if(zero>0){
                    int max=Math.min(cur,k);
                    ans*=dpWays(pre,max,zero);
                    ans%=MOD;
                    zero=0;
                }
                pre=cur;
            }
        }
        if(zero>0){
            ans*=dpWays(pre,k,zero);
            ans%=MOD;
        }
        return (int)(ans);
    }

}
