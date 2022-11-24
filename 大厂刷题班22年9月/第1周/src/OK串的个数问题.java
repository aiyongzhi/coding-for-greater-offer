public class OK串的个数问题 {
    /*
    * 给定一个长度为n的字符串str，其中每个字符由a-z组成
    * 在长度为n的字符串str的所有可能情况下，
    * 我们定义包含至少两个"red"子串的str为OK串
    * 请返回长度为n字符串所有可能情况下OK串的个数
    *
    * 数据规模:6<=n<=10^6
    * */

    public static long getOkStrCount(int n){
        long[] dp1=new long[n+1];
        long[] dp2=new long[n+1];
        //1:填好dp1表：dp1表的含义dp1[i]:长度为i的字符串中至少含有一个“red”子字符串的ok串的个数
        dp1[3]=1;
        for (int i=4;i<=n;i++){
            dp1[i]+=(long) Math.pow(26,i-3);
            dp1[i]+=26*dp1[i-1]-dp1[i-3];
        }
        dp2[6]=1;
        for(int i=7;i<=n;i++){
            dp2[i]=dp1[i-3]+26*dp2[i-1]-dp2[i-3];
        }
        return dp2[n];
    }

    public static void main(String[] args) {
        System.out.println(getOkStrCount(7));
    }
}
