import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class Test {


    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //创建一个Callable，3秒后返回String类型
        Callable<String> myCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                System.out.println("calld方法执行了");
                return "call方法返回值";
            }

        };
        System.out.println("提交任务之前 " + getStringDate());
        String call = myCallable.call();
        System.out.println("提交任务之后，获取结果之前 " + getStringDate());
        System.out.println("获取返回值: " + call);
        System.out.println("获取到结果之后 " + getStringDate());
        executor.shutdown();
    }


    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
