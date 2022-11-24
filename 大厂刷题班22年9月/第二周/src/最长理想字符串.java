public class 最长理想字符串 {
    /*
      美团
    * 给定一个只有小写字母组成的字符串s，和一个整数k
    * 如果满足以下两个条件称字符串t是s的理想字符串
    * 1) t是s的子序列
    * 2) t中任意相邻字符在字母表中的位次差不超过k
    * 请返回满足要求的最长理想字符串的长度
    * */

    //暴力递归+记忆化搜索
    //[i....]选择满足要求的最长理想字符串
    public static int process(int i,int pre,char[] str,int k,int[][] dp){
        if(dp[i][pre]!=-1){
            return dp[i][pre];
        }
        if(i==str.length){
            dp[i][pre]=0;
            return 0;
        }
        int cur=str[i]-'a'+1;
        //1:不要当前字符
        int p1=process(i+1,pre,str,k,dp);
        //2:要当前字符
        int p2=0;
        if(Math.abs(cur-pre)<=k||pre==0){
            p2=1+process(i+1,cur,str,k,dp);
        }
        dp[i][pre]=Math.max(p1,p2);
        return dp[i][pre];
    }
    public static int dpWays(String s,int k){
        if(s==null||s.equals("")){
            return 0;
        }
        char[] str=s.toCharArray();
        int N=str.length;
        int[][] dp=new int[N+1][27];
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[i].length;j++){
                dp[i][j]=-1;
            }
        }
        return process(0,0,str,k,dp);
    }

    //线段树
    public static class SegmentTree {
        private int n;
        private int[] max;

        public SegmentTree(int maxSize) {
            n = maxSize + 1;
            max = new int[n << 2];
        }
        public void update(int index, int c) {
            update(index, index, c, 1, n, 1);
        }

        public int max(int left, int right) {
            return max(left, right, 1, n, 1);
        }
        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        private void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                max[rt] = C;
                return;
            }
            int mid = (l + r) >> 1;
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        private int max(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return max[rt];
            }
            int mid = (l + r) >> 1;
            int ans = 0;
            if (L <= mid) {
                ans = Math.max(ans, max(L, R, l, mid, rt << 1));
            }
            if (R > mid) {
                ans = Math.max(ans, max(L, R, mid + 1, r, rt << 1 | 1));
            }
            return ans;
        }

    }
    //逻辑当前遍历到i字符时
    //考虑0-i-1中以每个字符结尾的最长理想字符串
    //中选择能够与i字母拼接的最长理想字符串

    public static int longestIdealString(String s,int k){
        if(s==null||s.equals("")){
            return 0;
        }
        SegmentTree segmentTree=new SegmentTree(26);
        int ans=0;
        char[] str=s.toCharArray();
        for (char c : str) {
            int cur=c-'a'+1;
            int preMax=segmentTree.max(Math.max(cur-k,1),Math.min(cur+k,26));
            ans=Math.max(ans,preMax+1);
            segmentTree.update(cur,preMax+1);
        }
        return ans;
    }
}
