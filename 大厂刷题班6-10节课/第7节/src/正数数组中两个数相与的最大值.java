import javax.management.loading.MLet;
import java.util.Arrays;

public class 正数数组中两个数相与的最大值 {
    /*
    * 题目给定一个正数数组，数组的长度大于1
    * 请返回这个数组中任意两个数相与的最大结果
    *
    * */
    /*
    * 暴力解:对数器
    *  */
    public static int compareWays(int[] arr){
        if(arr.length<=1){
            return -1;
        }
        int ans=0;
        for(int j=1;j<arr.length;j++){
            for(int i=0;i<j;i++){
                ans=Math.max(ans,arr[i]&arr[j]);
            }
        }
        return ans;
    }
    /* 最优解
    * 淘汰法
    * 从高位往低位尝试
    *
    * 在当前存活区中遍历所有数，当前位是1的留在存活区，当前位是0的去复活区，
    * 等待机会复活
    * 如果最终存活的人数小于2，则所有复活区的数复活，继续向低重复此过程
    * 如果最终存活区中人数等于2，即这两个数异或就是答案，直接返回结果
    * 如果最终存活区中的人数大于2，那么复活区中所有数全部淘汰，当前这一位的结果就是1
    *   */
    public static int maxAndValue(int[] arr){
        if(arr.length<2){
            return -1;
        }
        int ans=0;
        int M=arr.length;//存活区的长度
        for(int bit=30;bit>=0;bit--){
            int tmp= M;
            int i=0;
            //1:遍历存活区中的元素
            while(i<M){
                if(((arr[i]>>bit)&1)==0){
                    swap(arr,i,--M);
                }
                else{
                    i++;
                }
            }
            //2:查看存活区还剩余的人数
            if(M==2){
                return arr[0]&arr[1];
            }
            if(M<2){
                M=tmp;
            }else{
                ans|=(1<<bit);
            }
        }
        return ans;
    }
    public static void swap(int[] arr,int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
    //对数器测试结果
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime=100000;
        int maxLen=100;
        int maxValue=10000;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            int ans1=compareWays(arr);
            int ans2=maxAndValue(arr);
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
