import java.util.*;

public class 迷宫寻宝 {
    // 来自美团
// 某天小美进入了一个迷宫探险，根据地图所示，这个迷宫里有无数个房间
// 序号分别为1、2、3、...入口房间的序号为1
// 任意序号为正整数x的房间，都与序号 2*x 和 2*x + 1 的房间之间各有一条路径
// 但是这些路径是单向的，即只能从序号为x的房间去到序号为 2*x 或 2*x+1 的房间
// 而不能从 2*x 或 2*x+1 的房间去到序号为x的房间
// 在任何时刻小美都可以选择结束探险并离开迷宫，但是离开之后将无法再次进入迷宫
// 小美还提前了解了迷宫中宝藏的信息
// 已知宝藏共有n个，其中第i个宝藏在序号为pi的房间，价值为wi
// 且一个房间中可能有多个宝藏
// 小美为了得到更多的宝藏，需要精心规划路线，她找到你帮忙
// 想请你帮她计算一下，能获得的宝藏价值和最大值为多少
// 第一行一个正整数n，表示宝藏数量。
// 第二行为n个正整数p1, p2,...... pn，其中pi表示第 i 个宝藏在序号为pi的房间。
// 第三行为n个正整数w1, w2,...... wn，其中wi表示第i个宝藏的价值为wi。
// 1 <= n <= 40000, 1 <= pi < 2^30, 1 <= wi <= 10^6。

    //最暴力的方法:一定要回想原来的知识，这题就是一个背包的变种
    //假设当前获得的是index号宝藏,如果index+1能到达走递归，index+1能到达走递归
    //所有情况全部要收集到，就是[index.....]及其以后宝藏所能获得的最大收益
    //枚举
    public static int process(int n,int[][] treasure,int index,int[] dp){
        if(dp[index]!=-1){
            return dp[index];
        }
        if(index==n){//没有任务了
            dp[index]=0;
            return 0;
        }
        if(index==n-1){
            dp[index]=treasure[index][1];
            return dp[index];
        }
        //index..以后还有任务
        int next=Integer.MIN_VALUE;
        for(int i=index+1;i<n;i++){
            //当前index位置能否去i位置获得宝藏
            if (isReach(treasure[index][0],treasure[i][0])) {
                next=Math.max(next,process(n,treasure,i,dp));
            }
        }
        dp[index]=next!=Integer.MIN_VALUE?treasure[index][1]+next:treasure[index][1];
        return dp[index];
    }
    public static boolean isReach(int from,int to){
        while (to>=from){
            if(to==from){
                return true;
            }
            to/=2;
        }
        return false;
    }
    public static int maxValue(int n,int[] p,int[] w){
        int[][] treasure=new int[n][2];
        for(int i=0;i<n;i++){
            treasure[i][0]=p[i];
            treasure[i][1]=w[i];
        }
        Arrays.sort(treasure,(a,b)->a[0]-b[0]);
        int res=Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            int[] dp=new int[n+1];
            Arrays.fill(dp,-1);
            res=Math.max(res,process(n,treasure,i,dp));
        }
        return res;
    }

    //这题一定要想到这是颗完全二叉树
    //这是树形DP问题，但由于节点数目过于多
    //我们可以只保留有宝藏的节点
    public static class Node{
        public int id;
        public int value;
        public Node(int a,int b){
            id=a;
            value=b;
        }
    }
    public static class MyComparator implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            return o1.id-o2.id;
        }
    }
    public static int maxValue1(int n,int[] p,int[] w){
        //整体时间复杂度O(N)
        Node[] nodes=new Node[n];
        for(int i=0;i<n;i++){
            nodes[i]=new Node(p[i],w[i]);
        }
        Arrays.sort(nodes,new MyComparator());
        //开始建立二叉树
        HashMap<Integer,Node> indexNodeMap=new HashMap<>();
        for(int i=0;i<n;i++){
            int id=nodes[i].id;
            if(indexNodeMap.containsKey(id)){
                int value = indexNodeMap.get(id).value;
                nodes[i].value+=value;
                indexNodeMap.put(id,nodes[i]);
            }else {
                indexNodeMap.put(id,nodes[i]);
            }
        }
        HashMap<Node,List<Node>> childrenMap=new HashMap<>();
        //从由向左找父亲方式建图
        List<Node> rootList=new ArrayList<>();
        for(int i=n-1;i>=0;i--){
            int id=nodes[i].id;
            while(id!=0){
                if(indexNodeMap.containsKey(id/2)){
                    Node father=indexNodeMap.get(id/2);
                    if(!childrenMap.containsKey(father)){
                        childrenMap.put(father,new ArrayList<>());
                    }
                    childrenMap.get(father).add(nodes[i]);
                    break;
                }else{
                    id/=2;
                    if(id==0){
                        rootList.add(nodes[i]);
                    }
                }
            }
        }
        //树型dp
        int ans=Integer.MIN_VALUE;
        for (Node root : rootList) {
            ans=Math.max(ans,process(root,childrenMap));
        }
        return ans;
    }
    //递归
    //递归含义:给定root为跟的整颗树，请返回所有情况下能获得的最大价值
    public static int process(Node root,HashMap<Node,List<Node>> children){
        if(!children.containsKey(root)){
            return root.value;
        }else{
            List<Node> nodeList = children.get(root);
            int next=Integer.MIN_VALUE;
            for (Node node : nodeList) {
                next=Math.max(next,process(node,children));
            }
            return next+root.value;
        }
    }
    public static int[] generateRandomArray(int len,int maxValue){
        int[] arr=new int[len];
        for(int i=0;i<len;i++){
            arr[i]=(int)(Math.random()*maxValue)+1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime=1000;
        int maxLen=400;
        int maxValue=100000;
        for(int i=0;i<testTime;i++){
            int N=(int)(Math.random()*maxLen)+1;
            int[] p=generateRandomArray(N,maxValue);
            int[] w=generateRandomArray(N,maxValue);
            int ans1=maxValue(N,p,w);
            int ans2=maxValue1(N,p,w);
            if(ans1!=ans2){
                System.out.println(Arrays.toString(p));
                System.out.println(Arrays.toString(w));
                System.out.println("ans1==="+ans1);
                System.out.println("ans2==="+ans2);
                System.out.println("-------Fuck-------");
                break;
            }
        }
        System.out.println("Ops!");
    }
}
