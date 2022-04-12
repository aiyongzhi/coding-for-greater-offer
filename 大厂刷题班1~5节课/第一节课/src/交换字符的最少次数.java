public class 交换字符的最少次数 {
    /*
     * 题目描述:
     * 给定一个字符数组arr,arr中只有'G'字符和'B'字符
     * 请只允许数组中相邻元素两两元素交换的情况下
     * 使得arr中所有G字符全在数组的左侧
     * 所有B字符全在数组的右侧
     * 请返回最少的交换次数
     * */

    /*
     * 最优解用到了小贪心
     * 从左向右的第i个G应该放在数组下标为(i-1)位置处
     * */

    //暴力方法 O(N^2)
    public static int bubbleSort(char[] arr) {
        int count = 0;
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] == 'B' && arr[j + 1] == 'G') {
                    count++;
                    swap(arr, j, j + 1);
                }
            }
        }
        return count;
    }

    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    //最优解 双指针 O(N) 贪心算法
    public static int getMinStep(char[] arr) {
        if(arr==null||arr.length==0){
            return 0;
        }
        int L=0;//L指针指向盛放G的位置
        int count=0;
        for(int R=0;R<arr.length;R++){
            if(arr[R]=='G'){
                count+=(R-L);
                L++;
            }
        }
        return count;
    }

    //对数器
    public static char[] getGenerateRandomArray(int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        char[] chs = new char[len];
        double p = Math.random();
        for (int i = 0; i < chs.length; i++) {
            if (Math.random() <= p) {
                chs[i] = 'G';
            } else {
                chs[i] = 'B';
            }
        }
        return chs;
    }

    public static char[] copyArray(char[] chs) {
        char[] res = new char[chs.length];
        System.arraycopy(chs, 0, res, 0, res.length);
        return res;
    }

    public static void main(String[] args) {
        int test = 100000;
        int maxLen = 100;
        for (int i = 0; i < test; i++) {
            char[] chs1 = getGenerateRandomArray(maxLen);
            char[] chs2 = copyArray(chs1);
            int ans1 = getMinStep(chs1);
            int ans2 = bubbleSort(chs2);
            if (ans1 != ans2) {
                System.out.println(chs1);
                System.out.println("ans1==" + ans1);
                System.out.println("ans2==" + ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
