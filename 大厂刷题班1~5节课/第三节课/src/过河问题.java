import java.util.Arrays;

public class 过河问题 {
    /*
    * 给定一个正数数组arr，代表每一个人的体重。
    * 再给定一个正数limit代表船的载重，认为所有船的载重均相同
    * 每艘船最多载两个人且不能超过载重,请返回最终让所有人都过河需要的最少船数
    *
    * */

    /*
    * 思路:
    * 贪心点一:只要让每艘船尽可能载两个人，实在不行再考虑一艘船载一人的情况
    * 贪心点二:对于一艘船载两个人，只会有两种情况
    * 1:两人的体重均小于船的载重/2
    * 2:一个人的体重小于等于limit/2 另外一个人的体重大于等于limit/2
    *
    * 安排的优先顺序是
    * 首先安排两个人共同乘坐一艘船,在这个情况下优先安排情况2，其次安排情况1
    * 再安排一人乘船的情况
    * */
    public static int findLessOrEqualsKMostRight(int[] arr,int target){
        int L=0;
        int R=arr.length-1;
        int index=-1;
        while(L<=R){
            int mid=L+((R-L)>>1);
            if(arr[mid]<=target){
                index=mid;
                L=mid+1;
            }
            else{
                R=mid-1;
            }
        }
        return index;
    }
    public static int minBoat1(int[] arr, int limit){
        if(arr==null||arr.length==0){
            return 0;
        }
        if(limit<=0){
            return Integer.MAX_VALUE;
        }
        //排序
        Arrays.sort(arr);
        if(arr[arr.length-1]>limit){
            return Integer.MAX_VALUE;
        }
        //一:找到<=limit/2的位置
        int mid=findLessOrEqualsKMostRight(arr,limit>>1);
        int L=mid;
        int R=L+1;
        int pre=mid;
        int match=0;//记录体重在mid左右两侧的人的匹配成功的对数
        while(L>=0&&R<arr.length){
            if(arr[L]+arr[R]<=limit){
                while(R+1<arr.length&&arr[R+1]+arr[L]<=limit){
                    R++;
                }
                int cur=Math.min(L+1,R-pre);
                match+=cur;
                pre=R;
                L-=cur;
            }
            else{
                L--;
            }
        }
        return match+(int)Math.ceil((mid+1-match)/2.0)+arr.length-(mid+1)-match;
    }

    //可以用首尾双指针来写
    public static int minBoat2(int[] arr,int limit){
        if(arr==null||arr.length==0){
            return 0;
        }
        if(limit<=0){
            return Integer.MAX_VALUE;
        }
        Arrays.sort(arr);
        int L=0;
        int R=0;
        int ans=0;
        int sum=0;
        while(L<=R){
            sum=(L==R?arr[L]:(arr[L]+arr[R]));
            if(sum>limit){
                R--;
            }
            else{
                L++;
                R--;
            }
            ans++;
        }
        return ans;
    }
    //写一个暴力方法做对数器 暴力方法全排列
    //先枚举出所有能够两两组合的情况，取个最小，剩下的直接单人单船
    public static int compareWays(int[] arr,int limit){
        //判断数据是否合法
        if(arr==null||arr.length==0){
            return 0;
        }
        if(limit<=0){
            return Integer.MAX_VALUE;
        }
        int max=0;
        for(int num:arr){
            max=Math.max(max,num);
        }
        if(max>limit){
            return Integer.MAX_VALUE;
        }
        return process(arr,limit);
    }
    public static int process(int[] arr,int limit){
        //base case
        if(arr==null||arr.length==0){
            return 0;
        }
        if(arr.length<2){
            return 1;
        }
        //还有两个以上的人还没有安排过河
        int ans=Integer.MAX_VALUE;
        for(int L=0;L<arr.length;L++){
            for(int R=L+1;R<arr.length;R++){
                if(arr[L]+arr[R]<=limit){
                    int[] next=getNext(arr,L,R);
                    ans=Math.min(ans,1+process(next,limit));
                }
            }
        }
        return ans==Integer.MAX_VALUE?arr.length:ans;
    }
    public static int[] getNext(int[] arr,int L,int R){
        int[] next=new int[arr.length-2];
        int index=0;
        for(int i=0;i<arr.length;i++){
            if(i!=L&&i!=R){
                next[index++]=arr[i];
            }
        }
        return next;
    }
    //对数器
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
        int maxLen=20;
        int maxValue=100;
        int maxLimit=15;
        for(int i=0;i<testTime;i++){
            int limit=(int)(Math.random()*maxLimit)+1;
            int[] arr1=generateRandomArray(maxLen,maxValue);
            int[] arr2=copyArray(arr1);

            int ans1= minBoat1(arr1,limit);
            int ans2=compareWays(arr2,limit);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(arr1));
                System.out.println("limit=="+limit);
                System.out.println("asn1=="+ans1);
                System.out.println("ans2=="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
