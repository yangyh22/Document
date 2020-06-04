import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    private static final Lock LOCK = new ReentrantLock();

    public static void main(String args[]) throws NoSuchFieldException, IllegalAccessException {
        Thread a = new Thread(Test::test, "线程A");
        a.start();
        // a.interrupt();
        // new Thread(Test::test, "线程B").start();
        // new Thread(Test::test, "线程C").start();
        // new Thread(Test::test, "线程D").start();
        // new Thread(Test::test, "线程E").start();
    }

    public static void test() {
            try {
                LOCK.lock();
                LOCK.lock();
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }

    }


    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
