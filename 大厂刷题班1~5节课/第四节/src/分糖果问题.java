public class 分糖果问题 {
    //力扣链接:https://leetcode-cn.com/problems/candy/
    /*
    * 题目:现在有N个孩子站成一排，给你一个数组ratings,表示N个孩子的评分
    * 你需要按照以下的要求，给这些孩子分发糖果
    *      1.每个孩子至少要分到一颗糖果
    *      2.相邻的孩子直接分数高的分到的糖果数要比分数低的孩子多
    * 请你返回满足这个规则下给孩子分发的最少糖果总数是多少?
    * */

    /*
    * 贪心+预处理数组
    * 生成一个左数组，表示只兼顾每一个孩子如果比左孩子大的情况
    * 生成一个右数组，表示只兼顾每一个孩子如果比右孩子大的情况
    * 然后由贪心可知，给每个孩子分发的具体糖果数是其中的较大值
    * */
    public static int minCandy1(int[] ratings){
        if(ratings==null||ratings.length==0){
            return 0;
        }
        int[] left=new int[ratings.length];
        int[] right=new int[ratings.length];
        for(int i=0;i<left.length;i++){
            left[i]=i>0&&ratings[i]>ratings[i-1]?left[i-1]+1:1;
        }
        for(int i=right.length-1;i>=0;i--){
            right[i]=i<right.length-1&&ratings[i]>ratings[i+1]?right[i+1]+1:1;
        }
        int minCandy=0;
        for(int i=0;i<right.length;i++){
            minCandy+=Math.max(left[i],right[i]);
        }
        return minCandy;
    }
    /*
    * 进阶问题:分糖果的规则在上面的题目中加一条
    * 相邻孩子如果分数一样，分到的糖果数也要一样
    * 请返回在这样的情况下的最少总糖果数
    * */
    public static int minCandy2(int[] ratings){
        if(ratings==null||ratings.length==0){
            return 0;
        }
        /*
        * 如果比左边孩子大则糖果数加1
        * 如果相同则继承左边的糖果数
        * 如果小则时间归1
        * */
        int[] left=new int[ratings.length];
        int[] right=new int[ratings.length];
        for(int i=0;i<left.length;i++){
            left[i]=i>0?(ratings[i]>ratings[i-1]?left[i-1]+1:(ratings[i]==ratings[i-1
                    ]?left[i-1]:1)):1;
        }
        for(int i=right.length-1;i>=0;i--){
            if(i==ratings.length-1){
                right[i]=1;
                continue;
            }
            if(ratings[i]>ratings[i+1]){
                right[i]=right[i+1]+1;
            }
            else if(ratings[i]==ratings[i+1]){
                right[i]=right[i+1];
            }
            else{
                right[i]=1;
            }
        }
        int minCandy=0;
        for(int i=0;i<left.length;i++){
            minCandy+=Math.max(left[i],right[i]);
        }
        return minCandy;
    }
}
