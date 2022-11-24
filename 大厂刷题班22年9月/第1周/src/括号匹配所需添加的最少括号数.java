public class 括号匹配所需添加的最少括号数 {
//    给定一个由 '[' ，']'，'('，‘)’ 组成的字符串，请问最少插入多少个括号就能使这个字符串的所有括号左右配对。
//    例如当前串是 "([[])"，那么插入一个']'即可满足。
    public static int minAddToMatch(char[] str){
        int N=str.length;
        int[][] dp=new int[N][N];
        //base case
        for(int i=0;i<N;i++){
            dp[i][i]=1;
        }
        for(int R=1;R<N;R++){
            int L=R-1;
            dp[L][R]=(str[L]=='['&&str[R]==']')||(str[L]=='('&&str[R]==')')?0:2;
        }
        //填普遍位置
        for(int i=2;i<N;i++){
            int L=0;
            int R=i;
            while(L<N&&R<N){
                int p1,p2,p3;
                p1=p2=p3=Integer.MAX_VALUE;
                if((str[L]=='['&&str[R]==']')||(str[L]=='('&&str[R]==')')){
                    p1=dp[L+1][R-1];
                }else{
                    p2=dp[L+1][R]+1;
                    p3=dp[L][R-1]+1;
                }
                //2: 是由匹配串拼接形成的
                int p4=Integer.MAX_VALUE;
                for(int split=L;split<R;split++){
                    p4=Math.min(p4,dp[L][split]+dp[split+1][R]);
                }
                dp[L][R]=Math.min(Math.min(p1,p2),Math.min(p3,p4));
                L++;
                R++;
            }
        }
        return dp[0][N-1];
    }
    //先写暴力递归
    //递归含义:让str[L...R]范围内变成括号匹配所需添加的最少括号数
    public static int process(char[] str,int L,int R){
        //base case
        if(L==R){
            return 1;
        }
        if(L+1==R){
            return (str[L]=='['&&str[R]==']')||(str[L]=='('&&str[R]==')')?0:2;
        }
        //普遍情况
        //1: 是由最外层大括号括起来的匹配串
        int p1,p2,p3;
        p1=p2=p3=Integer.MAX_VALUE;
        if((str[L]=='['&&str[R]==']')||(str[L]=='('&&str[R]==')')){
            p1=process(str,L+1,R-1);
        }else{
            p2=process(str,L+1,R)+1;
            p3=process(str,L,R-1)+1;
        }
        //2: 是由匹配串拼接形成的
        int p4=Integer.MAX_VALUE;
        for(int split=L;split<R;split++){
            p4=Math.min(p4,process(str,L,split)+process(str,split+1,R));
        }
        return Math.min(Math.min(p1,p2),Math.min(p3,p4));
    }

    public static void main(String[] args) {
        String s="()(())";
        System.out.println(minAddToMatch(s.toCharArray()));
    }
}
