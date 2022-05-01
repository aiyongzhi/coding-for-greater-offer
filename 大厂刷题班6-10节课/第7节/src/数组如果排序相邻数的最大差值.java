import java.util.Arrays;

public class 数组如果排序相邻数的最大差值 {
    /*
    * 给定一个整数无序数组，请你求出如果排序后相邻元素的最大差值的绝对值
    * 不能采用任何排序算法，也就是说在不排序时，采取一种策略推理出排序后的答案
    * 要求时间复杂度为O(N)
    * */
    public static int maxDistance(int[] arr){
        if(arr==null||arr.length<2){
            return 0;
        }
        //找出数组中的最大值和最小值
        int max=Integer.MIN_VALUE;
        int min=Integer.MAX_VALUE;
        for(int num:arr){
            max=Math.max(max,num);
            min=Math.min(min,num);
        }
        if(max==min){
            return 0;
        }
        //用数组模拟桶
        int len=arr.length;
        int[] mins=new int[len+1];
        int[] maxs=new int[len+1];
        boolean[] hasNumber=new boolean[len+1];
        //让所有数进桶
        for(int num:arr){
            int id=getId(num,min,max,len);
            mins[id]=hasNumber[id]?Math.min(mins[id],num):num;
            maxs[id]=hasNumber[id]?Math.max(maxs[id],num):num;
            hasNumber[id]=true;
        }
        //遍历所有桶收集答案
        int ans=0;
        int lastMax=maxs[0];//储存上一个桶的最大值
        for(int i=1;i< len+1;i++){
            if(hasNumber[i]){
                ans=Math.max(ans,mins[i]-lastMax);
                lastMax=maxs[i];
            }
        }
        return ans;
    }
    //一种算法确定一个数具体在哪个桶中
    public static int getId(int num,int min,int max,int len){
        return (int)(((num-min)*len)/(max-min));
    }
    //写一个暴力解当对数器
    public static int compareWay(int[] arr){
        if(arr==null||arr.length<2){
            return 0;
        }
        Arrays.sort(arr);
        int lastNum=arr[0];
        int ans=0;
        for(int i=1;i<arr.length;i++){
            ans=Math.max(arr[i]-lastNum,ans);
            lastNum=arr[i];
        }
        return ans;
    }
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
        int maxLen=1000;
        int maxValue=1000;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            int ans1=maxDistance(arr);
            int ans2=compareWay(arr);
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
