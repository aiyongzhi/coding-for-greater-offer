package 线段树;

public class 最大值功能 {
    public static class SegmentTree {
        private int n;
        private int[] max;
        private int[] update;
        private boolean[] change;
        public SegmentTree(int maxSize) {
            n = maxSize + 1;
            max = new int[n << 2];
            update=new int[n<<2];
            change=new boolean[n<<2];
        }
        public void update(int L,int R,int M) {
            update(L, R, M, 1, n-1, 1);
        }
        public void pushDown(int rt){
            if(change[rt]){
                change[rt<<1]=true;
                change[rt<<1|1]=true;
                update[rt<<1]=update[rt];
                update[rt<<1|1]=update[rt];
                max[rt<<1]=update[rt];
                max[rt<<1|1]=update[rt];
                change[rt]=false;
            }
        }
        public int max(int left, int right) {
            return max(left, right, 1, n-1, 1);
        }
        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        private void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                max[rt] = C;
                update[rt]=C;
                change[rt]=true;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        private int max(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return max[rt];
            }
            int mid = (l + r) >> 1;
            int ans = 0;
            pushDown(rt);
            if (L <= mid) {
                ans = Math.max(ans, max(L, R, l, mid, rt << 1));
            }
            if (R > mid) {
                ans = Math.max(ans, max(L, R, mid + 1, r, rt << 1 | 1));
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        SegmentTree st=new SegmentTree(5);//[0,0,0,0,0]
        st.update(1,1,2);
        st.update(2,3,4);
        System.out.println(st.max(1, 3));
    }
}
