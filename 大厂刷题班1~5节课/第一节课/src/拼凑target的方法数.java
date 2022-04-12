import java.util.HashMap;

public class 拼凑target的方法数 {
    /*
     * 给定一个数组arr，你可以选择在每一个数字之前添加+号或者-号
     * 但是必须所有数字都参与
     * 再给定一个target，请返回拼凑出target的方法数
     *
     * 业务优化算法的天花板级别的题目:六层优化，层层递进
     * */
    public static int getWays1(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process1(0, arr, target);
    }

    //暴力递归 从左向右的尝试模型
    //arr[i...]以后的所有数字可以任意选择加减,请返回拼凑出rest的可能性
    public static int process1(int i, int[] arr, int rest) {
        //base case
        if(i==arr.length){
            return rest==0?1:0;
        }
        //还有数可以决策
        return process1(i+1,arr,rest-arr[i])+
                process1(i+1,arr,rest+arr[i]);
    }

    //改成动态规化 rest的范围是[-max,max]
    //记忆化搜索
    public static int dpWays1(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        HashMap<String, Integer> dp = new HashMap<>();
        return process2(0, arr, target, dp);
    }

    public static int process2(int i, int[] arr, int rest, HashMap<String, Integer> dp) {
        String cur = i + "_" + rest;
        if (dp.containsKey(cur)) {
            return dp.get(cur);
        }
        if (i == arr.length) {
            dp.put(cur, rest == 0 ? 1 : 0);
            return dp.get(cur);
        }
        int ans = process2(i + 1, arr, rest + arr[i], dp) +
                process2(i + 1, arr, rest - arr[i], dp);
        dp.put(cur, ans);
        return dp.get(cur);
    }

    //大神优化 从业务逻辑下手优化
    /*
     * 1:可以将arr数组全变为正数,因为正数负数都无所谓
     * 2:求出arr的累加和sum,如果target>sum 则返回0种方法
     * 3:如果sum和target的奇偶性不同，返回0种方法
     * 4:把arr种所有选+的集合组成P，所有选减号的集合组成Q P-Q=target=> P=(target+sum)/2
     * 5:空间压缩技巧
     * */
    //转化为了背包问题
    public static int findTargetSumWays1(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int sum = 0;
        //求出数组的和，并把数组中的元素全部变为正数
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                arr[i] = -arr[i];
            }
            sum += arr[i];
        }
        return ((target>sum)||((sum&1)^(target&1))!=0)?0:process3(arr, 0, (sum + target) >> 1);
    }
    /* 递归含义:arr[0...index-1]已经决策好了，可以任意决策[index..]后的数，请返回凑出rest的可能性 */
    public static int process3(int[] arr, int index, int rest) {
        if(index==arr.length){
            return rest==0?1:0;
        }
        //还有数可以决策

        //让这个数进背包
        int p1=0;
        if(arr[index]<=rest){
            p1=process3(arr,index+1,rest-arr[index]);
        }
        //不选择这个数
        int p2=process3(arr,index+1,rest);
        return p1+p2;
    }

    //改成动态规划
    public static int findTargetSumWays2(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                arr[i] = -arr[i];
            }
            sum += arr[i];
        }
        return (sum < target || ((sum & 1) ^ (target & 1)) != 0 ? 0 : process3(arr, 0, (sum + target) >> 1));
    }
    //数组压缩
    public static int dpWays(int[] arr, int bag) {
        int N=arr.length;
        int[]dp=new int[bag+1];
        dp[0]=1;
        for(int index=N-1;index>=0;index--){
            for(int rest=bag;rest>=0;rest--){
                if(arr[index]<=rest){
                    dp[rest]+=dp[rest-arr[index]];
                }
            }
        }
        return dp[bag];
    }
}
