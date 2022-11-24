import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class 数组中不同数字的个数 {
    /*
    * 现在给定一个数组arr
    * 数组arr满足先递减后递增
    * 请返回arr中不同数组的个数
    * 要求时间复杂度为O(N),且空间复杂度为O(1)
    *
    * */
    //[-1,-3,-5,2,4,5]
    public static int getNumber(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        int ans=0;
        int L=0;
        int R=N-1;
        while(L<=R){
            if(arr[L]==arr[R]){
                L=move(arr,L,true);
                R=move(arr,R,false);
            }else if(arr[L]>arr[R]){
                L=move(arr,L,true);
            }else{
                R=move(arr,R,false);
            }
            ans++;
        }
        return ans;
    }
    public static int move(int[] arr,int index,boolean L){
        int N=arr.length;
        int num=arr[index];
        if(L){
            while(index<N&&arr[index]==num){
                index++;
            }
        }else{
            while(index>=0&&arr[index]==num){
                index--;
            }
        }
        return index;
    }

    public static int number(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        HashSet<Integer> set=new HashSet<>();
        for (int j : arr) {
            set.add(j);
        }
        return set.size();
    }
    public static int[] generateRandomArray(int maxLen,int maxValue){
        int N=(int)(Math.random()*maxLen)+1;
        int[] arr=new int[N];
        for(int i=0;i<N;i++){
            arr[i]=(int)(Math.random()*maxValue)-(int)(Math.random()*maxValue);
        }
        return arr;
    }
    public static class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1;
        }
    }
    public static void main(String[] args) {
        int testTime=10000;
        int maxLen=100;
        int maxValue=100;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            int N=arr.length;
            int split=(int)(Math.random()*N);
            int ans1=getNumber(arr);
            int ans2=number(arr);
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
