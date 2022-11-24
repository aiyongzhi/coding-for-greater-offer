import java.util.Arrays;
import java.util.HashMap;

public class 偶数字符子串的最大长度 {
/*
* 字符串str,全部由小写字母构成
* 其子串t如果满足其中所有字母出现次数均为偶数
* 就称子串t为达标子串
* 请求出str中的最长达标子串
* */
    //O(N)
    public static int maxLen(char[] str){
        if(str==null||str.length==0){
            return 0;
        }
        int N=str.length;
        //只记录一个奇偶状态位每一个值的最早出现的下标
        HashMap<Integer, Integer> valueIndexMap=new HashMap<>();
        int max=0;
        int status=0;
        valueIndexMap.put(0,-1);
        for(int i=0;i<N;i++){
            status^=(1<<(str[i]-'a'));
            //0 1 2 3
            if(valueIndexMap.containsKey(status)) {
                int first = valueIndexMap.get(status);
                max = Math.max(i - first, max);
            }else {
                valueIndexMap.put(status,i);
            }
        }
        return max;
    }
    //暴力方法:对数器
    //O(N^3)
    public static int maxLen1(char[] str){
        if(str==null||str.length==0){
            return 0;
        }
        int max=0;
        int N=str.length;
        for(int L=0;L<N;L++){
            for(int R=L;R<N;R++){
                int status=0;
                for(int k=L;k<=R;k++){
                    status^=(1<<(str[k]-'a'));
                }
                if(status==0){
                    max=Math.max(max,R-L+1);
                }
            }
        }
        return max;
    }
    public static char[] generateRandomArray(int maxLen){
        int N=(int)(Math.random()*maxLen)+1;
        char[] str=new char[N];
        for(int i=0;i<N;i++){
            str[i]='a'+25;
        }
        return str;
    }

    public static void main(String[] args) {
        int testTime=1000;
        int maxLen=100;
        for(int i=0;i<testTime;i++){
            char[] str=generateRandomArray(maxLen);
            int ans1=maxLen(str);
            int ans2=maxLen1(str);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(str));
                System.out.println("ans1=="+ans1);
                System.out.println("ans2=="+ans2);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
