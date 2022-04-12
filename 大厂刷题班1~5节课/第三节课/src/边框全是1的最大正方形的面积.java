public class 边框全是1的最大正方形的面积 {
    /*
    * 题目:给定一个只含有0和1的二维数组,请求出边框全是1的最大正方形的面积。
    * 力扣链接:https://leetcode-cn.com/problems/largest-1-bordered-square/
    * */
    public static int largest1BorderedSquare1(int[][] grid){
        if(grid==null||grid.length==0||grid[0].length==0){
            return 0;
        }
        int N=grid.length;
        int M=grid[0].length;
        int[][] right=new int[grid.length][grid[0].length];
        int[][] down=new int[grid.length][grid[0].length];
        setBorderInfo(grid,right,down);
        //枚举出所有的正方形
        int maxBorder=0;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                for(int border=1;border<=Math.min(N-i,M-j);border++){
                    if(right[i][j]>=border&&down[i][j]>=border&&
                    right[i+border-1][j]>=border&&down[i][j+border-1]>=border){
                        maxBorder=Math.max(maxBorder,border);
                    }
                }
            }
        }
        return maxBorder*maxBorder;
    }
    private static void setBorderInfo(int[][] m, int[][] right, int[][] down) {
        int N=m.length;
        int M=m[0].length;
        //填特殊位置
        if(m[N-1][M-1]==1){
            right[N-1][M-1]=1;
            down[N-1][M-1]=1;
        }
        //填最后一行
        for(int col=M-2;col>=0;col--){
            if(m[N-1][col]==1){
                right[N-1][col]=1+right[N-1][col+1];
            }
            down[N-1][col]=m[N-1][col];
        }
        //填最后一列
        for(int row=N-2;row>=0;row--){
            if(m[row][M-1]==1){
                down[row][M-1]=1+down[row+1][M-1];
            }
            right[row][M-1]=m[row][M-1];
        }
        //填好普遍位置
        for(int row=N-2;row>=0;row--){
            for(int col=M-2;col>=0;col--){
                if(m[row][col]==1){
                    right[row][col]=1+right[row][col+1];
                    down[row][col]=1+down[row+1][col];
                }
            }
        }
    }
    //进行剪枝优化，不必要每一个点都尝试所有正方形的边
    public static int largest1BorderedSquare2(int[][] grid){
        if(grid==null||grid.length==0||grid[0].length==0){
            return 0;
        }
        int N=grid.length;
        int M=grid[0].length;
        int[][] right=new int[grid.length][grid[0].length];
        int[][] down=new int[grid.length][grid[0].length];
        setBorderInfo(grid,right,down);
        for(int size=Math.min(N,M);size>=1;size--){
            if(hasSizeOfBorder(size,right,down)){
                return size*size;
            }
        }
        return 0;
    }
    private static boolean hasSizeOfBorder(int size,int[][] right,int[][] down){
        //看看整个矩阵中是否有指定size大小的边，如果有则直接返回true，没有就返回false
        int N=right.length;
        int M=right[0].length;
        for(int i=0;i<=(N-size);i++){
            for(int j=0;j<=(M-size);j++){
                if(right[i][j]>=size&&down[i][j]>=size&&
                        right[i+size-1][j]>=size&&down[i][j+size-1]>=size){
                    return true;
                }
            }
        }
        return false;
    }
}
