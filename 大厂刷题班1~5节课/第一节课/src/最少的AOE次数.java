import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Arrays;

public class 最少的AOE次数 {
    /*
     * 题目描述:
     * 给定两个非负整数数组x和hp,长度都是N，再给定一个正数range,
     * x是有序的，x[i]表示i号怪物在x轴上的位置;hp[i]:表示i号怪物的血量
     * range表示法师如果站在x[i]号位置,可以打出AOE伤害
     * 即:[x[i]-range,x[i]+range] 范围内的数都会受到一滴血的伤害
     * 请返回法师将所有怪物都击杀所需要的最少AOE攻击次数。
     *
     * */
    /*
     * 难度:Hard
     * */

    //暴力解:每一次都尝试在任意位置进行AOE输出
    public static int minAOE1(int[] x, int[] hp, int range) {
        //生成法师在i号怪物处发动AOE技能，左侧最左打到的怪物编号，和右侧最右打到的怪物编号
        int N = x.length;
        int[] coverLeft = new int[N];
        int[] coverRight = new int[N];
        int L = 0;
        int R = 0;
        for (int i = 0; i < N; i++) {
            while (x[i] - x[L] > range) {
                L++;
            }
            while (R < N && x[R] - x[i] <= range) {
                R++;
            }

            coverLeft[i] = L;
            coverRight[i] = R - 1;
        }
        return process(hp, coverLeft, coverRight);
    }

    //暴力递归 返回将怪物全部击杀的最少AOE攻击次数
    public static int process(int[] hp, int[] coverLeft, int[] coverRight) {
        int N = hp.length;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            for (int f = coverLeft[i]; f <= coverRight[i]; f++) {
                if (hp[f] > 0) {
                    int[] next = aoe(hp, coverLeft[i], coverRight[i]);
                    ans = Math.min(ans, 1 + process(next, coverLeft, coverRight));
                    break;
                }
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    public static int[] aoe(int[] hp, int L, int R) {
        int N = hp.length;
        int[] next = new int[N];
        System.arraycopy(hp, 0, next, 0, N);
        //L..R号怪物被AOE命中
        for (int i = L; i <= R; i++) {
            next[i] -= (next[i] > 0 ? 1 : 0);
        }
        return next;
    }


    //贪心算法优化
    public static int minAOE2(int[] x, int[] hp, int rang) {
        int ans = 0;
        int N = x.length;
        for (int i = 0; i < N; i++) {
            if (hp[i] > 0) {
                int firePos = i;//法师攻击的位置
                while (firePos < N && (x[firePos] - x[i] <= rang)) {
                    firePos++;
                }
                ans += hp[i];
                aoe(x, hp, i, firePos - 1, rang);
            }
        }
        return ans;
    }

    public static void aoe(int[] x, int[] hp, int L, int firePos, int range) {
        int N = x.length;
        int R = firePos;
        while (R < N && (x[R] - x[firePos] <= range)) {
            R++;
        }
        int minus = hp[L];
        for (int i = L; i < R; i++) {
            hp[i] = Math.max(0, hp[i] - minus);
        }
    }

    //线段树再优化


    //对数器
    public static int[] getGenerateRandomArray(int maxValue, int N) {
        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            res[i] = (int) (Math.random() * maxValue) + 1;
        }
        return res;
    }

    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        System.arraycopy(arr, 0, res, 0, res.length);
        return res;
    }

    public static void main(String[] args) {
        int test = 10;
        int maxLen = 5;
        int maxValue = 10;
        int maxRange = 10;
        for (int i = 0; i < test; i++) {
            int N = (int) (Math.random() * maxLen) + 1;
            int rang = (int) (Math.random() * maxRange) + 1;
            int[] x = getGenerateRandomArray(maxValue, N);
            int[] hp1 = getGenerateRandomArray(maxValue, N);
            Arrays.sort(x);
            int[] hp2 = copyArray(hp1);
            //暴力方法
            int ans1 = minAOE1(x, hp1, rang);
            int ans2 = minAOE2(x, hp2, rang);
            if (ans1 != ans2) {
                System.out.println(Arrays.toString(x));
                System.out.println(Arrays.toString(hp1));
                System.out.println("range==" + rang);
                System.out.println("ans1==" + ans1);
                System.out.println("ans2==" + ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
