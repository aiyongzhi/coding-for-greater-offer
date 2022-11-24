package 线段树;

import javax.naming.ldap.PagedResultsControl;
import java.util.Arrays;

public class MeetingCheck {
    //字节面试题
    // 来自字节飞书团队
// 在字节跳动，大家都使用飞书的日历功能进行会议室的预订，遇到会议高峰时期，
// 会议室就可能不够用，现在请你实现一个算法，判断预订会议时是否有空的会议室可用。
// 为简化问题，这里忽略会议室的大小，认为所有的会议室都是等价的，
// 只要空闲就可以容纳任意的会议，并且：
// 1. 所有的会议预订都是当日预订当日的时段
// 2. 会议时段是一个左闭右开的时间区间，精确到分钟
// 3. 每个会议室刚开始都是空闲状态，同一时间一个会议室只能进行一场会议
// 4. 会议一旦预订成功就会按时进行
// 比如上午11点到中午12点的会议即[660, 720)
// 给定一个会议室总数m
// 一个预定事件由[a,b,c]代表 :有效的会议必须满足 a<=b<c
// a代表预定动作的发生时间，早来早得; b代表会议的召开时间; c代表会议的结束时间
// 给定一个n*3的二维数组，即可表示所有预定事件
// 返回一个长度为n的boolean类型的数组，表示每一个预定时间是否成功
    // 0 : [6, 100, 200]
    // 1 : [4, 30,  300]
    // [100,30,200,300]
    // 现在只需要思考:如何处理开始时间
    //左神牛逼


    //[1,1440]这么多分钟,构造线段树
    //只要当前会议所在的区间的最大值小于<m就可以安排当前会议


    //构造线段树:功能
    //1:求范围内的最大值max
    //2:对范围内的增加add
    //时间复杂度O(N)

    public static class SegmentTree{
        private int[] max;
        private int[] lazy;
        private int N;
        public SegmentTree(int n){
            N=n;
            max=new int[n<<2];
            lazy=new int[n<<2];
        }
        private void pushUp(int rt){
            max[rt]=Math.max(max[rt<<1],max[rt<<1|1]);
        }
        private void pushDown(int rt){
            if(lazy[rt]!=0){
                lazy[rt<<1]+=lazy[rt];
                lazy[rt<<1|1]+=lazy[rt];
                max[rt<<1]+=lazy[rt];
                max[rt<<1|1]+=lazy[rt];
                lazy[rt]=0;
            }
        }
        private void add(int L,int R,int M,int rt,int l,int r){
            //能懒更新住就懒更新
            if(L<=l&&r<=R){
                lazy[rt]+=M;
                max[rt]+=M;
                return;
            }
            int mid=(l+r)/2;
            pushDown(rt);
            if(L<=mid){
                add(L,R,M,rt<<1,l,mid);
            }
            if(R>mid){
                add(L,R,M,rt<<1|1,mid+1,r);
            }
            pushUp(rt);
        }
        private int max(int L,int R,int rt,int l,int r){
            if(L<=l&&r<=R){
                return max[rt];
            }
            int mid=(l+r)/2;
            pushDown(rt);
            int ans=0;
            if(L<=mid){
                ans=Math.max(ans,max(L,R,rt<<1,l,mid));
            }
            if(R>mid){
                ans=Math.max(ans,max(L,R,rt<<1|1,mid+1,r));
            }
            return ans;
        }
        public void add(int L,int R,int M){
            add(L,R,M,1,1,N);
        }
        public int max(int L,int R){
            return max(L,R,1,1,N);
        }
    }
    public static boolean[] checkMeeting(int[][] meeting,int m){
        int n=meeting.length;
        boolean[] res=new boolean[n];
        int[][] rMeeting=new int[n][4];
        for(int i=0;i<n;i++){
            rMeeting[i][0]=i;
            rMeeting[i][1]=meeting[i][0];
            rMeeting[i][2]=meeting[i][1];
            rMeeting[i][3]=meeting[i][2]-1;
        }
        Arrays.sort(rMeeting,(a,b)->a[1]-b[1]);
        SegmentTree st=new SegmentTree(24*60);
        for(int i=0;i<n;i++){
            int[] arr=rMeeting[i];
            if(arr[2]<=arr[3]){//该会议有效
                if(st.max(arr[2],arr[3])<m){//安排当前会议
                    res[arr[0]]=true;
                    st.add(arr[2],arr[3],1);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] meeting={{6,100,200},{4,30,300}};
        int m=2;
        boolean[] booleans = checkMeeting(meeting, m);
        System.out.println(Arrays.toString(booleans));
    }
}
