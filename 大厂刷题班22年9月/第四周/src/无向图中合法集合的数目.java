import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
// 来自美团
// 给定一个无向图
// 从任何一个点x出发，比如有一条路径: x -> a -> b -> c -> y
// 这条路径上有5个点并且5个点都不一样的话，我们说(x,a,b,c,y)是一条合法路径
// 这条合法路径的代表，就是x,a,b,c,y所组成的集合，我们叫做代表集合
// 如果从b到y，还有一条路径叫(b,a,c,x,y)，那么(x,a,b,c,y)和(b,a,c,x,y)是同一个代表集合
// 返回这个无向图中所有合法路径的代表集合数量
// 题目给定点的数量n <= 15，边的数量m <= 60
// 所有的点编号都是从0~n-1的
public class 无向图中合法集合的数目 {


    //美团笔试题
    public static int validatePathSets(int[][] graph){
        int n=graph.length;
        //用二进制表示合法路径
        //每次将合法路径加入hashset的时候，确保合法路径没有统计过
        HashSet<Integer> validatePath=new HashSet<>();
        for (int i=0;i<n;i++){
            dfs(graph,i,1, (1 << i),validatePath);
        }
        return validatePath.size();
    }
    //深度优先搜索
    public static void dfs(int[][] graph,int cur,int len,int path,HashSet<Integer> validatePath){
        //从root节点出发，将所有合法路径表示的二进制加入hashSet当中
        //base case
        if(len==5){
            validatePath.add(path);
            return;
        }
        int[] tos = graph[cur];
        for(int to:tos){
            if(((path>>to)&1)==0){
                path|=(1<<to);
                dfs(graph,to,len+1,path,validatePath);
            }
        }
    }

    public static void main(String[] args) {
        int[][] graph=new int[][]{{0,1},{1,2},{2,3},{3,4},{4,5},{5,6},{6,7},{7,6}};
        System.out.println(validatePathSets(graph));
    }
}
