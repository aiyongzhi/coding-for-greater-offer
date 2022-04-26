import org.w3c.dom.Node;

import java.util.*;

public class Test {
    /* 用前序遍历构造二叉搜索树  *
     */
    public class Node{
        public int x;
        public boolean isLive;
        public int height;

        public Node(int x, boolean isLive, int height) {
            this.x = x;
            this.isLive = isLive;
            this.height = height;
        }
    }
    public class MyComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.x-o2.x;
        }
    }
    public List<List<Integer>> getSkyline(int[][] buildings){
        if(buildings==null|| buildings.length==0){
            return new ArrayList<>();
        }
        Node[] nodes=new Node[buildings.length*2];
        for(int i=0;i<buildings.length;i++){
            nodes[2*i]=new Node(buildings[i][0],true,buildings[i][2]);
            nodes[2*i+1]=new Node(buildings[i][1],false,buildings[i][2]);
        }
        Arrays.sort(nodes,new MyComparator());
        //1:标记每一个位置的最大高度
        TreeMap<Integer, Integer> heightTimeMap = new TreeMap<>();
        TreeMap<Integer, Integer> maxHeightX=new TreeMap<>();
        for (Node node : nodes) {
            //1:先处理楼的高度问题
            int x = node.x;
            int height = node.height;
            boolean isLive = node.isLive;
            if (isLive) {
                if (!heightTimeMap.containsKey(height)) {
                    heightTimeMap.put(height, 1);
                } else {
                    heightTimeMap.put(height, heightTimeMap.get(height) + 1);
                }
            } else {
                int time = heightTimeMap.get(height);
                if (time == 1) {
                    heightTimeMap.remove(height);
                } else {
                    heightTimeMap.put(height, time - 1);
                }
            }
            //2:生成这个位置的最大高度
            int maxHeight = heightTimeMap.isEmpty() ? 0 : heightTimeMap.lastKey();
            maxHeightX.put(x, maxHeight);
        }
        //2:生成答案，只有最大高度发生变化的时候才会生成高楼
        List<List<Integer>> res=new ArrayList<>();
        Set<Map.Entry<Integer, Integer>> entrySet = maxHeightX.entrySet();
        for(Map.Entry<Integer, Integer> element:entrySet){
            //只有高度变化才能加入答案中，表明是新生的一条轮廓
            int curX=element.getKey();
            int curHeight=element.getValue();
            if(res.isEmpty() || res.get(res.size()-1).get(1)!=curHeight){
                res.add(new ArrayList<>(Arrays.asList(curX,curHeight)));
            }
        }
        return res;
    }
    public static void main(String[] args) {
    }
}
