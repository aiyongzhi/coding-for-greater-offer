import java.util.Arrays;
import java.util.HashSet;

public class 平方之后结果数 {
    /*
    * 给定一个有序数组arr,其中有正数，负数，0
    * 如果将这个数组arr中的每一个值全平方
    * 请返回平方操作后arr数组中有多少个不同的值
    * */

    //[-3,-1,0,4,6,8]
    //数据量猜解法:这题时间复杂度最好为O(N)
    //双指针一定要规定好L 指针 和R 指针的移动规则
    //|arr[L]|==|arr[R]| L++ R--
    //arr[R]>|arr[L]| 谁绝对值大谁移动
    //L和R每次移动时要移动出所有相同的值
    public static int differNumber(int[] arr){
        if(arr==null||arr.length==0){
            return 0;
        }
        int N=arr.length;
        int L=0;
        int R=N-1;
        int ans=0;
        while(L<=R){
            if(arr[L]+arr[R]==0){
                L=move(arr,L,true);
                R=move(arr,R,false);
                ans++;
            }else if(Math.abs(arr[L])>Math.abs(arr[R])){
                L=move(arr,L,true);
                ans++;
            }else if(Math.abs(arr[L])<Math.abs(arr[R])){
                R=move(arr,R,false);
                ans++;
            }else{
                L=move(arr,L,true);
                R=move(arr,R,false);
                ans++;
            }
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
    //暴力解当作对数器
    public static int number(int[] arr){
        int N=arr.length;
        HashSet<Integer> set=new HashSet<>();
        for(int i=0;i<N;i++){
            arr[i]*=arr[i];
            set.add(arr[i]);
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

    public static void main(String[] args) {
        int testTime=10000;
        int maxLen=100;
        int maxValue=100;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            Arrays.sort(arr);

            int ans1=differNumber(arr);
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
