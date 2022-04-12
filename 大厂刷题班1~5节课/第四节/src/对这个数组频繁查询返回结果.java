import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class 对这个数组频繁查询返回结果 {
    /*
    * 题意:给定一个数组arr,再给定一个查询表达式(0,3,2)表示在数组[0..3]返回上查询有多少个2
    * 注意:查询的次数很频繁，返回全部的查询结果
    * */

    /*
    * 这题因为查询的次数非常的频繁，因此一定要做预处理，加速后序的查询结果
    * */
    //把查询表达式抽象成一个类
    public static class search{
        public int start;
        public int end;
        public int target;

        public search(int start, int end, int target) {
            this.start = start;
            this.end = end;
            this.target = target;
        }

        @Override
        public String toString() {
            return "search{" +
                    "start=" + start +
                    ", end=" + end +
                    ", target=" + target +
                    '}';
        }
    }
    /*
    * 解法一:用HashMap记录每一个数字出现的位置，然后要查询某个具体的值的时候二分
    * */
    public static void doPre(int[] arr,HashMap<Integer, ArrayList<Integer>> map){
        for(int i=0;i<arr.length;i++){
            if(!map.containsKey(arr[i])){
                map.put(arr[i],new ArrayList<>());
            }
            map.get(arr[i]).add(i);
        }
    }
    public static int findMoreOrEqualMostLeft(ArrayList<Integer> arr,int k){
        int L=0;
        int R=arr.size()-1;
        int index=-1;
        while(L<=R){
            int mid=L+((R-L)>>1);
            if(arr.get(mid)>=k){
                index=mid;
                R=mid-1;
            }
            else{
                L=mid+1;
            }
        }
        return index;
    }
    public static int findLessOrEqualMostRight(ArrayList<Integer> arr,int k){
        int L=0;
        int R=arr.size()-1;
        int index=-1;
        while(L<=R){
            int mid=L+((R-L)>>1);
            if(arr.get(mid)<=k){
                index=mid;
                L=mid+1;
            }
            else{
                R=mid-1;
            }
        }
        return index;
    }
    public static int getCount(int start,int end,int target,HashMap<Integer, ArrayList<Integer>> map){
        ArrayList<Integer> arr=map.get(target);
        if(arr==null||arr.size()==0){
            return 0;
        }
        //找到>=start最左的位置
        int L=findMoreOrEqualMostLeft(arr,start);
        int R=findLessOrEqualMostRight(arr,end);
        if(L!=-1&&R!=-1){
            return R-L+1;
        }
        return 0;
    }
    public static int[] searchTargetCount(int[] arr,search[] searches){
        if(arr==null||arr.length==0||searches==null||searches.length==0){
            return null;
        }
        int[] res=new int[searches.length];
        //进行预处理,记录下每一个数
        HashMap<Integer, ArrayList<Integer>> map=new HashMap<>();
        doPre(arr,map);
        //遍历每一个操作，生成每一个操作的答案
        for(int i=0;i<res.length;i++){
            if(searches[i].start>searches[i].end){
                continue;
            }
            res[i]=getCount(searches[i].start,searches[i].end,searches[i].target,map);
        }
        return res;
    }

    //暴力解
    public static int[] compareWays(int[] arr,search[] searches){
        if(arr==null||arr.length==0||searches==null||searches.length==0){
            return null;
        }
        int[] res=new int[searches.length];
        for(int i=0;i<res.length;i++){
            int L= Math.max(searches[i].start, 0);
            int R=searches[i].end>=arr.length?arr.length-1:searches[i].end;
            if(L>R){
                continue;
            }
            int target=searches[i].target;
            for(int j=L;j<=R;j++){
                if(arr[j]==target){
                    res[i]++;
                }
            }
        }
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
    public static search[] getSearchArray(int maxLen,int maxValue){
        int len=(int)(Math.random()*maxLen)+1;
        search[] searches=new search[len];
        for(int i=0;i<searches.length;i++){
            int start=(int)(Math.random()*maxLen)+1;
            int end=(int)(Math.random()*maxLen)+1;
            int target=(int)(Math.random()*maxValue)+1;
            searches[i]=new search(start,end,target);
        }
        return searches;
    }
    public static boolean isEqual(int[] arr1,int[] arr2){
        if(arr1.length!=arr2.length){
            return false;
        }
        for(int i=0;i<arr1.length;i++){
            if(arr1[i]!=arr2[i]){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        int testTime=100000;
        int maxLen=100;
        int maxValue=100;
        for(int i=0;i<testTime;i++){
            int[] arr=generateRandomArray(maxLen,maxValue);
            search[] searches=getSearchArray(maxLen,maxValue);

            int[] ans1=searchTargetCount(arr,searches);
            int[] ans2=compareWays(arr,searches);
            if(!isEqual(ans1,ans2)){
                System.out.println(Arrays.toString(arr));
                System.out.println(Arrays.toString(searches));
                System.out.println("ans1== "+Arrays.toString(ans1));
                System.out.println("ans2== "+Arrays.toString(ans2));
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!!");
    }
}
