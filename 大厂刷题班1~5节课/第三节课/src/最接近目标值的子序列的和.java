import java.util.Arrays;

public class 最接近目标值的子序列的和 {
    /*
    * 题目:给定一个整数数组arr,和一个目标值target
    * 你需要从arr的全部子序列中选出一个子序列和最接近target的子序列
    * 最终返回target和这个达标子序列和的差值的绝对值
    * 即，求出Math.abs(target-sum)的最小值
    * */

    /*
    * 题目数据量的限制:
    * 1 <= nums.length <= 40
      -10^7 <= nums[i] <= 10^7
       -10^9 <= goal <= 10^9
    * */
    /*
    * 看到数据量就能够排除暴力方法
    * 看到数组长度不大就可以联想到分治
    * */
    public static int[] l=new int[1<<20];
    public static int[] r=new int[1<<20];
    public static int minAbsDifference(int[] nums, int goal) {
        int le=process(nums,0,nums.length>>1,0,l,0);
        int re=process(nums,nums.length>>1,nums.length,0,r,0);
        Arrays.sort(l,0,--le);
        Arrays.sort(r,0,--re);
        int ans=Integer.MAX_VALUE;
        for(int i=0;i<=le;i++){
            int rest=goal-l[i];
            while(re>0&&Math.abs(rest-r[re-1])<Math.abs(rest-r[re])){
                re--;
            }
            ans=Math.min(ans,Math.abs(goal-l[i]-r[re]));
        }
        return ans;
    }
    /*  递归含义:给定一个数组nums，请返回数组中start-end返回内所有子序列的累加和,并返回填了多少个数  */
    public static int process(int[] nums,int index,int end,int sum,int[] arr,int fill){
        //base case
        if(index==end){
            arr[fill++]=sum;
        }
        //每一个位置可以选择要或者不要两种选择
        else{
            fill=process(nums,index+1,end,sum,arr,fill);
            fill=process(nums,index+1,end,sum+nums[index],arr,fill);
        }
        return fill;
    }
}
