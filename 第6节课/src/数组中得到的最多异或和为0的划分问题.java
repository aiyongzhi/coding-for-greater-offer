import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class 数组中得到的最多异或和为0的划分问题 {
    /*
    * 对于一个数组，数组中所有元素的异或结果，称为这个数组的异或和
    * 现在给定你一个数组arr,你可以任意切分，将数组分成不相隔的子数组
    * 总有一种最优策略，能够使得所有切成的子数组中异或和为0的子数组最多
    * 请返回最优策略下，异或和为0子数组最多是多少？
    * */

    /* 暴力解:暴力递归 指数级算法 */
    /* 递归含义:i位置决定上一次决策是否结尾 */
    public static int process(int[] eor,int index,ArrayList<Integer> splits){
        int ans=0;
        //base case
        if(index==eor.length){
            splits.add(eor.length);
            ans=eorZeroParts(eor,splits);
            splits.remove(splits.size()-1);
        }
        else{
            //index位置不终结上一段
            int p1=process(eor,index+1,splits);
            //index位置终结上一段
            splits.add(index);
            int p2=process(eor,index+1,splits);
            splits.remove(splits.size()-1);
            ans=Math.max(p1,p2);
        }
        return ans;
    }

    private static int eorZeroParts(int[] eor, ArrayList<Integer> splits) {
        int ans=0;
        int L=0;
        for(int end:splits){
            if((eor[end-1]^(L==0?0:eor[L-1]))==0){
                ans++;
            }
            L=end;
        }
        return ans;
    }

    public static int getMaxXOrEquals0SubArray1(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        if(arr.length==1){
            return arr[0]==0?1:0;
        }
        int[] eor=new int[arr.length];
        eor[0]=arr[0];
        for(int i=1;i<eor.length;i++){
            eor[i]=eor[i-1]^arr[i];
        }
        return process(eor,1,new ArrayList<>());
    }
    //最优解:时间复杂度为O(N)
    //dp[i]表示[0...i]上的最优划分
    //如果最后一个划分的子数组异或和为0
    //dp[pre]+1 pre上一次这[0...i]这个前缀异或和出现的位置
    //如果最后一个划分的子数组异或和不为0
    //dp[i-1]
    public static int getMaxXOrEquals0SubArray2(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        int[] dp=new int[arr.length];
        //key:前缀和 value:表示该前缀和上一次出现的位置
        HashMap<Integer, Integer> map=new HashMap<>();
        map.put(0,-1);
        int sum=0;
        for(int i=0;i<dp.length;i++){
            sum^=arr[i];
            if(i>0){
                dp[i]=dp[i-1];
            }
            if(map.containsKey(sum)){
                int pre=map.get(sum);
                dp[i]=Math.max(dp[i],pre==-1?1:(dp[pre]+1));
            }
            map.put(sum,i);
        }
        return dp[arr.length-1];
    }
    //对数器比较一下
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue)-(int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime=100000;
        int maxLen=5;
        int maxValue=5;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            int ans1 = getMaxXOrEquals0SubArray1(arr);
            int ans2=getMaxXOrEquals0SubArray2(arr);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(arr));
                System.out.println("ans1==="+ans1);
                System.out.println("ans2==="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
