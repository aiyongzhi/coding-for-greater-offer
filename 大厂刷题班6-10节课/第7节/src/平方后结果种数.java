import java.util.Arrays;
import java.util.HashSet;

public class 平方后结果种数 {
    /*
    * 给定一个有序数组arr,其中可能有正数，负数和零
    * 如果将数组中的每个数都平方，返回数组中不同的数有多少种？
    * */
    //双指针:找相反数
    public static int getNums(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        //规则:双指针
        //arr[L]和arr[R]谁的值大就移动哪个指针 并计数1
        //                  如果相等，就同时移动 并计数1
        //但一定要保证 移动的时候要撸过所有相同的数
        int res=0;
        int L=0;
        int R=N-1;
        while(L<=R){
            if(Math.abs(arr[L])==Math.abs(arr[R])){
                //L移动
                L=move(arr,L,false);
                //R移动
                R=move(arr,R,true);
            }
            else if(Math.abs(arr[L])>Math.abs(arr[R])){
                L=move(arr,L,false);
            }
            else{
                R=move(arr,R,true);
            }
            res++;
        }
        return res;
    }
    //left==true 向左撸
    //      false 向右撸
    private static int move(int[] arr,int index,boolean left){
        int num=arr[index];
        int i=index;
        while(i>=0&&i<arr.length){
            if(arr[i]==num){
                //要撸
                if(left){
                    i--;
                }else{
                    i++;
                }
            }
            else {
                break;
            }
        }
        return i;
    }
    //暴力解当对数器
    public static int compareWays(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        HashSet<Integer> set=new HashSet<>();
        for (int j : arr) {
            set.add(j * j);
        }
        return set.size();
    }
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
        int maxValue=1000;
        int maxLen=1000;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            Arrays.sort(arr);
            int ans1=getNums(arr);
            int ans2=compareWays(arr);
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
