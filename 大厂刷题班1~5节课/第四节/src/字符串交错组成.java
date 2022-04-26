import java.util.Arrays;

public class 字符串交错组成 {
    /*  力扣链接:https://leetcode-cn.com/problems/interleaving-string/
    * 题目含义:现有字符串str1和字符串str2和str3
    * 请问str3能否用str1和str2交错组成
    * 如果能则返回true，否则返回false
    *  */
    public static boolean isInterleave1(String s1,String s2,String s3){
        if(s1==null||s1.length()==0){
            return s2.equals(s3);
        }
        if(s2==null||s2.length()==0){
            return s1.equals(s3);
        }
        int N=s1.length();
        int M=s2.length();
        if(N+M!=s3.length()){
            return false;
        }
        boolean[][] dp=new boolean[N+1][M+1];
        char[] str1=s1.toCharArray();
        char[] str2=s2.toCharArray();
        char[] str3=s3.toCharArray();
        dp[0][0]=true;
        //填第一行
        for(int j=1;j<=M;j++){
            dp[0][j]=dp[0][j-1]&&(str2[j-1]==str3[j-1]);
        }
        //填第一列
        for(int i=1;i<=N;i++){
            dp[i][0]=dp[i-1][0]&&(str1[i-1]==str3[i-1]);
        }
        //填普遍位置
        for(int i=1;i<=N;i++){
            for(int j=1;j<=M;j++){
                if (
                        (str1[i - 1] == str3[i + j - 1] && dp[i - 1][j]) ||
                        (str2[j - 1] == str3[i + j - 1] && dp[i][j - 1])
                ) {
                    dp[i][j]=true;
                }
            }
        }
        return dp[N][M];
    }
    //空间压缩技巧
    //最优解
    public static boolean isInterleave2(String s1,String s2,String s3){
        if(s1==null||s1.length()==0){
            return s2.equals(s3);
        }
        if(s2==null||s2.length()==0){
            return s1.equals(s3);
        }
        int N=s1.length();
        int M=s2.length();
        if(N+M!=s3.length()){
            return false;
        }
        boolean[] dp=new boolean[M+1];
        char[] str1=s1.toCharArray();
        char[] str2=s2.toCharArray();
        char[] str3=s3.toCharArray();
        dp[0]=true;
        //填第一行
        for(int j=1;j<=M;j++){
            dp[j]=dp[j-1]&&(str2[j-1]==str3[j-1]);
        }
        //填普遍位置
        for(int i=1;i<=N;i++){
            dp[0]=dp[0]&&(str1[i-1]==str3[i-1]);
            for(int j=1;j<=M;j++){
                if (
                        ((str1[i - 1] == str3[i + j - 1]) && dp[j]) ||
                        ((str2[j - 1] == str3[i + j - 1]) && dp[j-1])
                ) {
                    dp[j]=true;
                }
                else{
                    dp[j]=false;
                }
            }
        }
        return dp[M];
    }
    public static void main(String[] args) {
        String s1="ab";
        String s2="bc";
        String s3="bbac";
        isInterleave2(s1,s2,s3);
    }
}
