import java.util.Arrays;

public class 题库选题 {
    // 来自美团
// 有三个题库A、B、C，每个题库均有n道题目，且题目都是从1到n进行编号
// 每个题目都有一个难度值
// 题库A中第i个题目的难度为ai
// 题库B中第i个题目的难度为bi
// 题库C中第i个题目的难度为ci
// 小美准备组合出一套试题，试题共有三道题，
// 第一题来自题库A，第二题来自题库B，第三题来自题库C
// 试题要求题目难度递增，且梯度不能过大
// 具体地说，第二题的难度必须大于第一题的难度，但不能大于第一题难度的两倍
// 第三题的难度必须大于第二题的难度，但不能大于第二题难度的两倍
// 小美想知道在满足上述要求下，有多少种不同的题目组合
//（三道题目中只要存在一道题目不同，则两个题目组合就视为不同
// 输入描述 第一行一个正整数n, 表示每个题库的题目数量
// 第二行为n个正整数a1, a2,...... an，其中ai表示题库A中第i个题目的难度值
// 第三行为n个正整数b1, b2,...... bn，其中bi表示题库B中第i个题目的难度值
// 第四行为n个正整数c1, c2,...... cn，其中ci表示题库C中第i个题目的难度值
// 1 <= n <= 20000, 1 <= ai, bi, ci <= 10^9。

    public static int getAllWays(int[] a,int[] b,int[] c){
        int N=a.length;
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);
        int[] dp=new int[N];
        for(int i=0;i<N;i++){
            int[] range = getMatchRange(c, b[i]);
            if(range[1]>=range[0]){
                dp[i]=range[1]-range[0]+1;
            }
        }
        //前缀和数组
        int[] preSum=new int[N];
        for(int i=0;i<N;i++){
            preSum[i]=i>0?preSum[i-1]+dp[i]:dp[i];
        }
        int res=0;
        for(int i=0;i<N;i++){
            int[] range = getMatchRange(b, a[i]);
            if(range[1]>=range[0]){
                res+=(range[0]==0?preSum[range[1]]:preSum[range[1]]-preSum[range[0]-1]);
            }
        }
        return res;
    }

    public static int[] getMatchRange(int[] arr,int aim){
        int N=arr.length;
        int[] res=new int[]{N,-1};
        int L=0;
        int R=N-1;
        while (L<=R){
            int mid=(L+R)/2;
            if(arr[mid]>aim){
                res[0]=mid;
                R=mid-1;
            }else{
                L=mid+1;
            }
        }
        L=0;
        R=N-1;
        while (L<=R){
            int mid=(L+R)/2;
            if(arr[mid]<=2*aim){
                res[1]=mid;
                L=mid+1;
            }else{
                R=mid-1;
            }
        }
        return res;
    }

    //对数器
    public static int compareWays(int[] a,int[] b,int[] c){
        int N=a.length;
        int res=0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                for(int k=0;k<N;k++){
                    if(b[j]>a[i]&&b[j]<=2*a[i]&&c[k]>b[j]&&c[k]<=2*b[j]){
                        res++;
                    }
                }
            }
        }
        return res;
    }
    public static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * v);
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime=1000;
        int maxLen=100;
        int maxValue=1000;
        for(int i=0;i<testTime;i++){
            int N=(int)(Math.random()*maxLen)+1;
            int[] a=randomArray(N,maxValue);
            int[] b=randomArray(N,maxValue);
            int[] c=randomArray(N,maxValue);

            int ans1=getAllWays(a,b,c);
            int ans2=compareWays(a,b,c);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(a));
                System.out.println(Arrays.toString(b));
                System.out.println(Arrays.toString(c));
                System.out.println("ans1==="+ans1);
                System.out.println("ans2==="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
//        int[] a=new int[]{0, 0, 1, 4};
//        int[] b=new int[]{0, 1, 2, 4};
//        int[] c=new int[]{0, 1, 2, 3};
//        getAllWays(a,b,c);
    }
}
