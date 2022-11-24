import java.util.Arrays;

public class 砍树获得的最大收益 {
    /*
    * 现在有n棵树，有长度为n的数组weight和increase分别
    * 表示n棵树的初始重量和重量随天数增长速度，
    * 即weight[i]:表示第i颗树初始值重量 increase[i]:表示第i棵树每天重量增长量
    * 现在一个伐木工人要持续m天砍树(m<=n)，且每天至多砍一个树
    * 请返回该伐木工人砍树m天所能获得的最大收益(所砍数目的重量和即为其收益)
    * */
    public static int getMaxWeight(int n,int m,int[] weight,int[] increase){
        //两个贪心点
        //1: m天必须砍满m棵树，不能有空闲的天数
        //2：n颗树无论选择砍哪m颗，我们一定都能获得其初始重量收益，只需让其增长收益最大
        //即先砍增长速率小的再砍增长速率大的
        int[][] tree=new int[n][2];
        for(int i=0;i<n;i++){
            tree[i][0]=weight[i];
            tree[i][1]=increase[i];
        }
        Arrays.sort(tree,0,n,(o1,o2)-> o1[1]-o2[1]);
        for(int i=0;i<n;i++){
            System.out.println("weight:"+tree[i][0]+"---"+"increase:"+tree[i][1]);
        }
        int[][] dp=new int[n][m];
        dp[0][0]=tree[0][0];
        //1: 填第一列
        for(int i=1;i<n;i++){
            dp[i][0]=Math.max(tree[i][0],dp[i-1][0]);
        }
        //2：填第一行
        for(int j=1;j<m;j++){
            dp[0][j]=tree[0][0]+j*tree[0][1];
        }
        //3: 填普遍位置
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                int p1=dp[i-1][j-1]+tree[i][0]+j*tree[i][1];
                int p2=dp[i-1][j];
                dp[i][j]=Math.max(p1,p2);
            }
        }
        return dp[n-1][m-1];
    }

    public static void main(String[] args) {
        int n=3;
        int m=2;
        int[]weight=new int[]{4,7,3};
        int[]increase=new int[]{3,1,2};
        System.out.println(getMaxWeight(n, m, weight, increase));
    }

}
