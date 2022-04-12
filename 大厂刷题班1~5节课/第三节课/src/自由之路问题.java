import java.util.ArrayList;
import java.util.HashMap;

public class 自由之路问题 {
    /*
    * 力扣链接:https://leetcode-cn.com/problems/freedom-trail/
    * 解法：暴力递归和记忆化搜索
    *
    * */
    public static int getMinStep1(String ring,String key){
        return process(0,0,ring.toCharArray(),key.toCharArray())+key.length();
    }
    //暴力递归
    /*
    * key[0...keyIndex-1]都已经拼凑好了，不需要我们关心
    * keyIndex及其以后的字符你可以任意选择如何转动拼凑成，请返回最少的转动步数
    *
    * */
    public static int process(int preStrIndex,int keyIndex,char[] ring,char[] key){
        //base case
        if (keyIndex == key.length) {
            return 0;
        }
        //还有字符需要拼
        int ans=Integer.MAX_VALUE;
        for(int i=0;i<ring.length;i++){
            if(ring[i]==key[keyIndex]){
                int step=move(ring,preStrIndex,i);
                ans=Math.min(ans,step+process(i,keyIndex+1,ring,key));
            }
        }
        return ans;
    }
    public static int move(char[] ring,int i,int j){
        int N=ring.length;
        return Math.min(Math.abs(i-j),Math.min(i,j)+N-Math.max(i,j));
    }

    //记忆化搜索 由于preStrIndex是转动出来的，情况极为不确定，因此不能改成记忆化搜索
    public static int getMinStep2(String ring,String key){
        int[][] dp=new int[ring.length()][key.length()+1];
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                dp[i][j]=-1;
            }
        }
        return dpWays(0,0,ring.toCharArray(),key.toCharArray(),dp)+key.length();
    }
public static int dpWays(int preStrIndex,int keyIndex,char[] ring,char[] key,int[][] dp){
        if(dp[preStrIndex][keyIndex]!=-1){
            return dp[preStrIndex][keyIndex];
        }
        //base case
        int ans=Integer.MAX_VALUE;
        if (keyIndex == key.length) {
            ans=0;
        }
        else{
        //还有字符需要拼
        for (int i = 0; i < ring.length; i++) {
            if (ring[i] == key[keyIndex]) {
                int step = move(ring, preStrIndex, i);
                ans = Math.min(ans, step + dpWays(i, keyIndex + 1, ring, key,dp));
            }
        }
        }
        dp[preStrIndex][keyIndex]=ans;
        return ans;
    }
    /*
    * 用预处理简化查找操作
    * */
    public static int getMinStep3(String ring,String key){
        int[][] dp=new int[ring.length()][key.length()+1];
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                dp[i][j]=-1;
            }
        }
        //用一个哈希表来记录key中的每一个字符在ring中出现的位置
        HashMap<Character, ArrayList<Integer>> map=new HashMap<>();
        for(int i=0;i<ring.length();i++){
            if(!map.containsKey(ring.charAt(i))){
                map.put(ring.charAt(i),new ArrayList<>());
            }
            map.get(ring.charAt(i)).add(i);
        }
        return dpWays(0,0,ring.toCharArray(),key.toCharArray(),dp,map)+key.length();
    }
    public static int dpWays(int preStrIndex,int keyIndex,char[] ring,char[] key,int[][] dp,HashMap<Character, ArrayList<Integer>> map){
        if(dp[preStrIndex][keyIndex]!=-1){
            return dp[preStrIndex][keyIndex];
        }
        //base case
        int ans=Integer.MAX_VALUE;
        if (keyIndex == key.length) {
            ans=0;
        }
        else{
            ArrayList<Integer> cur=map.get(key[keyIndex]);
            for(int i:cur){
                int step=move(ring,preStrIndex,i);
                ans=Math.min(ans,step+dpWays(i,keyIndex+1,ring,key,dp,map));
            }
        }
        dp[preStrIndex][keyIndex]=ans;
        return ans;
    }
}
