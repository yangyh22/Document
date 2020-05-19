import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String args[]) throws NoSuchFieldException, IllegalAccessException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                3,
                0,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>()
        );


        for (int i = 1; i <= 4; i++) {
            int finalI = i;
            Runnable runnable = () -> {
                System.out.println("线程" + finalI + "进来了");
                try {
                    // 睡眠3秒，模拟线程占用
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + finalI + "执行完成");
            };
            threadPoolExecutor.submit(runnable);
        }
        threadPoolExecutor.shutdown();
        // 线程1进来了
        // 线程3进来了
        // 线程3执行完成
        // 线程1执行完成
        // 线程2进来了
        // 线程2执行完成

    }


    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
