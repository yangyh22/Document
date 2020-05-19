# 概览

1. corePoolSize，核心线程数
2. maximumPoolSize，最大线程数
3. keepAliveTime，最大空闲时间
4. unit，最大空闲时间单位
5. workQueue，等待队列
6. handler, 队列数量过多的拒绝方案，默认方案AbortPolicy 直接拒绝





## 案例

1. 当核心线程数与最大线程数不一致时，同时进来的线程会先使用空余的线程，当没有空余的线程时，进入等待队列，当等待队列满了以后会开辟线程至最大线程数。<font color="red">当等待队列的长度没有设置，即使用默认长度，Integer.max，相当于线程会一直等待，而不是创建线程至最大连接数</font>

   ```java
     ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                   2,
                   0,
                   TimeUnit.SECONDS, new LinkedBlockingQueue<>(1)
           );
   
   
           for (int i = 1; i <= 3; i++) {
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
   ```

   



线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这
样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
说明：Executors 返回的线程池对象的弊端如下：
1） FixedThreadPool 和 SingleThreadPool：
允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
2） CachedThreadPool：
允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。  