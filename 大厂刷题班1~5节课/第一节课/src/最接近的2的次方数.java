public class 最接近的2的次方数 {
    /*
     * 给定一个非负整数num
     * 如何不用循环语句
     * 求出>=num,且最接近num的2的某次方数
     *
     * */
    /*
     * 循环的方式很好想，于是用循环的语句做对数器
     * */
    public static int compareWay(int num) {
        int x = 1;
        while (x < num) {
            x *= 2;
        }
        return x;
    }

    //不用循环的大神解法 HashMap源码中有的解法
    //重点二的某次方二进制序列中只有一个1
    //时间复杂度为O(1)
    public static int near2Power(int n) {
        //1:兼容n本身就是2的某次方
        n--;

        //将n的二进制最高位的1的右边全部变成1
        n|=(n>>1);
        n|=(n>>2);
        n|=(n>>4);
        n|=(n>>8);
        n|=(n>>16);

        //返回答案
        return n<0?1:(n+1);
    }

    //写对数器
    public static void main(String[] args) {
        int test = 100000;
        int max = 1000;
        for (int i = 0; i < test; i++) {
            int random_n = (int) (Math.random() * max);
            int ans1 = near2Power(random_n);
            int ans2 = compareWay(random_n);
            if (ans1 != ans2) {
                System.out.println(random_n);
                System.out.println("ans1==" + ans1);
                System.out.println("ans2==" + ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
