import java.util.Arrays;

public class 构造长度为size的达标数组 {
    /*
    * 对于一个数组arr，我们定义如下的规则
    * 对于任意的,i< k <j,均有arr[i]+arr[j] != 2*arr[k]
    * 我们把这样的数组称为达标数组，给定任意一个size
    * 请构造出长度为size的达标数组
    * */

    /*
    * 分而治之，分治法的经典问题
    * 解法:反证法
    * 如果我们构造出一个长度为N的达标数组arr,那么我们能够轻易的构造出一个全是偶数的达标数组
    * 和一个全是奇数的达标数组，即将其*2   和*2+1
    * */

    /*大神分治的写法*/
    public static int[] get2NArray(int[] arr){
        int[] res=new int[2*arr.length];
        int N=arr.length;
        for(int i=0;i<res.length;i++){
            if(i<arr.length){
                res[i]=2*arr[i];
            }
            else{
                res[i]=2*arr[i%N]+1;
            }
        }
        return res;
    }
    public static int[] getArray(int size){
        if(size<=0){
            return null;
        }
        int[] arr={1};
        int len=1;
        /*
        * 找到一个长度大于等于size且离size最近的达标数组
        * */
        while(len<size){
            arr=get2NArray(arr);
            len*=2;
        }
        int index=0;
        int[] res=new int[size];
        for(int i=0;i<res.length;i++){
            res[i]=arr[index++];
        }
        return res;
    }

    //写一个方法来验证，是否每一个 左+右 != 2*中
    public static boolean isOk(int[] arr){
        //尝试每一个中心
        int N=arr.length;
        for(int mid=1;mid<N-1;mid++){
            for(int L=0;L<mid;L++){
                for(int R=mid+1;R<N;R++){
                    if(arr[mid]*2 ==arr[L]+arr[R]){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        int testTime=100000;
        int maxSize=100;
        for(int i=0;i<testTime;i++){
            int size=(int)(Math.random()*maxSize)+1;
            int[] arr=getArray(size);
            if(!isOk(arr)){
                System.out.println(Arrays.toString(arr));
                System.out.println("size==="+size);
                System.out.println("Fuck!");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
