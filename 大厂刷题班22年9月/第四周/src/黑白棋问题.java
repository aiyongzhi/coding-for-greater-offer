import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class 黑白棋问题 {
    // 来自360
// 有n个黑白棋子，它们的一面是黑色，一面是白色
// 它们被排成一行，位置0~n-1上。一开始所有的棋子都是黑色向上
// 一共有q次操作，每次操作将位置标号在区间[L，R]内的所有棋子翻转
// 那么这个范围上的每一颗棋子的颜色也就都改变了
// 请在每次操作后，求这n个棋子中，黑色向上的棋子个数
// 1 <= n <= 10^18
// 1 <= q <= 300
// 0 <= 每一条操作的L、R <= n - 1
// 输出q行，每一行一个整数，表示操作后的所有黑色棋子的个数
// 注意 : 其实q <= 10^5也可以通过，360考试时候降低了难度
    //根据数据量猜解法:这题时间复杂度一定与n无关
    //0代表黑棋  1代表白棋
    //arr数组一开始全是0，执行q次操作，每次选择arr[L...R]范围内0变1，1变0，请问每次操作后0的个数
    //对于一个范围内的反转操作，显然应该使用线段树

    //但是因为节点个数太多，10^18次方无疑会空间溢出，所以使用动态开点线段数
    //只要修改次数<=10^5都能够吼住


    //动态开点线段树应该完成的功能
    //1: 对一个范围内的反转
    //2: 对一个范围内的求黑棋的数目


    //线段树有一个易错点:就是线段树的下标是从1开始的而题目数组一般从0开始，一定要注意转换
    //懒更新信息:该节点所表示的范围有没有修改过
    public static class Node{
        public long sum;
        public boolean reversed;
        public Node left;
        public Node right;
        public Node(long n){
            sum=n;
            reversed=false;
        }
    }
    public static class DynamicSegmentTree{
        public Node root;
        public long N;
        public DynamicSegmentTree(long n){
            N=n;
            root=new Node(N);
        }
        public void pushUp(Node rt){
            rt.sum=rt.left.sum+rt.right.sum;
        }
        //动态开点线段树的节点是，在pushDown时开出来的
        public void pushDown(Node rt,long ln,long rn){
            if(rt.left==null){
                rt.left=new Node(ln);
            }
            if(rt.right==null){
                rt.right=new Node(rn);
            }
            if(rt.reversed){
                rt.left.reversed=!rt.left.reversed;
                rt.right.reversed=!rt.right.reversed;
                rt.left.sum=ln-rt.left.sum;
                rt.right.sum=rn-rt.right.sum;
                rt.reversed=false;
            }
        }
        private void reverse(long L,long R,Node rt,long l,long r){
            //能否懒更新住
            if(L<=l&&r<=R){
                rt.reversed=!rt.reversed;
                rt.sum=(r-l+1)-rt.sum;
                return;
            }
            long mid=(l+(r-l)/2);
            pushDown(rt,mid-l+1,r-mid);//将懒更新信息往下沉
            if(L<=mid){
                reverse(L,R,rt.left,l,mid);
            }
            if(R>mid){
                reverse(L,R,rt.right,mid+1,r);
            }
            pushUp(rt);
        }
        public void reverse(long L,long R){
            if(L>R){
                return;
            }
            reverse(L+1,R+1,root,1,N);
        }
        public long blacks(){
            return root.sum;
        }
    }
    public static List<Long> whiteBlackChess(long[][] options, long n){
        DynamicSegmentTree st=new DynamicSegmentTree(n);
        List<Long> res=new ArrayList<>();
        for (long[] option : options) {
            st.reverse(option[0],option[1]);
            res.add(st.blacks());
        }
        return res;
    }
}
