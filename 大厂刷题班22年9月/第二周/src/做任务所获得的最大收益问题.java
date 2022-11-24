import java.util.Arrays;

public class 做任务所获得的最大收益问题 {
    // 有n个城市，城市从0到n-1进行编号。小美最初住在k号城市中
// 在接下来的m天里，小美每天会收到一个任务
// 她可以选择完成当天的任务或者放弃该任务
// 第i天的任务需要在ci号城市完成，如果她选择完成这个任务
// 若任务开始前她恰好在ci号城市，则会获得ai的收益
// 若她不在ci号城市，她会前往ci号城市，获得bi的收益
// 当天的任务她都会当天完成
// 任务完成后，她会留在该任务所在的ci号城市直到接受下一个任务
// 如果她选择放弃任务，她会停留原地，且不会获得收益
// 小美想知道，如果她s2合理地完成任务，最大能获得多少收益
// 输入描述: 第一行三个正整数n, m和k，表示城市数量，总天数，初始所在城市
// 第二行为m个整数c1, c2,...... cm，其中ci表示第i天的任务所在地点为ci
// 第三行为m个整数a1, a2,...... am，其中ai表示完成第i天任务且地点不变的收益
// 第四行为m个整数b1, b2,...... bm，其中bi表示完成第i天的任务且地点改变的收益
// 0 <= k, ci <= n <= 30000
// 1 <= m <= 30000
// 0 <= ai, bi <= 10^9
    //9*10^8
// 输出描述 输出一个整数，表示小美合理完成任务能得到的最大收益

    public static int process(int i,int cur,int[] c,int[] a,int[] b,int m,int[][] dp){
        if(dp[i][cur]!=-1){
            return dp[i][cur];
        }
        //base case
        if(i==m){
            dp[i][cur]=0;
            return 0;
        }
        //1:不做当前任务
        int p1=process(i+1,cur,c,a,b,m,dp);
        //2:做当前任务
        int p2=0;
        if(cur==c[i]){
            p2=a[i]+process(i+1,cur,c,a,b,m,dp);
        }else{
            p2=b[i]+process(i+1,c[i],c,a,b,m,dp);
        }
        dp[i][cur]=Math.max(p1,p2);
        return dp[i][cur];
    }
    public static int dpWays(int n,int m,int k,int[] c,int[] a,int[] b){
        int[][] dp=new int[m+1][n];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return process(0,k,c,a,b,m,dp);
    }
    //最优解:线段树
    public static class SegmentTree {
        private int n;
        private int[] max;

        public SegmentTree(int N) {
            n = N;
            max = new int[(n + 1) << 2];
            Arrays.fill(max, Integer.MIN_VALUE);
        }

        public int max(int l, int r) {
            l++;
            r++;
            if (l > r) {
                return Integer.MIN_VALUE;
            }
            return max(l, r, 1, n, 1);
        }

        public void update(int i, int v) {
            i++;
            update(i, i, v, 1, n, 1);
        }

        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        private void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                max[rt] = Math.max(max[rt], C);
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
            int left = Integer.MIN_VALUE;
            int right = Integer.MIN_VALUE;
            if (L <= mid) {
                left = max(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = max(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }

    }
    //当前要做i号任务，之前做的所有任务使之停留
    //在每一个城市所获得的最大收益构成线段树
    public static int maxProfit(int n, int m, int k, int[] c, int[] a, int[] b) {
        SegmentTree st=new SegmentTree(n);
        st.update(k,0);
        int ans=0;
        for(int i=0;i<m;i++){
            int cur=c[i];
            //1:方案一:从其它位置赶过来
            int p1=Math.max(st.max(0,cur-1),st.max(cur+1,n-1))+b[i];
            //2:在本地做任务
            int p2=st.max(cur,cur)+a[i];
            int curAns=Math.max(p1,p2);
            ans=Math.max(ans,curAns);
            st.update(cur,curAns);
        }
        return ans;
    }
    //对数器测试
    public static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * v);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N=100;
        int M=100;
        int V=10000;
        int testTime=5000;
        for(int i=0;i<testTime;i++){
            int n=(int)(Math.random()*N)+1;
            int m=(int)(Math.random()*M)+1;
            int[] c=randomArray(m,n);
            int[] a=randomArray(m,V);
            int[] b=randomArray(m,V);
            int k=(int)(Math.random()*n);
            int ans1=dpWays(n,m,k,c,a,b);
            int ans2=maxProfit(n,m,k,c,a,b);
            if(ans1!=ans2){
                System.out.println("n=="+n+";m=="+m+";k=="+k);
                System.out.println(Arrays.toString(c));
                System.out.println(Arrays.toString(a));
                System.out.println(Arrays.toString(b));
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
