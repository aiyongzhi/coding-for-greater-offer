public class 矩阵中的最长递增链 {
    public static int longestIncreasingPath1(int[][] matrix){
        int max=1;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                max=Math.max(max,process1(i,j,matrix));
            }
        }
        return max;
    }
    /*递归含义:从(i,j)位置开始走，请返回所能够走出的最长递增练的长度是多少 */
    private static int process1(int i,int j,int[][] matrix){
        //尝试向上下左右四个方向去走出最长递增链
        int up=i>0&&matrix[i-1][j]>matrix[i][j]?process1(i+1,j,matrix):0;
        int down=(i< matrix.length-1)&&matrix[i+1][j]>matrix[i][j]?process1(i+1,j,matrix):0;
        int left=(j>0&&matrix[i][j-1]>matrix[i][j])?process1(i,j-1,matrix):0;
        int right=(j<matrix[0].length-1)&&matrix[i][j+1]>matrix[i][j]?process1(i,j+1,matrix):0;
        return Math.max(Math.max(up,down),Math.max(left,right))+1;
    }
    /*
    * 由于位置依赖关系很难确定，也没有斜优这些优化点，这题优化到记忆化搜索就OK了
    * */
    public static int longestIncreasingPath2(int[][] matrix){
        int max=1;
        int N=matrix.length;
        int M=matrix[0].length;
        int[][] dp=new int[N][M];//这个dp缓存一定要放外面，因为这个矩阵是确定的，算过的地方就不需要再进行计算了
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                max=Math.max(max,process2(i,j,matrix,dp));
            }
        }
        return max;
    }

    private static int process2(int i, int j, int[][] matrix, int[][] dp) {
        if(dp[i][j]!=0){
            return dp[i][j];
        }
        int up=i>0&&matrix[i-1][j]>matrix[i][j]?process1(i+1,j,matrix):0;
        int down=(i< matrix.length-1)&&matrix[i+1][j]>matrix[i][j]?process1(i+1,j,matrix):0;
        int left=(j>0&&matrix[i][j-1]>matrix[i][j])?process1(i,j-1,matrix):0;
        int right=(j<matrix[0].length-1)&&matrix[i][j+1]>matrix[i][j]?process1(i,j+1,matrix):0;
        dp[i][j]=Math.max(Math.max(up,down),Math.max(left,right))+1;
        return dp[i][j];
    }
}
