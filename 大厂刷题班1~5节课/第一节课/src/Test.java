import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class Test {
    //方法一:纯暴力枚举 时间复杂度O(N^3)
    public static String minCoverSubString1(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return null;
        }
        //1:生成字符串t的词频
        int[] tCount = new int[256];
        for (int i = 0; i < t.length(); i++) {
            tCount[t.charAt(i)]++;
        }
        //2:枚举出s中所有的子串
        int min = Integer.MAX_VALUE;
        int minL = s.length();//最小值的左边界
        for (int L = 0; L < s.length(); L++) {
            for (int R = L; R < s.length(); R++) {
                int[] subCount = new int[256];
                for (int i = L; i <= R; i++) {
                    subCount[s.charAt(i)]++;
                }
                if (isCover(subCount, tCount)) {
                    if (R - L + 1 < min) {
                        min = R - L + 1;
                        minL = L;
                    }
                }
            }
        }
        return min == Integer.MAX_VALUE ? null : s.substring(minL, minL + min);
    }

    //方法二:双指针优化 时间复杂度为O(N)
    //尝试以s串中的每一个字符作为子串的起点，求出所有起点中的最小值
    public static String minCoverSubString2(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return null;
        }
        //1:生成字符串t的词频
        int[] tCount = new int[256];
        int[] subCount = new int[256];//记录子串用的
        for (int i = 0; i < t.length(); i++) {
            tCount[t.charAt(i)]++;
        }
        int R = 0;
        int min = Integer.MAX_VALUE;
        int minL = s.length();
        for (int L = 0; L < s.length(); L++) {
            while (R < s.length() && !isCover(subCount, tCount)) {
                subCount[s.charAt(R)]++;
                R++;
            }
            if ((R - L < min && isCover(subCount, tCount))) {
                min = R - L;
                minL = L;
            }
            subCount[s.charAt(L)]--;
        }
        return min == Integer.MAX_VALUE ? null : s.substring(minL, minL + min);
    }

    //因为数组长度是有限的，时间复杂度为O(1)
    public static boolean isCover(int[] subCount, int[] tCount) {
        for (int i = 0; i < 256; i++) {
            if (subCount[i] - tCount[i] < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "ADEKGHDACKB";
        String t = "ABC";
        System.out.println(minCoverSubString1(s, t));
        System.out.println(minCoverSubString2(s, t));
    }
}
