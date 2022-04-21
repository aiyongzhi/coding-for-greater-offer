import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class str2删除最少字符成为str1的字串 {
    /*
    * 给定两个字符串str1，str2,
    * 请将str2变成str1的字串，但只能通过删除字符的方式
    * 请返回删除的最少字符数
    * */

    /* 解法一:时间复杂度为O(2^M * N)
    * 记str1的长度为N，str2的长度为M
    * 一:如果M很小，一般小于10
    * 这时候我们就把str2删除字符后所有的可能性存放起来
    * 逐个用KMP来判断是否是str1的字串
    * */
    public static class MyComparator implements Comparator<String>{
        @Override
        public int compare(String o1, String o2) {
            return o2.length()-o1.length();
        }
    }
    public static int minDistance1(String s1,String s2){
        //base case
        if(s1==null||s1.length()==0||s2==null||s2.length()==0) {
            return Integer.MAX_VALUE;
        }
        List<String> ans=new ArrayList<>();
        process(s2.toCharArray(),0,"",ans);
        ans.sort(new MyComparator());
        for (String str : ans) {
            if (s1.contains(str)) {
                return s2.length() - str.length();
            }
        }
        return Integer.MAX_VALUE;
    }
    /*
    * 暴力递归获取一个字符串的全部子序列
    * */
    public static void process(char[] str, int index, String path, List<String> ans){
        //base case
        if(index==str.length){
            ans.add(path);
            return;
        }
        //还有字符可以选择
        process(str,index+1,path+str[index],ans);
        process(str,index+1,path,ans);
    }
/*
* 如果M稍微大一些，上面的指数级算法就会指数爆炸
* 因此我们换一种视角
*
* str2最终是要拼凑成str1的子串的，
* 因此我们可以枚举出str1的所有子串,看看str2只通过删除变成str1的每一个子串都有一个最少步数，
* 将所有最小步数求最小就可以
* 而由一个字符串通过删除变成另一个字符串，就是编辑距离问题的阉割版
* */
    public static int minDistance2(String s1,String s2){
        //base case
        if(s1==null||s1.length()==0||s2==null||s2.length()==0) {
            return Integer.MAX_VALUE;
        }
        char[] str1=s1.toCharArray();
        char[] str2=s2.toCharArray();
        int ans=Integer.MAX_VALUE;
        for(int L=0;L<str1.length;L++){
            for(int R=L;R<str1.length;R++){
                char[] str=s1.substring(L,R+1).toCharArray();
                ans=Math.min(ans,editDistance(str2,str));
            }
        }
        return ans;
    }
    /* 功能:只通过删除操作将str1变成str2,返回最少的步数  */
    public static int editDistance(char[] str1,char[] str2){
        if(str1.length<str2.length){
            return Integer.MAX_VALUE;
        }
        //N>=M
        /* 动态规划:样本对应模型 */
        int N=str1.length;
        int M=str2.length;
        int[][] dp=new int[N+1][M+1];
        //先填对角线
        for(int i=0;i<=N;i++){
            for(int j=0;j<=M;j++){
                dp[i][j]=Integer.MAX_VALUE;
            }
        }
        dp[0][0]=0;
        //填第一列
        for(int i=1;i<=N;i++){
            dp[i][0]=i;
        }
        //填普遍位置
        for(int i=1;i<=N;i++){
            for(int j=1;j<=Math.min(i,M);j++){
                if(dp[i-1][j]!=Integer.MAX_VALUE){
                    dp[i][j]=1+dp[i-1][j];
                }
                if(str1[i-1]==str2[j-1]&&dp[i-1][j-1]!=Integer.MAX_VALUE){
                    dp[i][j]=Math.min(dp[i][j],dp[i-1][j-1]);
                }
            }
        }
        return dp[N][M];
    }
    public static void main(String[] args) {
        String s1="abdefg";
        String s2="k";
        System.out.println(minDistance1(s1,s2));
        System.out.println(minDistance2(s1,s2));
    }

}
