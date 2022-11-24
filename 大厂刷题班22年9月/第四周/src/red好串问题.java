public class red好串问题 {
    /*
    * 给定一个全是由'r','e','d'三种字符构成的字符串str
    * 如果str只有一个长度大于等于2的回文子串，则称这个
    * str字符串是好串，给定一个数字n表示字符串str的长度
    * 请返回str的所有排列中，好串的个数
    * 1<=n<=10^9
    * */

    //通过数据量猜解法可知，这题应该是可以用公式来解决的
    //暴力解，打标找规律


    //
    public static int process(int i,int n,String path){
        if(i==n){
            return isOk(path.toCharArray())?1:0;
        }
        return process(i+1,n,path+"r")+process(i+1,n,path+"e")+process(i+1,n,path+"d");
    }
    public static boolean isOk(char[] str){
        int N=str.length;
        boolean isMeet=false;
        for(int L=0;L<N;L++){
            for(int R=L;R<N;R++){
                if(isA(str,L,R)){
                    if(R-L+1>=2){
                        if(isMeet){
                            return false;
                        }else{
                            isMeet=true;
                        }
                    }
                }
            }
        }
        return isMeet;
    }
    public static boolean isA(char[] str,int L,int R){
        while(L<=R){
            if(str[L]==str[R]){
                L++;
                R--;
            }else{
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        for(int i=1;i<=11;i++){
            int ans=process(0,i,"");
            System.out.println(i+"====="+ans);
            System.out.println(getOkStrNumber(i));
        }
    }
    public static int getOkStrNumber(int n){
        if(n==1){
            return 0;
        }
        if(n==2){
            return 3;
        }
        if(n==3){
            return 18;
        }
        if(n==4){
            return 30;
        }
        return 6*(n+1);
    }
}
