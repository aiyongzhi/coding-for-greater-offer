import java.util.*;

public class 大楼轮廓问题 {
    /*
    * 给你一个N*3的二维数组buildings，即这个数组有N行3列
    * buildings[i][0]:表示第i栋大楼的起始位置
    * buildings[i][1]:表示第i栋大楼的结束位置
    * buildings[i][2]:表示第i栋大楼的高度
    * 请你生成一个大楼轮廓数组
    *
    * */
    public static class Node{
        public int x;
        public boolean isLive;
        public int height;

        public Node(int x, boolean isLive, int height) {
            this.x = x;
            this.isLive = isLive;
            this.height = height;
        }
    }
    public static class MyComparator implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            return o1.x-o2.x;
        }
    }
    public static List<List<Integer>> getSkyline(int[][] buildings){
        if(buildings==null||buildings.length==0){
            return new ArrayList<>();
        }
        /* 生成nodes数组 */
        Node[] nodes=new Node[buildings.length*2];
        for(int i=0;i<buildings.length;i++){
            nodes[i*2]=new Node(buildings[i][0],true,buildings[i][2]);
            nodes[i*2+1]=new Node(buildings[i][1],false,buildings[i][2]);
        }
        Arrays.sort(nodes,new MyComparator());
        /*准备一个有序表，可以显示当前位置有哪些高度存活，并可以求出最大高度
        * key：大楼的高度 , value: 这个高度还存活的大楼的数目*/
        TreeMap<Integer, Integer> heightTimesMap=new TreeMap<>();
        /*  准备一个有序表，记录一下每一个位置的最大高度 */
        TreeMap<Integer, Integer> maxXHeightMap = new TreeMap<>();
        for (Node curNode : nodes) {
            //1:调整heightTimeMap
            if (curNode.isLive) {
                if (!heightTimesMap.containsKey(curNode.height)) {
                    heightTimesMap.put(curNode.height, 1);
                } else {
                    heightTimesMap.put(curNode.height, heightTimesMap.get(curNode.height) + 1);
                }
            } else {
                int time = heightTimesMap.get(curNode.height);
                if (time == 1) {
                    heightTimesMap.remove(curNode.height);
                } else {
                    heightTimesMap.put(curNode.height, time - 1);
                }
            }
            //2:记录该位置的大楼轮廓
            int maxHeight = heightTimesMap.isEmpty() ? 0 : heightTimesMap.lastKey();
            maxXHeightMap.put(curNode.x, maxHeight);
        }
        //2:借助每个位置的最大高度的map生成答案
        List<List<Integer>> res=new ArrayList<>();
        Set<Map.Entry<Integer, Integer>> entrySet = maxXHeightMap.entrySet();
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
}
