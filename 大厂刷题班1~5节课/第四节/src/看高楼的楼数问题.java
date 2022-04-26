import java.util.Arrays;
import java.util.PriorityQueue;

public class 看高楼的楼数问题 {
    /*
    * 给你一个数组height,表示一排楼的高度，现在规定高楼会挡住矮楼
    * 现在人A依次站在每一座高楼上，请问他一共能看见几栋楼
    * 请返回一个数组arr,arr[i]表示A站在i号楼能看见的楼数
    * 默认A一定能看见自己所在的楼
    * 例如height=[5,3,8,3,2,5],arr=[3,3,5,4,4,4]
    * */
    /*
    * 堆+预处理数组
    * 题眼:一旦楼被遮住，说明有一栋楼比它高，那么之前(或之后的所有楼都会看不见)
    * */
    public static int[] getBuildingsArray(int[] height){
        if(height==null||height.length==0){
            return null;
        }
        //分治思想:每一个位置所能看见的楼数等于1+往左看能看见的楼数+往右看能看见的楼数
        int[] right=new int[height.length];
        int[] left=new int[height.length];
        PriorityQueue<Integer> minHeap= new PriorityQueue<>();
        int N=height.length;
        for(int i=N-2;i>=0;i--){
            while(!minHeap.isEmpty()&&minHeap.peek()<height[i+1]){
                minHeap.poll();
            }
            minHeap.add(height[i+1]);
            right[i]=minHeap.size();
        }
        minHeap=new PriorityQueue<>();
        for(int i=1;i<N;i++){
            while(!minHeap.isEmpty()&&minHeap.peek()<height[i-1]){
                minHeap.poll();
            }
            minHeap.add(height[i-1]);
            left[i]=minHeap.size();
        }
        int[] res=new int[N];
        for(int i=0;i<res.length;i++){
            res[i]=1+left[i]+right[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] height={5,3,8,3,2,5};
        System.out.println(Arrays.toString(getBuildingsArray(height)));
    }
}
