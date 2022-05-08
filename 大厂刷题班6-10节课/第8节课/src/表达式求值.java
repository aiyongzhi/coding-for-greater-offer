import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

public class 表达式求值 {
    /*
    * 题目:给定一个字符串表示一个表达式
    * 字符串中的字符只能为：数字字符，运算符，括号
    * 括号可以多层嵌套
    * 保证所有的字符串都是有效的运算表达式
    * 给定你一个表达式，请你算出结果
    * */

    /*
    * 括号嵌套(某种符号嵌套问题)的通解
    * 准备一个递归函数f(i...)从i开始撸到结束或者')'
    *
    * 返回两个结果bra[0]:这个过程的结果
    * bra[1]:算到哪里结束的
    * */
    public static int[] f(char[] str,int i){
        LinkedList<String> queue=new LinkedList<>();
        int cur=0;
        int[] bra=new int[2];
        while(i<str.length&&str[i]!=')'){
            //1:当前这个字符是数字
            if(str[i]>='0'&&str[i]<='9'){
                cur=cur*10+(str[i++]-'0');
            }
            else if(str[i]!='('){//2:当前遇到的是运算符
                addNum(queue,cur);
                queue.addLast(String.valueOf(str[i++]));
                cur=0;
            }else{//3:当前遇到的是'('
                bra=f(str,i+1);
                cur=bra[0];
                i=bra[1]+1;
            }
        }
        addNum(queue,cur);
        return new int[]{getAns(queue),i};
    }
    private static int getAns(LinkedList<String> queue){
        boolean isAdd=true;
        String cur=null;
        int ans=0;
        while(!queue.isEmpty()){
            cur=queue.pollFirst();
            if(cur.equals("+")){
                isAdd=true;
            }else if(cur.equals("-")){
                isAdd=false;
            }else{
                int num=Integer.parseInt(cur);
                ans+=isAdd?num:(-num);
            }
        }
        return ans;
    }
    private static void addNum(LinkedList<String> queue,int num){
        if(!queue.isEmpty()){
            int cur=0;
            String operate=queue.pollLast();
            if(operate.equals("+")||operate.equals("-")){
                queue.addLast(operate);
            }else{
                cur=Integer.parseInt(Objects.requireNonNull(queue.pollLast()));
                num=operate.equals("*")?num*cur:(cur/num);
            }
        }
        queue.add(String.valueOf(num));
    }
    public static int calculate(String s){
        if(s==null||s.equals("")){
            throw new RuntimeException("表达式不能为空");
        }
        return f(s.toCharArray(),0)[0];
    }
    public static void main(String[] args) {
        String s="4+5-2*6-4/2-3";
        System.out.println(calculate(s));
    }

}
