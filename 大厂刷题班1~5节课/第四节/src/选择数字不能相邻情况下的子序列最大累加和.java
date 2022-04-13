import java.util.Arrays;

public class 选择数字不能相邻情况下的子序列最大累加和 {
    /*
    * 给定一个数组arr，请返回选择数字不能相邻
    * 的情况下子序列的最大累加和
    *
    * */
    /*
    * 子序列问题，从左向右的尝试模型
    * 现在实力功底够了，对于简单的题就没必要从暴力递归到动态规划了
    *
    * [0....i]上讨论dp[i]的可能性
    *
    * 我们这种情况是不能全都不选至少要选择一个
    * 有三种情况
    * 1) 不选i位置的数,dp[i-1]
    * 2) 选i位置的数
    *    1.只要arr[i]
    *    2.要arr[i]+dp[i-2]
    * */
    public static int maxSum(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        if(N==1){
            return arr[0];
        }
        if(N==2){
            return Math.max(arr[1],arr[0]);
        }
        int[] dp=new int[N];
        dp[0]=arr[0];
        dp[1]=Math.max(arr[1],arr[0]);
        for(int i=2;i<dp.length;i++){
            dp[i]=Math.max(Math.max(dp[i-1],arr[i]),arr[i]+dp[i-2]);
        }
        return dp[N-1];
    }
    //写对数器
}
