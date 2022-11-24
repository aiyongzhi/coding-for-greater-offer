import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        //所谓的异步很多实际上都是开一个子线程去执行
        //相应的代码逻辑而主线程可以继续执行

        //Callable接口和 Runnable接口的区别
        //Callable接口可以看作是一个具有返回值的Runnable
        FutureTask<List<String>> futureTask=new FutureTask<List<String>>(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> studentNameList=new ArrayList<>();
                //执行向数据库查询的语句
                studentNameList.add("哈哈");
                Thread.sleep(5000);
                System.out.println("进来了");
                return studentNameList;
            }
        });
        new Thread(futureTask).start();
        try {
            List<String> list = futureTask.get();
            System.out.println(Arrays.toString(list.toArray()));
            System.out.println("哈哈");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
