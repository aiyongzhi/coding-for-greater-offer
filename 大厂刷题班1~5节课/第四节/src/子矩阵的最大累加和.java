public class 子矩阵的最大累加和 {
    /*
    * 解法一:压缩数组技巧+子数组的最大累加和
    *
    * */
    /*子数组的最大累加和*/
    public static int[] getMaxMatrix(int[][] matrix){
        if(matrix==null||matrix.length==0||matrix[0].length==0){
            return null;
        }
        int r1=0;
        int c1=0;
        int r2=0;
        int c2=0;
        int max=Integer.MIN_VALUE;
        for(int i=0;i<matrix.length;i++){
            int[] arr=new int[matrix[0].length];
            for(int j=i;j<matrix.length;j++){
                int cur=0;
                int begin=0;
                for(int k=0;k<matrix[0].length;k++){
                    arr[k]+=matrix[j][k];
                    cur+=arr[k];
                    if(max<cur){
                        r1=i;
                        r2=j;
                        c1=begin;
                        c2=k;
                        max=cur;
                    }
                    if(cur<0){
                        begin=k+1;
                        cur=0;
                    }
                }
            }
        }
        return new int[]{r1,c1,r2,c2};
    }
}
