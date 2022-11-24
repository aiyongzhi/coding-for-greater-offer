public class 二分图问题 {
    /*
    * 微软面试:
    *
   给定一个长度为n的二维数组graph，代表一张图
   graph[i] = {a,b,c,d} 表示i讨厌(a,b,c,d)，讨厌关系为双向的
  一共有n个人，编号0~n-1
   讨厌的人不能一起开会
   返回所有人能不能分成两组开会
   测试链接 : https://leetcode.cn/problems/is-graph-bipartite/
    *
    *
    * */



    public static class UnionFindSet{
        private int[] father;
        private int[] size;
        private int[] stack;

        public UnionFindSet(int n){
            father=new int[n];
            size=new int[n];
            stack=new int[n];
            for(int i=0;i<n;i++){
                father[i]=i;
                size[i]=1;
            }
        }

        public int findFather(int i){
            int si=0;
            while(i!=father[i]){
                stack[si++]=i;
                i=father[i];
            }
            while(si>0){
                father[stack[--si]]=i;
            }
            return i;
        }
        public boolean isSame(int i,int j){
            return findFather(i)==findFather(j);
        }
        public void union(int i,int j){
            int fatherI=findFather(i);
            int fatherJ=findFather(j);
            if(fatherI!=fatherJ){
                int big=size[fatherI]>size[fatherJ]?fatherI:fatherJ;
                int small=fatherI==big?fatherJ:fatherI;
                father[small]=big;
                size[big]+=size[small];
            }
        }
    }



    public boolean isBipartite(int[][] graph){
        if(graph==null||graph.length==0){
            return true;
        }
        int N=graph.length;
        UnionFindSet unionFind=new UnionFindSet(N);
        for (int[] neighbours : graph) {
            for (int j = 1; j < neighbours.length; j++) {
                unionFind.union(neighbours[j], neighbours[j - 1]);
            }
        }
        for(int i=0;i< N;i++){
            for(int j=0;j<graph[i].length;j++){
                if(unionFind.isSame(i,graph[i][j])){
                    return false;
                }
            }
        }
        return true;
    }
}
