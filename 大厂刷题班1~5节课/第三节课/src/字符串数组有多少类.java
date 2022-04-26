import java.util.HashSet;

public class 字符串数组有多少类 {
    /*
    * 字节跳动 笔试题
    * 题目:给定一个字符串数组，其中每一个字符串都只有小写字母(a-z)构成，26
    * 规定:两个字符串如果字符种类完全一样，就称为同一类
    * 请返回字符串数组中有多少类字符串
    * */

    /*
    * 技巧:遇到字符种类有限制的时候，可以用bit位来统计词频
    *
    * 最优解:用一个32位int型整数来记录一个字符串中字符的种类,0位表示a,1位表示b等....
    * 如果该字符出现了，就在对应的位置填上1
    *
    * 优化点:1.比用HashMap和数组统计字符种类要省空间
    * 2.如果种类不同，最后的int数值一定不同
    * 3.我们可以通过整型数值最终有多少个不同的数来判断种类数
    * */
    public static int theKindOfString(String[] strs){
        if(strs==null||strs.length==0){
            return 0;
        }
        HashSet<Integer> set=new HashSet<>();
        for(int i=0;i<strs.length;i++){
            int n=0;
            char[] str=strs[i].toCharArray();
            for(int j=0;j<str.length;j++){
                n|=(1<<(str[i]-'a'));
            }
            set.add(n);
        }
        return set.size();
    }

}
