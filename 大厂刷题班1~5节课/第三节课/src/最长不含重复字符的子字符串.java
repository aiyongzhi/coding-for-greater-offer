public class 最长不含重复字符的子字符串 {
    /*
    * 题目:请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
    * */

    /*
    * 解法一:滑动窗口 最优解:时间复杂度O(N) 空间复杂度O(1)
    * */

    public static int lengthOfLongestSubstring(String s){
        if(s==null||s.length()==0){
            return 0;
        }
        int max=Integer.MIN_VALUE;
        char[] str=s.toCharArray();
        int N=str.length;
        int R=0;
        //统计子字符串中出现的字符
        boolean[] appeared=new boolean[256];
        for(int L=0;L<str.length;L++){
            while (R<str.length&&!appeared[str[R]]){
                appeared[str[R++]]=true;
            }
            max=Math.max(max,R-L);
            appeared[str[L]]=false;
        }
        return max;
    }

    /*
    * 解法二:动态规划
    * 尝试以每一个位置作为子字符串的结尾,看他能往左边划多远的距离
    * dp[i]怎么求?
    * 关联因素
    * 1:str[i]上一次出现的位置?
    * 2.dp[i-1]时往左推到哪里?
    * 谁离i近就依赖谁
    * */
    public static int dpWays1(String s){
        if(s==null||s.equals("")){
            return 0;
        }
        //用数组记录每个字符上一次出现的位置
        int[] appeared=new int[256];
        for(int i=0;i<256;i++){
            appeared[i]=-1;
        }
        char[] str=s.toCharArray();
        int N=str.length;
        int[] dp=new int[N];
        dp[0]=1;
        appeared[str[0]]=0;
        for(int i=1;i<N;i++){
            int pre=Math.max(i-1-dp[i-1],appeared[str[i]]);
            dp[i]=i-pre;
        }
        return dp[N-1];
    }
    //空间压缩
    public static int dpWays2(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        //用数组记录每个字符上一次出现的位置
        int[] appeared = new int[256];
        for (int i = 0; i < 256; i++) {
            appeared[i] = -1;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int dp = 1;
        appeared[str[0]] = 0;
        int max = 1;
        int pre=-1;
        for (int i = 1; i < N; i++) {
            pre =Math.max(pre,appeared[str[i]]);
            dp = i - pre;
            max = Math.max(max, dp);
            appeared[str[i]] = i;
        }
        return max;
    }
}
