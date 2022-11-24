import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

public class 排序游戏 {
    // 来自百度
// 二狗买了一些小兵玩具，和大胖一起玩
// 一共有n个小兵，这n个小兵拍成一列
// 第i个小兵战斗力为hi，然后他们两个开始对小兵进行排列
// 一共进行m次操作，二狗每次操作选择一个数k，将前k个小兵战斗力从小到大排列
// 大胖每次操作选择一个数k，将前k个小兵战斗力从大到小排列
// 问所有操作结束后，排列顺序什么样
// 给定一个长度为n的数组arr，表示每个小兵的战斗力
// 给定一个长度为m的数组op,
// op[i] = { k , 0 }, 表示对前k个士兵执行从小到大的操作
// op[i] = { k , 1 }, 表示对前k个士兵执行从大到小的操作
// 返回数组ans，表示最终的排列
// 1 <= n, m <= 2 * 10^5
// - 10^9 <= arr[i] <= + 10^9

    public static class Number{
        public int value;
        public int index;

        public Number(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
    public static class MyComparator implements Comparator<Number>{
        @Override
        public int compare(Number o1, Number o2) {
            if(o1.value!=o2.value){
                return o1.value-o2.value;
            }else{
                return o1.index-o2.index;
            }
        }
    }

    public static int[] sortGame(int[] arr,int[][] op){
        int n=arr.length;
        int m=op.length;
        //利用单调栈进行数据的过滤
        int[] stack=new int[m];
        int r=0;//栈中下次放入数据的下标
        for(int i=0;i<m;i++){
            while(r!=0&&op[stack[r-1]][0]<=op[i][0]){
                r--;
            }
            stack[r++]=i;
        }
        int[] ans=new int[n];
        int ansI=n-1;
        for(;ansI>=op[stack[0]][0];ansI--){
            ans[ansI]=arr[ansI];
        }
        TreeSet<Number> set=new TreeSet<>(new MyComparator());
        //初始化set
        for(int i=0;i<op[stack[0]][0];i++){
            set.add(new Number(arr[i],i));
        }
        int l=0;
        while(l!=r){
            int[] cur=op[stack[l]];
            if(l+1<r){
                int[] next=op[stack[l+1]];
                if(cur[1]==0){
                    for(int i=0;i<cur[0]-next[0];i++){
                        ans[ansI--]=set.pollLast().value;
                    }
                }else{
                    for(int i=0;i<cur[0]-next[0];i++){
                        ans[ansI--]=set.pollFirst().value;
                    }
                }
                cur=next;
            }else{
                if(cur[1]==0){
                    for(int i=0;i<cur[0];i++){
                        ans[ansI--]=set.pollLast().value;
                    }
                }else{
                    for(int i=0;i<cur[0];i++){
                        ans[ansI--]=set.pollFirst().value;
                    }
                }
            }
            l++;
        }
        return ans;
    }
    // 为了测试
    public static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * v) + 1;
        }
        return ans;
    }

    // 为了测试
    public static int[][] randomOp(int m, int n) {
        int[][] ans = new int[m][2];
        for (int i = 0; i < m; i++) {
            ans[i][0] = (int) (Math.random() * (n + 1));
            ans[i][1] = Math.random() < 0.5 ? 0 : 1;
        }
        return ans;
    }

    // 为了测试
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
    public static int[] game1(int[] arr, int[][] op) {
        int n = arr.length;
        Integer[] help = new Integer[n];
        for (int i = 0; i < n; i++) {
            help[i] = arr[i];
        }
        for (int[] o : op) {
            if (o[1] == 0) {
                Arrays.sort(help, 0, o[0], (a, b) -> a - b);
            } else {
                Arrays.sort(help, 0, o[0], (a, b) -> b - a);
            }
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = help[i];
        }
        return ans;
    }
    // 为了测试
    public static void main(String[] args) {
        int N = 100;
        int M = 100;
        int V = 200;
        int testTimes = 5000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int n = (int) (Math.random() * N) + 1;
            int m = (int) (Math.random() * M) + 1;
            int[] arr = randomArray(n, V);
            int[][] op = randomOp(m, n);
            int[] ans1 = game1(arr, op);
            int[] ans2 = sortGame(arr, op);
            if (!isEqual(ans1, ans2)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

}
