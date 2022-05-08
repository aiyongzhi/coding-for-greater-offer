import java.util.Arrays;
import java.util.HashMap;

public class 盛水最多的容器 {
/*    给定一个长度为 n 的整数数组height。有n条垂线，第 i 条线的两个端点是(i, 0)和(i, height[i])。
    找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
    返回容器可以储存的最大水量。
    说明：你不能倾斜容器。
    链接：https://leetcode-cn.com/problems/container-with-most-water
    */

    /*暴力解: O(N^2)*/
    public static int compareWays(int[] height){
        if(height==null||height.length<2){
            return 0;
        }
        int max=Integer.MIN_VALUE;
        for(int L=0;L<height.length;L++){
            for(int R=L+1;R< height.length;R++){
                max=Math.max(max,Math.min(height[L],height[R])*(R-L));
            }
        }
        return max;
    }
    /*最优解双指针*/
    /*首尾双指针，谁小移动谁*/
    /*之所以谁小移动谁，是因为被移动的木板以它做高的最优情况就是此时宽度最大的时候*/
    /*比暴力方法优在: 每次只需要考虑局部最优*/
    /*
    * arr[L.....R]
    * 假设arr[L]<arr[R]
    * 为啥移动L，万一arr[R+1....]>=arr[L] 不是有更优的吗？
    * 这样确实会有更优但是如果 R+1... 有数比你大，那么这个数在结算时结果一定比你大
    * 相当于这个数为arr[L]做担保
    *
    * 如果arr[R+1....]<arr[L] 那么arr[L]就是结算到R最优
    *
    * */
    public static int maxArea(int[] height){
        if(height==null||height.length<2){
            return 0;
        }
        int N=height.length;
        int L=0;
        int R=N-1;
        int ans=Integer.MIN_VALUE;
        while(L<R){
            ans=Math.max(ans,Math.min(height[L],height[R])*(R-L));
            if(height[L]<height[R]){
                L++;
            }else if(height[R]<height[L]){
                R--;
            }else{
                L++;
                R--;
            }
        }
        return ans;
    }
    public static int[] generateRandomArray(int maxLen,int maxValue){
        int N=(int)(Math.random()*maxLen)+1;
        int[] arr=new int[N];
        for(int i=0;i<N;i++){
            arr[i]=(int)(Math.random()*maxValue)+1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime=100000;
        int maxLen=100;
        int maxValue=100;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            int ans1=compareWays(arr);
            int ans2=maxArea(arr);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(arr));
                System.out.println("ans1=="+ans1);
                System.out.println("ans2=="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }

}
