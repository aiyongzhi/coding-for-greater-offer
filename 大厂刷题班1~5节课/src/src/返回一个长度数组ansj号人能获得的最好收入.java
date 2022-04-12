import java.util.*;

public class 返回一个长度数组ansj号人能获得的最好收入 {
    //大厂刷题班第二节课 第一题:https://www.mashibing.com/study?courseNo=465&sectionNo=34944&systemId=21
    //题目要点:堆结构的应用
    public static class Work {
        public int hard;
        public int money;

        public Work(int hard, int money) {
            this.hard = hard;
            this.money = money;
        }
    }

    public static class minComparator implements Comparator<Work> {
        @Override
        public int compare(Work o1, Work o2) {
            return o1.hard - o2.hard;
        }
    }

    public static int[] getMaxIncomes1(int[] hard, int[] money, int[] ability) {
        if (hard == null || money == null || hard.length != money.length || ability == null) {
            return null;
        }
        int[] ans = new int[ability.length];
        //初始化work数组
        Work[] works = new Work[hard.length];
        for (int i = 0; i < hard.length; i++) {
            works[i] = new Work(hard[i], money[i]);
        }
        //准备一个堆
        PriorityQueue<Work> minQueue = new PriorityQueue<>(new minComparator());
        //将所有工作加入堆中
        Collections.addAll(minQueue, works);
        //将能力数组排序
        Arrays.sort(ability);

        //堆的规则
        //现在轮到i号人来应聘,将堆中所有他能cover的住的工作全弹出并选择出最大值
        //和他前面的那个人求最大值就是他应得的工资
        for (int i = 0; i < ability.length; i++) {
            int max = Integer.MIN_VALUE;
            while (!minQueue.isEmpty() && minQueue.peek().hard <= ability[i]) {
                max = Math.max(max, minQueue.poll().money);
            }
            ans[i] = Math.max(i - 1 >= 0 ? ans[i - 1] : 0, max);
        }
        return ans;
    }

    //最优解 贪心算法,和有序表
    //在难度相同的工作组中只保留赚钱数最高的
    //难度高工资低的工作一定不会被选中
    //将剩余工作放入有序表中处理
    public static class workComparator implements Comparator<Work> {
        @Override
        public int compare(Work o1, Work o2) {
            return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
        }
    }

    public static int[] getMaxIncomes(int[] hard, int[] money, int[] ability) {
        if (hard == null || money == null || hard.length != money.length || ability == null) {
            return null;
        }
        int[] ans = new int[ability.length];
        //生成工作数组
        Work[] works = new Work[hard.length];
        for (int i = 0; i < hard.length; i++) {
            works[i] = new Work(hard[i], money[i]);
        }
        Arrays.sort(works, new workComparator());
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Work prev = works[0];
        map.put(works[0].hard, works[0].money);
        for (int i = 1; i < works.length; i++) {
            if (works[i].hard != prev.hard && works[i].money > prev.money) {
                prev = works[i];
                map.put(works[i].hard, works[i].money);
            }
        }
        for (int i = 0; i < ability.length; i++) {
            Integer key = map.floorKey(ability[i]);
            ans[i] = key == null ? 0 : map.get(key);
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}
