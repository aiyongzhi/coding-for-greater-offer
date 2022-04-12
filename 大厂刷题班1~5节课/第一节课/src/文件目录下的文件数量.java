import java.io.File;
import java.util.*;

public class 文件目录下的文件数量 {
    /*
     * 题目描述:给定一个文件的目录,
     * 请统计这个目录下的文件总数，
     * 注意文件夹不算文件，请不要统计在内
     * */
    //可以图的深搜也可以图的宽度优先搜索
    //进行优化:只有文件夹才有必要压入队列中
    //如果是文件直接统计就ok
    public static int numbersOfFile(String URL) {
        File root=new File(URL);
        if(!root.isDirectory() && !root.isFile()){
            return 0;
        }
        if(root.isFile()){
            return 1;
        }
        //是文件夹 图的宽度优先遍历
        //只将文件夹放入队列
        int count=0;
        Queue<File> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            File cur=queue.poll();
            File[] files = cur.listFiles();
            if(files!=null){
                for(File file:files){
                    if(file.isFile()){
                        count++;
                    }
                    if(file.isDirectory()){
                        queue.add(file);
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(numbersOfFile("C:/Program Files"));
    }
}
