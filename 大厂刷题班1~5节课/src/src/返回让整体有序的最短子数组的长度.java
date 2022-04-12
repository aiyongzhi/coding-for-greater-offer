import java.util.Arrays;

public class 返回让整体有序的最短子数组的长度 {
    /*
     * 对于一个无序数组arr,arr的所有子数组中，如果我们对其中某一个子数组进行排序后，
     * 能够使得整体有序，我们就称这个子数组为达标。
     * 请返回arr的所有子数组中达标子数组的长度最短是多少
     *
     *  */

    /*
     * 思路:
     * 1): 从左向右进行一次遍历
     *     1. 左边最大值>当前数,则当前数画❌
     *     2.         <=当前数，则当前数画✔
     *     则最右边的x 就是最短子数组的右边界
     * 2): 从右向左进行一次遍历
     *     1. 右边最小值>当前数，当前数画✔
     *     2.         <=当前数，当前数画❌
     *     则最左边的x 就是最短子数组的左边界
     * */
    //时间复杂度为O(N) 空间复杂度O(1)
    public static int getMinLength(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int noMaxIndex = -1;
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < max) {
                noMaxIndex = i;
            } else {
                max = Math.max(max, arr[i]);
            }
        }
        if (noMaxIndex == -1) {//说明是整体升序，则完全不需要排序
            return 0;
        }
        int noMinIndex = -1;
        int min = arr[arr.length - 1];
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > min) {
                noMinIndex = i;
            } else {
                min = Math.min(min, arr[i]);
            }
        }
        return noMaxIndex - noMinIndex + 1;
    }

    /* 暴力解:枚举所有子数组，排序后看整体是否有序 */
    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }

    public static boolean isIncrease(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static int compareWays(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int L = 0; L < arr.length; L++) {
            for (int R = L; R < arr.length; R++) {
                int[] copyArr = copyArray(arr);
                Arrays.sort(copyArr, L, R + 1);
                if (isIncrease(copyArr)) {
                    min = Math.min(min, R - L + 1);
                }
            }
        }
        return min == 1 ? 0 : min;
    }


    //写对数器
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int maxLen = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxLen, maxValue);
            int[] arr2 = copyArray(arr1);

            int ans1 = getMinLength(arr1);
            int ans2 = compareWays(arr2);
            if (ans1 != ans2) {
                System.out.println(Arrays.toString(arr1));
                System.out.println("ans1==" + ans1);
                System.out.println("ans2==" + ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
