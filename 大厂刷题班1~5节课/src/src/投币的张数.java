public class 投币的张数 {
    //贩卖机只支持100,50,10三种面值的货币，现在张三有100元a张
    //50元b张,10元c张 现在要购买m瓶可乐，可乐的单价是x元
    //投钱和找零都遵循，先用大钱再用小钱的原则
    //请返回最终投币的张数
    public static int getTheSumOfZhang(int m, int a, int b, int c, int x) {
        //为了方便使用用张数数组和钱数数组来储存
        int[] qians = {100, 50, 10};
        int[] zhangs = {a, b, c};
        int puts = 0;//投入币的张数
        int preRestZhang = 0;
        int preRestQian = 0;
        for (int i = 0; i < 3 && m != 0; i++) {
            //用当前面值的货币搞定第一瓶可乐的时候要算上之前的剩余

            //当前面值买第一瓶可乐需要的张数
            int curBuyFirstColaZhang = (x - preRestQian + qians[i] - 1) / qians[i];
            if (zhangs[i] >= curBuyFirstColaZhang) {//能够买到第一瓶可乐
                m--;
                zhangs[i] -= curBuyFirstColaZhang;
                puts += preRestZhang + curBuyFirstColaZhang;
                getRest(zhangs, qians, i + 1, preRestQian + curBuyFirstColaZhang * qians[i] - x, 1);
            } else {//买不到第一瓶可乐
                preRestZhang += zhangs[i];
                preRestQian += zhangs[i] * qians[i];
                continue;
            }
            //只用当前货币买剩下的可乐

            //买一瓶可乐需要当前面值货币的张数
            int buyOneColaZhang = (x + qians[i] - 1) / qians[i];
            //只用当前货币解决的总可乐数目
            int buyColas = Math.min(zhangs[i] / buyOneColaZhang, m);
            m -= buyColas;
            //买一瓶可乐需要找零多少
            int buyOneRest = buyOneColaZhang * qians[i] - x;
            //当前货币尽能用

            //找零
            getRest(zhangs, qians, i + 1, buyOneRest, buyColas);
            zhangs[i] -= buyOneColaZhang * buyColas;
            puts += buyColas * buyOneColaZhang;

            preRestZhang = zhangs[i];
            preRestQian = zhangs[i] * qians[i];
        }
        return m == 0 ? puts : -1;
    }

    //函数意义:用当前货币及其以后所有的货币，进行找零，把钱数加上
    public static void getRest(int[] zhangs, int[] qians, int i, int oneTimeRest, int times) {
        for (; i < 3; i++) {
            zhangs[i] += (oneTimeRest / qians[i]) * times;
            oneTimeRest %= qians[i];
        }
    }

    public static void main(String[] args) {
        System.out.println(getTheSumOfZhang(3, 0, 1, 1, 20));
    }
}
