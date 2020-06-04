import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {

    private final static ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock(true);
    private final static ReentrantReadWriteLock.ReadLock READ_LOCK = LOCK.readLock();
    private final static ReentrantReadWriteLock.WriteLock WRITE_LOCK = LOCK.writeLock();
    private final static List<String> DATA = new ArrayList<>();

    public static void main(String[] args) {
        new Thread(ReentrantReadWriteLockTest::write).start();
        new Thread(ReentrantReadWriteLockTest::write).start();
        new Thread(ReentrantReadWriteLockTest::read).start();
        new Thread(ReentrantReadWriteLockTest::read).start();
    }

    public static void write() {
        try {
            WRITE_LOCK.lock();
            DATA.add("写数据");
            System.out.println(Thread.currentThread().getName() + "【write()】写数据");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "【write()】锁释放");
            WRITE_LOCK.unlock();
        }
    }

    public static void read() {
        try {
            READ_LOCK.lock();
            if (DATA.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + "【read()】没有数据");
            } else {
                System.out.println(Thread.currentThread().getName() + "【read()】读数据" + DATA.size());
            }
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            System.out.println(Thread.currentThread().getName() + "【read()】锁释放");
            READ_LOCK.unlock();
        }
    }

}
