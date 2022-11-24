import java.util.Comparator;
import java.util.PriorityQueue;

public class 单元最短路径 {
    /*
    * 给定一个正数n，代表有n座城市，编号为1-n
    * 给定一个distances数组,distances[i]=distance[a,b,c]:表示a到b的距离是c
    * 现在给定两个正数x，y请问x 到 y的最短距离是多少
    * 注意这是一个有向图
    * */
    public static class Node{
        public int id;
        public int sum;
        public Node(int a,int b){
            id=a;
            sum=b;
        }
    }
    public static int minDistance(int n,int[][] distances,int x,int y){
        //1:生成图的邻接矩阵
        int[][] graph=new int[n+1][n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                graph[i][j]=Integer.MAX_VALUE;
            }
        }
        for (int[] distance : distances) {
            graph[distance[0]][distance[1]]=Math.min(graph[distance[0]][distance[1]],distance[2]);
            graph[distance[1]][distance[0]]=Math.min(graph[distance[1]][distance[0]],distance[2]);
        }
        boolean[] isUsed=new boolean[n+1];//记录每个节点是否用过做为基准点
        //准备小根堆
        PriorityQueue<Node> priorityQueue=new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.sum-o2.sum;
            }
        });
        priorityQueue.add(new Node(x,0));//x->x距离为0
        //标准的Dijkstra算法
        while(!priorityQueue.isEmpty()){
            Node cur = priorityQueue.poll();
            if(cur.id==y){
                return cur.sum;
            }
            isUsed[cur.id]=true;
            int[] tos = graph[cur.id];
            for(int to=1;to<=n;to++){
                if(cur.id!=to&&!isUsed[to]&&tos[to]!=Integer.MAX_VALUE){
                    priorityQueue.add(new Node(to,cur.sum+tos[to]));
                }
            }
        }
        return Integer.MAX_VALUE;//表示x到y不可达
    }
}
