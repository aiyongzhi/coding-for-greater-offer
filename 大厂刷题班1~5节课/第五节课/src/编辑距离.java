public class 编辑距离 {
    /*
    * 题目:给定两个字符串str1,和str2
    * 请你将str1编辑成str2，并返回最短编辑距离
    * 你只能选择4种操作
    * 1)保留不变 编辑距离 0
    * 2)增加一个字符 编辑距离 a
    * 3)删除一个字符 编辑距离 d
    * 4)修改一个字符 编辑距离 r
    * */

    /*
    * 这题最容易想到的就是暴力尝试，但暴力尝试无疑是指数数量级的算法
    * 并且还是较大数的指数级，所以一定要写出动态规划，不然必定不过
    *
    * */
    /*
    * 动态规划:样本对应模型
    * 考虑str1长度i前缀编辑成str2长度为j的前缀的最短编辑距离
    *
    *
    *   1.删除i位置的字符dp[i-1][j]+d
    *
    *   2.增加一个字符 dp[i][j-1]+a
    *   3.if(str1[i-1]!=str2[j-1]) dp[i-1][j-1]+r
    *   4.if(str1[i-1]==str2[j-1]) dp[i-1][j-1]
    *
    */
    public static int minDistance(String word1,String word2,int a,int d,int r){
        if(word1==null||word1.length()==0){
            return word2!=null&&word2.length()!=0?a*word2.length():0;
        }
        if(word2==null||word2.length()==0){
            return d*word1.length();
        }
        int len1=word1.length();
        int len2=word2.length();
        char[] str1=word1.toCharArray();
        char[] str2=word2.toCharArray();
        int[][] dp=new int[len1+1][len2+1];//多加一行一列是为了减少前面的判断
        //填特殊位置
        dp[0][0]=0;
        for(int j=1;j<=len2;j++){
            dp[0][j]=j*a;
        }
        for(int i=1;i<=len1;i++){
            dp[i][0]=i*d;
        }
        //填普遍位置
        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                dp[i][j]=dp[i-1][j]+d;
                dp[i][j]=Math.min(dp[i][j],dp[i][j-1]+a);
                if(str1[i-1]!=str2[j-1]){
                    dp[i][j]=Math.min(dp[i][j],dp[i-1][j-1]+r);
                }
                else{
                    dp[i][j]=Math.min(dp[i][j],dp[i-1][j-1]);
                }
            }
        }
        return dp[len1][len2];
    }
}
