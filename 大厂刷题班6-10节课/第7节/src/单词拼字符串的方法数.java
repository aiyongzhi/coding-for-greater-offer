import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

public class 单词拼字符串的方法数 {
    /*
    * 假设所有字符都是小写字母
    * 大字符串是str
    * arr是去重的单词表，其中每个单词都不是空字符串，且可以使用任意次
    * 请返回用arr单词表中的单词拼凑出str的方法数
    *
    * */

    /*
    * 暴力递归:含义
    * words数组中的单词任意使用次数
    * 请返回拼凑出str[index....]的方法数
    *
    * 并返回方法数
    *
    * */
    public static int process(int index,String[] words,String str){
        //base case
        if(index==str.length()){
            return 1;
        }
        int res=0;
        for(String word:words){
            if(str.startsWith(word,index)){
                res+=process(index+word.length(),words,str);
            }
        }
        return res;
    }
    public static int getWays(String[] words,String str){
        if(words==null||words.length==0||str==null||str.equals("")){
            return 0;
        }
        return process(0,words,str);
    }
    //由暴力递归到动态规划
    //时间复杂度 words数组长度为M，str长度为N words单词最大长度K
    //O(N*M*K)  简化来说就是O(N^3)
    public static int dpWays1(String[] words,String str){
        if(words==null||words.length==0||str==null||str.equals("")){
            return 0;
        }
        int N=str.length();
        int[] dp=new int[N+1];
        dp[N]=1;
        for(int i=N-1;i>=0;i--){
            int res=0;
            for(String word:words){
                if(str.startsWith(word,i)){
                    res+=dp[i+word.length()];
                }
            }
            dp[i]=res;
        }
        return dp[0];
    }
    //写对数器
    public static String generateRandomStr(int maxLen){
        int N=(int)(Math.random()*maxLen)+1;
        char[] str=new char[N];
        for(int i=0;i<str.length;i++){
            int randomNum=(int)(Math.random()*26);
            str[i]=(char)('a' + randomNum);
        }
        return String.valueOf(str);
    }
    public static String[] generateRandomStrArray(int maxLen,int maxStrLen){
        int N=(int)(Math.random()*maxLen)+1;
        String[] strs=new String[N];
        for(int i=0;i<strs.length;i++){
            strs[i]=generateRandomStr(maxStrLen);
        }
        HashSet<String> set = new HashSet<>(Arrays.asList(strs));
        return set.toArray(new String[0]);
    }
    //用前缀树优化
    public static class Node{
        public boolean isEnd;
        public Node[] next;
        public Node(){
            isEnd=false;
            next=new Node[26];
        }
    }
    public static class WordsTire{
        public Node root;
        public WordsTire(){
            root=new Node();
        }
        public void add(String word){
            Node cur=root;
            for(int i=0;i<word.length();i++){
                int path=word.charAt(i)-'a';
                cur.next[path]=cur.next[path]==null?new Node():cur.next[path];
                cur=cur.next[path];
            }
            cur.isEnd=true;
        }
    }
    public static int dpWays2(String[] words,String str){
        if(words==null||words.length==0||str==null||str.equals("")){
            return 0;
        }
        //1:生成前缀树
        WordsTire tire=new WordsTire();
        for(String word:words){
            tire.add(word);
        }
        int N=str.length();
        int[] dp=new int[N+1];
        dp[N]=1;
        for(int i=N-1;i>=0;i--){
            Node cur=tire.root;
            for(int end=i;end<N;end++){
                int path=str.charAt(end)-'a';
                if(cur.next[path]==null){
                    break;
                }
                cur=cur.next[path];
                if(cur.isEnd){
                    dp[i]+=dp[end+1];
                }
            }
        }
        return dp[0];
    }
    public static void main(String[] args) {
        int testTime=100000;
        int maxLen=100;
        int maxStrLen=10;
        int maxStr=1000;
        for(int i=0;i<testTime;i++){
            String[] words=generateRandomStrArray(maxLen,maxStrLen);
            String str=generateRandomStr(maxStr);
            int ans1=getWays(words,str);
            int ans2=dpWays2(words,str);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(words));
                System.out.println(str);
                System.out.println("ans1=="+ans1);
                System.out.println("asn2=="+ans2);
                System.out.println("Fuck！");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
