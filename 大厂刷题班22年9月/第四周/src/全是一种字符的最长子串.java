import java.util.Arrays;

public class 全是一种字符的最长子串 {
    // 来自字节
// 给定一个只由小写字母组成的字符串str，长度为N
// 给定一个只由0、1组成的数组arr，长度为N
// arr[i] == 0表示str中i位置的字符不许修改
// arr[i] == 1表示str中i位置的字符允许修改
// 给定一个正数m，表示在任意允许修改的位置
// 可以把该位置的字符变成a~z中的任何一个
// 可以修改m次
// 返回在最多修改m次的情况下，全是一种字符的最长子串是多长
// 1 <= N, M <= 10^5
// 所有字符都是小写

    // a c d e a b a c
    // 0 1 1 1 0 0 1 1   m=3
    //全是a时有最长子串的长度，全是b也有一种
    //那么枚举a-z 求最大值
    //时间复杂度O(N) + 子数组
    //双指针 滑动窗口 单调栈 一维动态规划
    //借帐表法

    public static int getMaxLen(String s,int[] arr,int m){
        if(s==null||s.equals("")||arr==null||arr.length==0||s.length()!=arr.length){
            return 0;
        }
        char[] str=s.toCharArray();
        int ans=0;
        int N=str.length;
        for(char aim = 'a'; aim <= 'z'; aim++){
            int R=0;
            for(int L=0;L<N;L++){
                if(L>R){
                    R=L;
                }
                while(R<N){//R向右扩到不能再扩的位置
                    if(str[R]==aim){
                        R++;
                    }else{
                        if(arr[R]==1&&m>0){
                            m--;
                            R++;
                        }else {
                            break;
                        }
                    }
                }
                //结算L位置的答案
                ans=Math.max(ans,R-L);
                //L即将移动，处理L滑出窗口的逻辑
                if(str[L]!=aim && arr[L]==1){
                    m++;
                }
            }
        }
        return ans;
    }
    //暴力解:全排列
    //对数器
    //现在你总共有m次修改机会，请问全部用完所能得出的最长相同字符串是多少
    public static int process(char[] str,int[] arr,int m){
        if(m==0){
            int N=str.length;
            int res=1;
            char pre=str[0];
            int curAns=1;
            for(int i=1;i<N;i++){
                if(str[i]==pre){
                    curAns++;
                }else{
                    res=Math.max(res,curAns);
                    pre=str[i];
                    curAns=1;
                }
            }
            res=Math.max(res,curAns);
            return res;
        }
        //现在还有修改机会
        int res=1;
        for(int i=0;i<str.length;i++){
            if(arr[i]==1){
                char tmp=str[i];
                for(char aim='a';aim<='z';aim++){
                    str[i]=aim;
                    res=Math.max(process(str,arr,m-1),res);
                }
                str[i]=tmp;
            }
        }
        return res;
    }
    public static char[] generateRandomStr(int Len){
        char[] str=new char[Len];
        int N=str.length;
        for(int i=0;i<N;i++){
            str[i]=(char) ('a'+(int)(Math.random()*26));
        }
        return str;
    }
    public static int[] generateRandomArr(int Len){
        int[] arr=new int[Len];
        int N=arr.length;
        for(int i=0;i<N;i++){
            arr[i]=Math.random()>0.4?1:0;
        }
        return arr;
    }
    public static void main(String[] args){
        int testTime=100;
        int MaxM=3;
        int maxLen=20;
        for(int i=0;i<testTime;i++){
            int N=(int)(Math.random()*maxLen)+1;
            char[] str=generateRandomStr(N);
            int[] arr=generateRandomArr(N);
            int m=(int)(Math.random()*MaxM)+1;
            int ans1=getMaxLen(String.valueOf(str),arr,m);
            int ans2=process(str,arr,m);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(str));
                System.out.println(Arrays.toString(arr));
                System.out.println("m===="+m);
                System.out.println("ans1==="+ans1);
                System.out.println("ans2==="+ans2);
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
