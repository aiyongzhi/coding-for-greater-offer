public class 子数组的最大累加和 {
    /*
    * 给定一个数组arr，请返回数组arr中子数组的最大累加和是多少
    * 力扣链接:https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/
    * */
    /*
    * 最优解:动态规划
    * 子数组问题一定要联想到以每一个位置做结尾，往左能扩出来的最大累加和是多少
    * 在每一个位置都两种决策
    *  一:不向左扩
    *  二:向左扩扩充
    * */
    public static int maxSubArray(int[] nums){
        if(nums==null||nums.length==0){
            return 0;
        }
        int pre=nums[0];
        int cur=0;
        int max=pre;
        for(int i=1;i<nums.length;i++){
            cur=nums[i];
            if(pre>=0){
                cur+=pre;
            }
            pre=cur;
            max=Math.max(max,cur);
        }
        return max;
    }

}
