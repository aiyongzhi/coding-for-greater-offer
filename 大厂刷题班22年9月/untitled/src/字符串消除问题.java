import java.util.Stack;

public class 字符串消除问题 {
    /*
    * 来自阿里
    * 给定一个只由'a'和'b'两种字符组成的字符串str
    * str中的"ab"子串和"ba"子串均可以消除
    * 消除之后剩下的字符串组成在一起，可能出现可以继续消除的字符串
    * 你的任务是决定一种消除顺序，让str消除到尽可能最短
    * 返回str的这个最短的长度
    * */


    //贪心从左向右遇到"ab"或者"ba"子串就消除
    //这样求出来的一定是最优解
    //但是这有coding难读
    public static int minLen(String s){
        if(s==null||s.equals("")){
            return 0;
        }
        if(s.length()<2){
            return 1;
        }
        char[] str=s.toCharArray();
        int N=str.length;
        Stack<Character> stack=new Stack<>();
        for (char c : str) {
            if (!stack.isEmpty()) {
                char l = stack.peek();
                boolean hasA = c == 'a' || l == 'a';
                boolean hasB = c == 'b' || l == 'b';
                if (hasA && hasB) {
                    stack.pop();
                } else {
                    stack.add(c);
                }
            } else {
                stack.add(c);
            }
        }
        return stack.size();
    }

    //暴力递归
    //遇到每一个连接在一起的"ab"或"ba"都尝试消除
    public static int process(String s){
        if(s==null||s.length()==0){
            return 0;
        }
        if(s.length()==1){
            return 1;
        }
        int ans=Integer.MAX_VALUE;
        for(int i=1;i<s.length();i++){
            boolean hasA=s.charAt(i)=='a'||s.charAt(i-1)=='a';
            boolean hasB=s.charAt(i)=='b'||s.charAt(i-1)=='b';
            if(hasA&&hasB){
                String next=s.substring(0,i-1)+s.substring(i+1);
                ans=Math.min(ans,process(next));
            }
        }
        return ans==Integer.MAX_VALUE?s.length():ans;
    }
    public static String generateRandomStr(int maxLen){
        int N=(int)(Math.random()*maxLen)+1;
        char[] str=new char[N];
        for(int i=0;i<N;i++){
            str[i]=Math.random()>0.4?'a':'b';
        }
        return new String(str);
    }
    public static void main(String[] args) {
        int maxLen=10;
        int testTime=100000;
        for(int i=0;i<testTime;i++){
            String s=generateRandomStr(maxLen);
            int ans1=minLen(s);
            int ans2=process(s);
            if(ans1!=ans2){
                System.out.println(s);
                System.out.println("ans1==="+ans1);
                System.out.println("ans2==="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
