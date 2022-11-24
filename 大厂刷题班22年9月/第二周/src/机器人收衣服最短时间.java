import java.util.Arrays;

public class 机器人收衣服最短时间 {
    // 来自美团笔试第四道
// 给定一个正数n, 表示从0位置到n-1位置每个位置放着1件衣服
// 从0位置到n-1位置不仅有衣服，每个位置还摆着1个机器人
// 给定两个长度为n的数组，powers和rates
// powers[i]表示i位置的机器人的启动电量
// rates[i]表示i位置的机器人收起1件衣服的时间
// 使用每个机器人只需要付出启动电量
// 当i位置的机器人收起i位置的衣服，它会继续尝试往右收起i+1位置衣服
// 如果i+1位置的衣服已经被其他机器人收了或者其他机器人正在收
// 这个机器人就会停机, 不再收衣服。
// 不过如果它不停机，它会同样以rates[i]的时间来收起这件i+1位置的衣服
// 也就是收衣服的时间为每个机器人的固定属性，当它收起i+1位置的衣服，
// 它会继续检查i+2位置...一直到它停机或者右边没有衣服可以收了
// 形象的来说，机器人会一直尝试往右边收衣服，收k件的话就耗费k * rates[i]的时间
// 但是当它遇见其他机器人工作的痕迹，就会认为后面的事情它不用管了，进入停机状态
// 你手里总共有电量b，准备在0时刻将所有想启动的机器人全部一起启动
// 过后不再启动新的机器人，并且启动机器人的电量之和不能大于b
// 返回在最佳选择下，假快多久能收完所有衣服
// 如果无论如何都收不完所有衣服，返回-1
// 给定数据: int n, int b, int[] powers, int[] rates
// 数据范围:
// powers长度 == rates长度 == n <= 1000
// 1 <= b <= 10^5
// 1 <= powers[i]、rates[i] <= 10^5
// 0号 : 10^5 * 10^3 -> 10^8
// log 10^8 * N^2  -> 27 * 10^6 -> 10^7
// 优化之后 : (log10^8) -> 27 * 1000 * 10

    //极限秀操作:最优解:二分+动态规划+线段树

    //我需要一个递归能够计算出给定时间内收完所有衣服所需要的最少电量

    public static int process(int i,int n,int time,int[] powers,int[] raws,int[] dp){
        if(dp[i]!=-1){
            return dp[i];
        }
        if(i==n){
            dp[i]=0;
            return 0;
        }
        int ans=Integer.MAX_VALUE;
        int usedTime=raws[i];
        int nextPower=Integer.MAX_VALUE;
        for(int j=i;j<n&&usedTime<=time;j++){
            nextPower=Math.min(nextPower,process(i+1,n,time,powers,raws,dp));
            usedTime+=raws[i];
        }
        ans=nextPower==Integer.MAX_VALUE?nextPower:nextPower+powers[i];
        dp[i]=ans;
        return ans;
    }
    public static int minTime(int[] powers,int[] raws,int n,int b){
        if(n==0){
            return 0;
        }
        int min=1;
        int max=raws[0]*n;
        int L=min;
        int R=max;
        int ans=Integer.MAX_VALUE;
        while(L<=R){
            int mid=((L+R)>>1);
            int[] dp=new int[n];
            Arrays.fill(dp,-1);
            if(process(0,n,mid,powers,raws,dp)<=b){
                ans=mid;
                R=mid-1;
            }else{
                L=mid+1;
            }
        }
        return ans;
    }
}
