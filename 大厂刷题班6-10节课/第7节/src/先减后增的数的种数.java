import java.util.Arrays;
import java.util.HashSet;

public class 先减后增的数的种数 {
    /*
    * 给定一个数组arr，
    * arr中的值先递减后递增，
    * 请返回arr中不同数的种数
    *
    * */
    public static int getNums(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        //1:遍历找到先减后增的波谷
        int index=0;
        while(index<arr.length-1&&arr[index]>arr[index+1]){
            index++;
        }
        int sum=0;
        int L=0;
        int R=arr.length-1;
        while(L<=R){
            if(arr[L]==arr[R]){
                L=move(arr,L,false);
                R=move(arr,R,true);
            }
            else if((arr[L]-arr[index])>(arr[R]-arr[index])){
                L=move(arr,L,false);
            }
            else{
                R=move(arr,R,true);
            }
            sum++;
        }
        return sum;
    }
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
    /* 写一个暴力方法当作对数器  */
    public static int compareWays(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        HashSet<Integer> set=new HashSet<>();
        for(int n:arr){
            set.add(n);
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
    public static int[] merge(int maxLen,int maxValue){
        int[] left=generateRandomArray(maxLen,maxValue);
        int[] right=generateRandomArray(maxLen,maxValue);
        Arrays.sort(left);
        Arrays.sort(right);
        int N1=left.length;
        int N2=right.length;
        int[] merge=new int[N1+N2];
        int R=N1-1;
        int i;
        for(i=0;i<N1;i++){
            merge[i]=left[R--];
        }
        int L=0;
        for(;i<merge.length;i++){
            merge[i]=right[L++];
        }
        return merge;
    }

    public static void main(String[] args) {
        int testTime=100000;
        int maxValue=1000;
        int maxLen=100;
        for(int i=0;i<testTime;i++){
            int[] arr=merge(maxLen,maxValue);
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
