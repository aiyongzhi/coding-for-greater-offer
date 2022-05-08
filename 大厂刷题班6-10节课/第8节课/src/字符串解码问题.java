import java.util.LinkedList;

public class 字符串解码问题 {
    //力扣：394 链接:https://leetcode-cn.com/problems/decode-string/
    public static String decodeString(String s){
        return f(s.toCharArray(),0).ans;
    }
    /*
    * 递归含义:
    * str[i....] 一直撸到有括号']'或者结尾
    * 把答案返回，并返回执行到的下标位置
    * */
    public static class Data{
        public String ans;
        public int end;
        public Data(String ans,int end){
            this.ans=ans;
            this.end=end;
        }
    }
    public static Data f(char[] str,int i){
        LinkedList<String> queue=new LinkedList<>();
        StringBuilder sb=new StringBuilder();
        Data bra=null;
        while(i<str.length&&str[i]!=']'){
            if(str[i]=='['){
                bra=f(str,i+1);
                queue.addLast(bra.ans);
                i=bra.end+1;
            }else{
                bra=getStr(str,i,(str[i]>='0'&&str[i]<='9'));
                i=bra.end;
                queue.addLast(bra.ans);
            }
        }
        return new Data(getAns(queue),i);
    }
    public static String getAns(LinkedList<String> queue){
        String cur=null;
        int times=1;
        StringBuilder ans=new StringBuilder();
        while(!queue.isEmpty()){
            cur=queue.pollFirst();
            char c=cur.charAt(0);
            if(c>='0'&&c<='9'){
                times=Integer.parseInt(cur);
            }else{
                for(int i=0;i<times;i++){
                    ans.append(cur);
                }
                times=1;
            }
        }
        return ans.toString();
    }
    public static Data getStr(char[] str,int i,boolean isNum){
        StringBuilder sb=new StringBuilder();
        while(i<str.length&& (str[i] >= '0' && str[i] <= '9') == isNum){
            if(str[i]=='['||str[i]==']'){
                break;
            }
            sb.append(str[i++]);
        }
        return new Data(sb.toString(),i);
    }

    public static void main(String[] args) {
        String s="2[abc]3[cd]ef";
        System.out.println(decodeString(s));
    }
}
