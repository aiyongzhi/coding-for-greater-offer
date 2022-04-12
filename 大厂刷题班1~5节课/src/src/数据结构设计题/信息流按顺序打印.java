package 数据结构设计题;

import org.w3c.dom.Node;

import java.util.HashMap;

public class 信息流按顺序打印 {
    public static class Node {
        public String info;
        public Node next;

        public Node(String info) {
            this.info = info;
        }
    }

    public static class MessageBox {
        private final HashMap<Integer, Node> headMap;
        private final HashMap<Integer, Node> tailMap;
        private int waitPoint;

        public MessageBox() {
            this.headMap = new HashMap<>();
            this.tailMap = new HashMap<>();
            this.waitPoint = 1;
        }

        //接收信息的receive方法
        public void receive(int num, String info) {
            if (num <= 0) {
                return;
            }
            Node cur = new Node(info);
            //尝试和上区间合并
            if (tailMap.containsKey(num - 1)) {
                tailMap.get(num - 1).next = cur;
                tailMap.remove(num - 1);
                headMap.remove(num);
            }
            //尝试和下区间合并
            if (headMap.containsKey(num + 1)) {
                cur.next = headMap.get(num + 1);
                tailMap.remove(num);
                headMap.remove(num + 1);
            }
            if (waitPoint == num) {
                print();
            }
        }

        private void print() {
            Node cur = headMap.get(waitPoint);
            headMap.remove(waitPoint);
            while (cur != null) {
                System.out.print(cur.info + "  ");
                cur = cur.next;
                waitPoint++;
            }
            tailMap.remove(waitPoint - 1);
            System.out.println();
        }
    }
}
