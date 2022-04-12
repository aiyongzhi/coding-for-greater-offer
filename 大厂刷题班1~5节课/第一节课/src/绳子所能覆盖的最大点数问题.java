import java.lang.reflect.Array;
import java.util.Arrays;

public class 绳子所能覆盖的最大点数问题 {
    /*
     * 题目描述:
     * 给定一个有序数组arr，代表坐落在x轴上的点
     * 给定一个正数k代表绳子的长度
     * 返回绳子最多压中几个点?
     * 即使绳子的边缘盖住点也计算在内
     * */
    /*
     * 左神讲的窗口模型
     * */
    public static int maxCoverPoint1(int[] arr, int K) {
        //双指针 时间复杂度为O(N)
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }
        int max=1;
        int L=0;
        int R=0;
        for(;L<arr.length;L++){
            while(R<arr.length&&arr[R]-arr[L]<=K){
                R++;
            }
            //R指向的是匹配失败的位置
            max=Math.max(max,R-L);
        }
        return max;
    }
    //稍微暴力点的解法就是二分
    public static int maxCoverPoint2(int[] arr,int K){
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }
        int max=1;
        for(int L=0;L<arr.length;L++){
            int R=binarySearch(arr,L,K);
            max=Math.max(max,R-L+1);
        }
        return max;
    }
    public static int binarySearch(int[] arr,int i,int K){
        int L=i;
        int R=arr.length-1;
        int target=arr[i]+K;
        int ans=-1;
        while(L<=R){
            //1:找中点
            int mid=L+((R-L)>>1);
            if(arr[mid]<=target){
                ans=mid;
                L=mid+1;
            }
            else{
                R=mid-1;
            }
        }
        return ans;
    }

    //跑一个对数器
    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }
    public static void main(String[] args) {
        int testTime=100000;
        int maxLen=100;
        int maxValue=100;
        int maxK=20;
        for(int i=0;i<testTime;i++){
            int K=(int)(Math.random()*maxK)+1;
            int[] arr=generateRandomArray(maxLen,maxValue);
            Arrays.sort(arr);
            int ans1=maxCoverPoint1(arr,K);
            int ans2=maxCoverPoint2(arr,K);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(arr));
                System.out.println("K=="+K);
                System.out.println("ans1=="+ans1);
                System.out.println("ans2=="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
